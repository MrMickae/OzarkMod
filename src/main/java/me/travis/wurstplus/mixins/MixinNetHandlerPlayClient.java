package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Redirect(method = { "handleEntityStatus" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V"))
    private void playTotemSound(final WorldClient worldClient, final double x, final double y, final double z, final SoundEvent soundIn, final SoundCategory category, final float volume, final float pitch, final boolean distanceDelay) {
        if (!Wurstplus.get_hack_manager().get_module_with_tag("AntiSound").is_active() || !Wurstplus.get_setting_manager().get_setting_with_tag("AntiSound", "Totem").get_value(true)) {
            worldClient.playSound(x, y, z, soundIn, category, volume, pitch, distanceDelay);
        }
    }
}
