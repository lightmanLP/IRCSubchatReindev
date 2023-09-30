package com.lightmanlp.ircsubchat;

import com.fox2code.foxloader.loader.Mod;

import java.io.IOException;

import com.fox2code.foxloader.loader.ClientMod;

import com.lightmanlp.ircsubchat.configs.Config;
import com.lightmanlp.ircsubchat.configs.FileConfig;

public class IRCSubchatMod extends Mod implements ClientMod {
    public static IRCSubchatMod INSTANCE;

    public Config cfg;

    public IRCSubchatMod() {
        assert INSTANCE == null;
        INSTANCE = this;
    }

    @Override
    public void onPreInit() {
        try {
            this.cfg = new FileConfig();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void onPostInit() {
        IRCManager.get().start();
    }
}
