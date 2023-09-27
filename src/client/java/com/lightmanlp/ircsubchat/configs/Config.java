package com.lightmanlp.ircsubchat.configs;

import org.pircbotx.delay.Delay;

public abstract class Config {
    public String server;
    public String channel;
    public String password;
    public Delay reconnectDelay;

    public String[] msgPrefixes;
}
