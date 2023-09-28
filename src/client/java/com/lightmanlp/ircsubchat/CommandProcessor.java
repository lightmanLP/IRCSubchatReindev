package com.lightmanlp.ircsubchat;

import com.lightmanlp.ircsubchat.configs.Config;

public class CommandProcessor {
    public static boolean process(String text) {
        Config cfg = IRCSubchatMod.INSTANCE.cfg;

        for (String prefix : cfg.msgPrefixes) {
            if (text.length() > prefix.length() && text.startsWith(prefix, 0)) {
                IRCManager irc = IRCManager.get();
                irc.send(text.substring(prefix.length()));
                irc.viewMessage(cfg.nickname, text);
                return true;
            }
        }

        return false;
    }
}
