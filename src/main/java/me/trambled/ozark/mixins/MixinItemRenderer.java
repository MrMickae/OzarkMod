package me.trambled.ozark.mixins;

import me.trambled.ozark.Ozark;
import me.trambled.ozark.ozarkclient.event.Eventbus;
import me.trambled.ozark.ozarkclient.event.events.EventTransformSideFirstPerson;
import me.trambled.ozark.ozarkclient.module.combat.Aura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @Shadow
    private float equippedProgress;

    @Shadow
    public abstract void transformFirstPersonItem(float equipProgress, float swingProgress);

    @Shadow
    public abstract void rotateArroundXAndY(float angle, float angleY);

    @Shadow
    public abstract void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress);

    @Shadow
    private float prevEquippedProgress;

    @Shadow
    public abstract void setLightMapFromPlayer(AbstractClientPlayer clientPlayer);

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    public abstract void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);

    @Shadow
    public abstract void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks);

    @Shadow
    public abstract void doBlockTransformations();

    @Shadow
    public abstract void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer);

    @Shadow
    public abstract void doItemUsedTransformations(float swingProgress);

    @Shadow
    public abstract void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform);

    @Shadow
    public abstract void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress);

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo callbackInfo) {
        EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(hand);
        Eventbus.EVENT_BUS.post(event);
    }

    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo callbackInfo) {
        EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(hand);
        Eventbus.EVENT_BUS.post(event);

        if (Ozark.get_module_manager().get_module_with_tag("CustomViewmodel").is_active() && Ozark.get_setting_manager().get_setting_with_tag("CustomViewmodel", "FOVCancelEating").get_value(true)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo callbackInfo) {
        EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(hand);
        Eventbus.EVENT_BUS.post(event);
    }
}
