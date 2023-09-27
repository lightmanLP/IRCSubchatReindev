package com.lightmanlp.ircsubchat;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.loader.ClientMod;

import com.lightmanlp.ircsubchat.configs.Config;
import com.lightmanlp.ircsubchat.configs.ConstConfig;

public class IRCSubchatMod extends Mod implements ClientMod {
    public static IRCSubchatMod INSTANCE;

    public Config cfg;

    @Override
    public void onPreInit() {
        INSTANCE = this;
        this.cfg = new ConstConfig();
    }

    @Override
    public void onInit() {
        IRCManager.get().start();
    }
}
