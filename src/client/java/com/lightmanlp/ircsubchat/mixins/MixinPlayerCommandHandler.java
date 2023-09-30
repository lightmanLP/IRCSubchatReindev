package com.lightmanlp.ircsubchat.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lightmanlp.ircsubchat.commands.IRCToggleCommand;
import com.lightmanlp.ircsubchat.commands.framework.ClientCommand;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.Minecraft;
import net.minecraft.mitask.PlayerCommandHandler;
import net.minecraft.mitask.command.Command;
import net.minecraft.src.client.player.EntityPlayerSP;

@Mixin(PlayerCommandHandler.class)
public abstract class MixinPlayerCommandHandler {
    @Inject(method = "registerCommands", at = @At(value = "RETURN"))
    public void registerCommandsMixin(Minecraft mc, CallbackInfo ci) {
        PlayerCommandHandler self = (PlayerCommandHandler)(Object) this;
        self.addCommand(new IRCToggleCommand(self));
    }

    @WrapOperation(
        method = "handleSlashCommand",
        at = @At(value = "INVOKE", target = "handleCommand")
    )
    public void handleCommandWrap(
        PlayerCommandHandler self,
        Command command,
        EntityPlayerSP commandExecutor,
        String[] commandMessage,
        Operation<Void> original
    ) {
        if (command instanceof ClientCommand || !Minecraft.getInstance().isMultiplayerWorld()) {
            original.call(self, command, commandExecutor, commandMessage);
        }
    }
}
