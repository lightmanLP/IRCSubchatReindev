package com.lightmanlp.ircsubchat;

public class CommandProcessor {
    public static void process(String text) {
        for (String prefix : IRCSubchatMod.INSTANCE.cfg.msgPrefixes) {
            if (text.startsWith(prefix, 0)) {
                IRCManager.get().send(text.substring(prefix.length()));
                return;
            }
        }
    }
}
