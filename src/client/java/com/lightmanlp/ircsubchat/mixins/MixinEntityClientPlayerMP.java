package com.lightmanlp.ircsubchat.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.client.Session;
import net.minecraft.src.client.player.EntityClientPlayerMP;
import net.minecraft.src.client.player.EntityPlayerSP;
import net.minecraft.src.game.level.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lightmanlp.ircsubchat.ChatProcessor;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

@Mixin(EntityClientPlayerMP.class)
public abstract class MixinEntityClientPlayerMP extends EntityPlayerSP {
    public MixinEntityClientPlayerMP(Minecraft minecraft, World world, Session session, int arg4) {
        super(minecraft, world, session, arg4);
    }

    @Inject(
        method = "sendChatMessage",
        at = @At(value = "HEAD"),
        cancellable = true
    )
    public void sendChatMessageMixin(String msg, CallbackInfo ci, @Local LocalRef<String> msgRef) {
        String result = ChatProcessor.get().process(msg);
        if (result == null) {
            ci.cancel();
        } else {
            msgRef.set(result);
        }
    }
}
