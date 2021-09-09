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

package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.Registry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Lazy;

public class TMORegistriesArchitectury {
    public static final Lazy<Registries> REGISTRIES = new Lazy<>(() -> Registries.get(TooManyOrigins.MODID));

    public static final Registry<Item> ITEMS;
    public static final Registry<Block> BLOCKS;
    public static final Registry<EntityType<?>> ENTITY_TYPES;
    public static final Registry<StatusEffect> STATUS_EFFECTS;
    public static final Registry<SoundEvent> SOUNDS;

    public TMORegistriesArchitectury() {

    }

    static {
        Registries registries = REGISTRIES.get();
        ITEMS = registries.get(net.minecraft.util.registry.Registry.ITEM_KEY);
        BLOCKS = registries.get(net.minecraft.util.registry.Registry.BLOCK_KEY);
        ENTITY_TYPES = registries.get(net.minecraft.util.registry.Registry.ENTITY_TYPE_KEY);
        STATUS_EFFECTS = registries.get(net.minecraft.util.registry.Registry.MOB_EFFECT_KEY);
        SOUNDS = registries.get(net.minecraft.util.registry.Registry.SOUND_EVENT_KEY);
    }
}
