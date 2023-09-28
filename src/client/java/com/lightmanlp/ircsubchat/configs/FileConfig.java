package com.lightmanlp.ircsubchat.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.lightmanlp.ircsubchat.IRCSubchatMod;

public class FileConfig extends Config {
    private static final String DELIMITER = " and ";

    public FileConfig() throws IOException {
        File cfg = new File(IRCSubchatMod.INSTANCE.getConfigFolder(), "IRCSubchat.cfg");
        Properties prop = new Properties();

        if (cfg.exists()) {
            {
                FileInputStream cfgStream = new FileInputStream(cfg);
                prop.load(cfgStream);
                cfgStream.close();
            }

            this.nickname = prop.getProperty("nickname", this.nickname).trim();
            this.server = prop.getProperty("server", this.server).trim();
            this.channel = prop.getProperty("channel", this.channel).trim();
            String password = prop.getProperty("password", "").trim();
            if (password != "") {
                this.password = password;
            }
            String msgPrefixes = prop.getProperty("msgPrefixes", null);
            if (msgPrefixes != null) {
                msgPrefixes = msgPrefixes.trim();
                if (msgPrefixes == "") {
                    this.msgPrefixes = new String[]{};
                } else {
                    this.msgPrefixes = msgPrefixes.split(DELIMITER);
                }
            }
        } else {
            prop.put("nickname", this.nickname);
            prop.put("server", this.server);
            prop.put("channel", this.channel);
            prop.put("password", this.password == null ? "" : this.password);
            prop.put("msgPrefixes", String.join(DELIMITER, this.msgPrefixes));

            cfg.createNewFile();
            FileOutputStream cfgStream = new FileOutputStream(cfg, false);
            prop.store(cfgStream, "config");
            cfgStream.close();
        }
    }

}
