package net.merchantpug.toomanyorigins.badge;

import com.google.auto.service.AutoService;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.Badge;
import net.merchantpug.toomanyorigins.badge.factory.IDefinedKeybindBadgeFactory;
import net.merchantpug.toomanyorigins.badge.factory.IGuiBadgeFactory;
import net.merchantpug.toomanyorigins.util.GuiBackground;
import net.merchantpug.toomanyorigins.util.GuiContent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Function;

public record GuiBadge(SerializableData.Instance data) implements IGuiBadge<PowerType<?>> {


    @Override
    public GuiBackground getBackground() {
        return data.get("background");
    }

    @Override
    public List<List<GuiContent>> getContent() {
        return data.get("content");
    }

    @Override
    public ResourceLocation spriteId() {
        return data.getId("sprite");
    }

    @Override
    public List<ClientTooltipComponent> getTooltipComponents(PowerType<?> powerType, int i, float v, Font font) {
        return IGuiBadge.super.generateTooltipComponents(powerType, i, v, font);
    }

    @AutoService(IGuiBadgeFactory.class)
    public static class Factory implements IGuiBadgeFactory {

        public Factory() {

        }

        @Override
        public Function<SerializableData.Instance, Badge> getFactoryCreator() {
            return GuiBadge::new;
        }
    }
}
