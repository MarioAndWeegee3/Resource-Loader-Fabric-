package marioandweegee3.resourceloader.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import marioandweegee3.resourceloader.packs.LoadedPackProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientResourcePackProfile;
import net.minecraft.resource.ResourcePackManager;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MCClientMixin {
    @Final @Shadow private ResourcePackManager<ClientResourcePackProfile> resourcePackManager;

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private Thread registerLoader(){
        this.resourcePackManager.registerProvider(new LoadedPackProvider());
        return Thread.currentThread();
    }
}