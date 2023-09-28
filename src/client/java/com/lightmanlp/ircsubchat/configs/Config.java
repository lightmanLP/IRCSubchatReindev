package com.lightmanlp.ircsubchat.configs;

public abstract class Config {
    public String nickname = "randomuser";
    public String server = "irc.libera.chat";
    public String channel = "#reindev";
    public String password = null;

    public String[] msgPrefixes = {"."};
}
