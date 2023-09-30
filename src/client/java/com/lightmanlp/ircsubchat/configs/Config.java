package com.lightmanlp.ircsubchat.configs;

import lombok.Getter;
import lombok.Setter;

public class Config {
    @Getter @Setter private String nickname = "randomuser";
    @Getter @Setter private String server = "irc.libera.chat";
    @Getter @Setter private String channel = "#reindev";
    @Getter @Setter private String password = null;

    @Getter @Setter private String[] msgPrefixes = {"."};
}
