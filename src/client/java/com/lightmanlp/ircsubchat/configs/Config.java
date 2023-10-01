package com.lightmanlp.ircsubchat.configs;

import lombok.Getter;
import lombok.Setter;

public class Config {
    @Getter @Setter private String nickname = "randomuser";
    @Getter @Setter private String server = "irc.libera.chat";
    @Getter private String channel = "reindev";
    @Getter @Setter private String password = null;

    @Getter @Setter private String[] msgPrefixes = {"."};

    public void setChannel(String channel) {
        if (channel.startsWith("#")) {
            channel = channel.substring(1);
        }
        this.channel = channel;
    }

    public String getChannelHashed() {
        return "#" + this.channel;
    }
}
