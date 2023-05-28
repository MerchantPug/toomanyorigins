package net.merchantpug.toomanyorigins.action.factory.entity;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.action.factory.IActionFactory;
import net.merchantpug.toomanyorigins.networking.c2s.ExhaustPacket;
import net.merchantpug.toomanyorigins.platform.Services;
import net.minecraft.world.entity.Entity;

public class ExhaustToServerAction implements IActionFactory<Entity> {
    
    @Override
    public SerializableData getSerializableData() {
        return new SerializableData()
            .add("amount", SerializableDataTypes.FLOAT);
    }
    
    @Override
    public void execute(SerializableData.Instance data, Entity entity) {
        if (entity.level.isClientSide) {
            Services.PLATFORM.sendC2S(new ExhaustPacket(data.getFloat("amount")));
        }
    }

}
