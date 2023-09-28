package com.lightmanlp.ircsubchat;

import com.lightmanlp.ircsubchat.configs.Config;

public class CommandProcessor {
    public static boolean process(String text) {
        Config cfg = IRCSubchatMod.INSTANCE.cfg;

        for (String prefix : cfg.msgPrefixes) {
            if (text.length() > prefix.length() && text.startsWith(prefix, 0)) {
                IRCManager irc = IRCManager.get();
				String cleanMsg = text.substring(prefix.length());
                irc.send(cleanMsg);
                irc.viewMessage(cfg.nickname, cleanMsg);
                return true;
            }
        }

        return false;
    }
}
