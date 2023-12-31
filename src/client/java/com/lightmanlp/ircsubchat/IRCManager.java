package com.lightmanlp.ircsubchat;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.delay.AdaptingDelay;
import org.pircbotx.delay.Delay;
import org.pircbotx.delay.StaticDelay;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.managers.BackgroundListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.network.ChatColors;
import com.lightmanlp.ircsubchat.configs.Config;

public class IRCManager {
    private static IRCManager INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(IRCManager.class);
    private static final int SEND_RETRIES = 5;
    private static final Delay SEND_RETRY_DELAY = new StaticDelay(250);

    private BackgroundListenerManager listenerManager;
    private PircBotX bot;
    private Thread listenerThread;
    private Thread senderThread;
    private BlockingQueue<String> messagesQueue;

    public Config cfg;
    public Delay reconnectDelay;

    public IRCManager() {
        this(
            IRCSubchatMod.INSTANCE.cfg,
            new AdaptingDelay(500, 10000)
        );
    }

    public IRCManager(
        Config cfg,
        Delay reconnectDelay
    ) {
        assert INSTANCE == null;
        INSTANCE = this;

        this.cfg = cfg;
        this.reconnectDelay = reconnectDelay;
    }

    public static IRCManager get() {
        if (INSTANCE == null) {
            new IRCManager();
        }
        return INSTANCE;
    }

    public void start() {
        this.listenerManager = new BackgroundListenerManager();
        Listener listener = new IRCListener(this);

        Configuration configuration = new Configuration.Builder()
            .setName(this.cfg.getNickname())
            .setAutoNickChange(true)
            .addServer(this.cfg.getServer())
            .addAutoJoinChannel(this.cfg.getChannelHashed())
            .setServerPassword(this.cfg.getPassword())
            .setAutoReconnect(true)
            .setAutoReconnectDelay(this.reconnectDelay)
            .setListenerManager(this.listenerManager)
            .addListener(listener)
            .buildConfiguration();
        this.bot = new PircBotX(configuration);

        this.messagesQueue = new LinkedBlockingQueue<>();
        this.listenerThread = new Thread(this::listenerThreadWorker);
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
        this.senderThread = new Thread(this::sendThreadWorker);
        this.senderThread.setDaemon(true);
        this.senderThread.start();
    }

    public void send(String text) {
        this.messagesQueue.add(text);
    }

    private void sendInternal(String text) {
        if (text.equals("")) { return; }
        this.bot.sendIRC().message(this.cfg.getChannelHashed(), text);
    }

    public void viewMessage(String nick, String text) {
        if (nick == null) { nick = this.bot.getNick(); }
        String view = String.format(
            "[%sIRC%s] %s: %s",
            ChatColors.AQUA, ChatColors.RESET, nick, text
        );
        ClientMod.getGameInstance().ingameGUI.addChatMessage(view);
    }

    private void listenerThreadWorker() {
        try {
            while (true) {
                try {
                    this.bot.startBot();
                } catch (IOException exc) {
                    LOGGER.error("IRC connection error", exc);
                }
                try {
                    Thread.sleep(this.reconnectDelay.getDelay());
                } catch (InterruptedException exc) {}
            }
        } catch (IrcException exc) {
            LOGGER.error("IRC init error", exc);
        }
    }

    private void sendThreadWorker() {
        while (true) {
            try {
                String text = this.messagesQueue.take();
                int retries;
                for (retries = 0; retries < SEND_RETRIES; ++retries) {
                    try {
                        sendInternal(text);
                        viewMessage(null, text);
                        break;
                    } catch (Exception exc) {
                        LOGGER.warn("Error sending IRC message", exc);
                        Thread.sleep(SEND_RETRY_DELAY.getDelay());
                    }
                }
                if (retries == SEND_RETRIES) {
                    viewMessage(null, ChatColors.RED + text);
                }
            } catch (InterruptedException exc) {
                LOGGER.warn("Unexpected interrupt", exc);
            }
        }
    }
}
