package com.lightmanlp.ircsubchat;

import com.lightmanlp.ircsubchat.configs.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.mitask.PlayerCommandHandler;

public class ChatProcessor {
    private static ChatProcessor INSTANCE;

    public boolean ircMode = false;

    public ChatProcessor() {
        assert INSTANCE == null;
        INSTANCE = this;
    }

    public static ChatProcessor get() {
        if (INSTANCE == null) {
            new ChatProcessor();
        }
        return INSTANCE;
    }

    public String process(String text) {
        Config cfg = IRCSubchatMod.INSTANCE.cfg;
        Minecraft mc = Minecraft.getInstance();

        if (mc.lineIsCommand(text)) {
            // TODO: do smth to prevent serverside command
            PlayerCommandHandler.instance.handleSlashCommand(text, mc.thePlayer);
            return text;
        }

        boolean localIrcMode = false;
        for (String prefix : cfg.msgPrefixes) {
            if (text.length() > prefix.length() && text.startsWith(prefix, 0)) {
                localIrcMode = true;
				text = text.substring(prefix.length()).trim();
                break;
            }
        }
        if (this.ircMode) { localIrcMode = !localIrcMode; }

        if (localIrcMode) {
            IRCManager irc = IRCManager.get();
            irc.send(text);
            irc.viewMessage(cfg.nickname, text);
            return null;
        }

        return text;
    }

    public void toggleIrcMode() {
        this.ircMode = !this.ircMode;
    }
}
