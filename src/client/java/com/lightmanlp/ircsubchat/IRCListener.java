package com.lightmanlp.ircsubchat;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.fox2code.foxloader.loader.ClientMod;

public class IRCListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) {
        // TODO: check this
        // if (event.getChannel().getName() != channel) {
        //     return;
        // }
        String view = String.format(
            "[IRC] {}: {}",
            event.getUser().getNick(),
            event.getMessage()
        );
        ClientMod.getGameInstance().ingameGUI.addChatMessage(view);
	}
}
