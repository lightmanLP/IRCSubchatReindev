package com.lightmanlp.ircsubchat;

import java.io.IOException;
import java.util.logging.Level;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.delay.Delay;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.managers.BackgroundListenerManager;

import com.fox2code.foxloader.loader.ClientMod;
import com.lightmanlp.ircsubchat.configs.Config;

public class IRCManager {
    private static IRCManager INSTANCE = null;

    private BackgroundListenerManager listenerManager;
    private PircBotX bot;
    private Thread thread;

    private String server;
    private String channel;
    private String password;
    private Delay reconnectDelay;

    public IRCManager(String server, String channel, String password, Delay reconnectDelay) {
        this.server = server;
        this.channel = channel;
        this.password = password;
        this.reconnectDelay = reconnectDelay;
    }

    public static IRCManager get() {
        if (INSTANCE == null) {
            Config cfg = IRCSubchatMod.INSTANCE.cfg;
            INSTANCE = new IRCManager(
                cfg.server,
                cfg.channel,
                cfg.password,
                cfg.reconnectDelay
            );
        }
        return INSTANCE;
    }

    public void start() {
        this.listenerManager = new BackgroundListenerManager();

        Listener listener = new IRCListener();
        Configuration configuration = new Configuration.Builder()
            .setName(ClientMod.getLocalNetworkPlayer().getPlayerName())
            .setAutoNickChange(true)
            .addServer(this.server)
            .addAutoJoinChannel(this.channel)
            .setServerPassword(this.password)
            .setAutoReconnect(true)
            .setAutoReconnectDelay(this.reconnectDelay)
            .addListener(listener)
            .setListenerManager(listenerManager)
            .buildConfiguration();

        this.bot = new PircBotX(configuration);
        thread = new Thread(this::threadWorker);
        thread.setDaemon(true);
        thread.start();
    }

    private void threadWorker() {
        try {
            this.bot.startBot();
        } catch (IOException exc) {
            IRCSubchatMod.INSTANCE.getLogger().log(Level.WARNING, "error", exc);  // TODO
        } catch (IrcException exc) {
            IRCSubchatMod.INSTANCE.getLogger().log(Level.WARNING, "error", exc);  // TODO
        }
    }

    public void send(String text) {
        this.bot.sendIRC().message(channel, text);
    }
}
