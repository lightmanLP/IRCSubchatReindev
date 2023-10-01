package com.lightmanlp.ircsubchat;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class IRCListener extends ListenerAdapter {
    private IRCManager manager;

    public IRCListener(IRCManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessage(MessageEvent event) {
        if (!event.getChannel().getName().equals(manager.cfg.getChannelHashed())) {
            return;
        }
        IRCManager.get().viewMessage(
            event.getUser().getNick(),
            event.getMessage()
        );
	}
}
