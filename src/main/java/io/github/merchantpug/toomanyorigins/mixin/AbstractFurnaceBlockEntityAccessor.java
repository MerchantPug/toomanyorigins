package io.github.merchantpug.toomanyorigins.mixin;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Accessor
    int getBurnTime();

    @Accessor
    int getFuelTime();

    @Accessor
    void setFuelTime(int value);

    @Accessor
    void setBurnTime(int value);
}
