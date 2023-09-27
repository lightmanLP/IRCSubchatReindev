package com.lightmanlp.ircsubchat.configs;

import org.pircbotx.delay.Delay;

// shitstub
public class ConstConfig extends Config {
    public final String server = "irc.libera.chat:6697";
    public final String channel = "#reindev-ru";
    public final String password = null;
    public final Delay reconnectDelay = null;

    public final String[] msgPrefixes = {".\\", "/\\"};
}
