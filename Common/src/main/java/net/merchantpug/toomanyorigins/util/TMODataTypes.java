package net.merchantpug.toomanyorigins.util;

import com.mojang.datafixers.util.Either;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TMODataTypes {

    public static final SerializableDataType<GuiBackground> GUI_BACKGROUND = SerializableDataType.compound(GuiBackground.class, new SerializableData()
                    .add("texture_location", SerializableDataTypes.IDENTIFIER)
                    .add("u", SerializableDataTypes.INT, 0)
                    .add("v", SerializableDataTypes.INT, 0)
                    .add("texture_width", SerializableDataTypes.INT, -1)
                    .add("texture_height", SerializableDataTypes.INT, -1),
            data -> new GuiBackground(data.getId("texture_location"),
                    data.getInt("u"), data.getInt("v"),
                    data.getInt("texture_width"), data.getInt("texture_height")),
            (data, background) -> {
                SerializableData.Instance inst = data.new Instance();
                inst.set("texture_location", background.location());
                inst.set("u", background.u());
                inst.set("v", background.v());
                inst.set("texture_width", background.textureWidth());
                inst.set("texture_height", background.textureHeight());
                return inst;
            });

    public static final SerializableDataType<GuiContent> SINGLE_GUI_CONTENT = SerializableDataType.compound(GuiContent.class, new SerializableData()
                    .add("texture_location", SerializableDataTypes.IDENTIFIER, null)
                    .add("stack", SerializableDataTypes.ITEM_STACK, null)
                    .add("x", SerializableDataTypes.INT, 0)
                    .add("y", SerializableDataTypes.INT, 0)
                    .add("u", SerializableDataTypes.INT, 0)
                    .add("v", SerializableDataTypes.INT, 0)
                    .add("texture_width", SerializableDataTypes.INT, -1)
                    .add("texture_height", SerializableDataTypes.INT, -1),
            data -> {
                Either<ResourceLocation, ItemStack> either;
                if (data.isPresent("texture_location")) {
                    either = Either.left(data.getId("texture_location"));
                } else if (data.isPresent("stack")) {
                    either = Either.right(data.get("stack"));
                } else {
                    throw new NullPointerException("Gui Content data type requires either `texture_location` or `stack` field.");
                }
                return new GuiContent(either,
                        data.getInt("x"), data.getInt("y"),
                        data.getInt("u"), data.getInt("v"),
                        data.getInt("texture_width"), data.getInt("texture_height"));
            },
            (data, texture) -> {
                SerializableData.Instance inst = data.new Instance();
                inst.set("texture_location", texture.content().left().orElse(null));
                inst.set("stack", texture.content().right().orElse(null));
                inst.set("x", texture.x());
                inst.set("y", texture.y());
                inst.set("u", texture.u());
                inst.set("v", texture.v());
                inst.set("texture_width", texture.textureWidth());
                inst.set("texture_height", texture.textureHeight());
                return inst;
            });

    public static final SerializableDataType<List<GuiContent>> GUI_CONTENT_LIST = SerializableDataType.list(SINGLE_GUI_CONTENT);

}
