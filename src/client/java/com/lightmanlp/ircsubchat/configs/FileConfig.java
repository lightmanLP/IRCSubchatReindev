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

            setNickname(prop.getProperty("nickname", getNickname()).trim());
            setServer(prop.getProperty("server", getServer()).trim());
            setChannel(prop.getProperty("channel", getChannel()).trim());
            String password = prop.getProperty("password", "").trim();
            if (password != "") { setPassword(password); }
            String msgPrefixes = prop.getProperty("msgPrefixes", null);
            if (msgPrefixes != null) {
                msgPrefixes = msgPrefixes.trim();
                setMsgPrefixes(msgPrefixes != "" ? msgPrefixes.split(DELIMITER) : new String[]{});
            }
        } else {
            prop.put("nickname", getNickname());
            prop.put("server", getServer());
            prop.put("channel", getChannel());
            prop.put("password", getPassword() == null ? "" : getPassword());
            prop.put("msgPrefixes", String.join(DELIMITER, getMsgPrefixes()));

            cfg.createNewFile();
            FileOutputStream cfgStream = new FileOutputStream(cfg, false);
            prop.store(cfgStream, "IRCSubchat config");
            cfgStream.close();
        }
    }

}
