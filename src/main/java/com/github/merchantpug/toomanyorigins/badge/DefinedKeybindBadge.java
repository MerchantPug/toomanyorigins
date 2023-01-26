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

package com.github.merchantpug.toomanyorigins.badge;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.registry.TMOBadges;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.badge.Badge;
import io.github.apace100.origins.badge.BadgeFactory;
import io.github.apace100.origins.util.PowerKeyManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public record DefinedKeybindBadge(Identifier spriteId, String text, Active.Key key) implements Badge {
    public DefinedKeybindBadge(SerializableData.Instance instance) {
        this(instance.getId("sprite"), instance.get("text"), instance.get("key"));
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    public static void addLines(List<TooltipComponent> tooltips, Text text, TextRenderer textRenderer, int widthLimit) {
        if(textRenderer.getWidth(text) > widthLimit) {
            for(OrderedText orderedText : textRenderer.wrapLines(text, widthLimit)) {
                tooltips.add(new OrderedTextTooltipComponent(orderedText));
            }
        } else {
            tooltips.add(new OrderedTextTooltipComponent(text.asOrderedText()));
        }
    }

    @Override
    public List<TooltipComponent> getTooltipComponents(PowerType<?> powerType, int widthLimit, float time, TextRenderer textRenderer) {
        List<TooltipComponent> tooltips = new LinkedList<>();
        Text keyText;
        keyText = ((MutableText)Text.of("["))
                .append(KeyBinding.getLocalizedName(key.key).get())
                .append(Text.of("]"));
        addLines(tooltips, Text.translatable(text, keyText), textRenderer, widthLimit);
        return tooltips;
    }

    @Override
    public SerializableData.Instance toData(SerializableData.Instance instance) {
        instance.set("sprite", spriteId);
        instance.set("text", text);
        instance.set("key", key);
        return instance;
    }

    @Override
    public BadgeFactory getBadgeFactory() {
        return TMOBadges.DEFINED_KEYBIND;
    }
}
