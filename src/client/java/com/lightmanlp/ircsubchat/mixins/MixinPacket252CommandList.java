package com.lightmanlp.ircsubchat.mixins;

import java.io.DataInputStream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lightmanlp.ircsubchat.commands.IRCToggleCommand;

import net.minecraft.mitask.PlayerCommandHandler;
import net.minecraft.src.client.packets.Packet;
import net.minecraft.src.client.packets.Packet252CommandList;

@Mixin(Packet252CommandList.class)
public abstract class MixinPacket252CommandList extends Packet {
    @Inject(method = "readPacketData", at = @At(value = "RETURN"))
    public void readPacketDataMixin(DataInputStream dataInputStream, CallbackInfo ci) {
        // shit should be resolved with custom commands register
        Packet252CommandList.commands.add(new IRCToggleCommand(PlayerCommandHandler.instance).getName());
    }
}
