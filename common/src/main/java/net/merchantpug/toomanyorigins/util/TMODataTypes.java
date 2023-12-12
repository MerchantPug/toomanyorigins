package net.merchantpug.toomanyorigins.util;

import com.mojang.datafixers.util.Either;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TMODataTypes {

    public static final SerializableDataType<GuiBackground> GUI_BACKGROUND = SerializableDataType.compound(GuiBackground.class, new SerializableData()
                    .add("texture_location", SerializableDataTypes.IDENTIFIER)
                    .add("u_offset", SerializableDataTypes.FLOAT, 0.0F)
                    .add("v_offset", SerializableDataTypes.FLOAT, 0.0F)
                    .add("u_width", SerializableDataTypes.INT, -1)
                    .add("v_height", SerializableDataTypes.INT, -1),
            data -> new GuiBackground(data.getId("texture_location"),
                    data.getFloat("u_offset"), data.getFloat("v_offset"),
                    data.getInt("u_width"), data.getInt("v_height")),
            (data, background) -> {
                SerializableData.Instance inst = data.new Instance();
                inst.set("texture_location", background.location());
                inst.set("u_offset", background.uOffset());
                inst.set("v_offset", background.vOffset());
                inst.set("u_width", background.uWidth());
                inst.set("v_height", background.vHeight());
                return inst;
            });

    public static final SerializableDataType<GuiContent> SINGLE_GUI_CONTENT = SerializableDataType.compound(GuiContent.class, new SerializableData()
                    .add("texture_location", SerializableDataTypes.IDENTIFIER, null)
                    .add("stack", SerializableDataTypes.ITEM_STACK, null)
                    .add("item_tag", SerializableDataTypes.ITEM_TAG, null)
                    .add("x", SerializableDataTypes.INT, 0)
                    .add("y", SerializableDataTypes.INT, 0)
                    .add("u_offset", SerializableDataTypes.FLOAT, 0.0F)
                    .add("v_offset", SerializableDataTypes.FLOAT, 0.0F)
                    .add("u_width", SerializableDataTypes.INT, -1)
                    .add("v_height", SerializableDataTypes.INT, -1),
            data -> {
                Either<ResourceLocation, Either<ItemStack, TagKey<Item>>> either;
                if (data.isPresent("texture_location")) {
                    either = Either.left(data.getId("texture_location"));
                } else if (data.isPresent("stack")) {
                    either = Either.right(Either.left(data.get("stack")));
                } else if (data.isPresent("item_tag")) {
                    either = Either.right(Either.right(data.get("item_tag")));
                } else {
                    throw new NullPointerException("Gui Content data type requires either `texture_location` or `stack` field.");
                }
                return new GuiContent(either,
                        data.getInt("x"), data.getInt("y"),
                        data.getFloat("u_offset"), data.getFloat("v_offset"),
                        data.getInt("u_width"), data.getInt("v_height"));
            },
            (data, content) -> {
                SerializableData.Instance inst = data.new Instance();
                if (content.content().left().isPresent()) {
                    inst.set("texture_location", content.content().left().get());
                } else {
                    inst.set("texture_location", null);
                }
                if (content.content().right().isPresent()) {
                    if (content.content().right().get().left().isPresent()) {
                        inst.set("stack", content.content().right().get().left().get());
                        inst.set("item_tag", null);
                    } else if (content.content().right().get().right().isPresent()) {
                        inst.set("item_tag", content.content().right().get().right().get());
                        inst.set("stack", null);
                    } else {
                        inst.set("stack", null);
                        inst.set("item_tag", null);
                    }
                } else {
                    inst.set("stack", null);
                    inst.set("item_tag", null);
                }
                inst.set("x", content.x());
                inst.set("y", content.y());
                inst.set("u_offset", content.uOffset());
                inst.set("v_offset", content.vOffset());
                inst.set("u_width", content.uWidth());
                inst.set("v_height", content.vHeight());
                return inst;
            });

    public static final SerializableDataType<List<GuiContent>> GUI_CONTENT_LIST = SerializableDataType.list(SINGLE_GUI_CONTENT);

}
