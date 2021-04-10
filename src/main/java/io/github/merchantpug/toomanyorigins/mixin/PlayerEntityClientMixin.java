package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityClientMixin extends LivingEntity implements SkinOverlayOwner {

    public PlayerEntityClientMixin(World world) {
        super(EntityType.PLAYER, world);
    }

    @Override
    public boolean shouldRenderOverlay() {
        if (TMOPowers.RENDER_WITHER_OVERLAY.isActive(this) && this.getHealth() <= this.getMaxHealth() * 0.5) {
            return true;
        } else return this.hasStatusEffect(TMOEffects.CHARGED);
    }
}
