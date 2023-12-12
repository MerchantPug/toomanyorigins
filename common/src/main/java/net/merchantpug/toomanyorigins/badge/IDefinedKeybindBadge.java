/*
MIT License

Copyright (c) 2020 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package net.merchantpug.toomanyorigins.badge;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.BadgeFactory;
import net.merchantpug.toomanyorigins.registry.TMOBadges;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.LinkedList;
import java.util.List;

public interface IDefinedKeybindBadge<P> extends IBadge<P> {

    @Override
    default boolean hasTooltip() {
        return true;
    }

    static void addLines(List<ClientTooltipComponent> tooltips, Component text, Font textRenderer, int widthLimit) {
        if(textRenderer.width(text) > widthLimit) {
            for(FormattedCharSequence orderedText : textRenderer.split(text, widthLimit)) {
                tooltips.add(new ClientTextTooltip(orderedText));
            }
        } else {
            tooltips.add(new ClientTextTooltip(text.getVisualOrderText()));
        }
    }

    @Override
    default List<ClientTooltipComponent> generateTooltipComponents(P power, int widthLimit, float time, Font textRenderer) {
        List<ClientTooltipComponent> tooltips = new LinkedList<>();
        MutableComponent keyText;
        keyText = ((MutableComponent)Component.nullToEmpty("["))
                .append(KeyMapping.createNameSupplier(getKeyName()).get())
                .append(Component.nullToEmpty("]"));
        addLines(tooltips, Component.translatable(getText(), keyText), textRenderer, widthLimit);
        return tooltips;
    }

    @Override
    default SerializableData.Instance toData(SerializableData.Instance instance) {
        instance.set("sprite", spriteId());
        instance.set("text", getText());
        instance.set("key", getKey());
        return instance;
    }

    @Override
    default BadgeFactory getBadgeFactory() {
        return TMOBadges.DEFINED_KEYBIND.get();
    }

    String getText();

    <K> K getKey();

    String getKeyName();

}