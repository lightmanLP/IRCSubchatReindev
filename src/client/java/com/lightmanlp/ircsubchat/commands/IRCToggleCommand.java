package com.lightmanlp.ircsubchat.commands;

import com.lightmanlp.ircsubchat.ChatProcessor;
import com.lightmanlp.ircsubchat.commands.framework.ClientCommand;

import net.minecraft.mitask.PlayerCommandHandler;
import net.minecraft.mitask.command.Command;
import net.minecraft.src.client.player.EntityPlayerSP;

public class IRCToggleCommand extends Command implements ClientCommand {
    public IRCToggleCommand(PlayerCommandHandler commandHandler) {
        super("irctoggle", false, false, "irct");
    }

    public void onExecute(String[] args, EntityPlayerSP executor) {
        ChatProcessor chat = ChatProcessor.get();
        chat.toggleIrcMode();
        executor.addChatMessage(
            BLUE + "IRC mode: "
            + (chat.ircMode ? GREEN + "enabled" : RED + "disabled")
        );
    }

    public void printHelpInformation(EntityPlayerSP commandExecutor) {}

    public String commandSyntax() {
      return YELLOW + "/irctoggle or /irct";
    }
}
