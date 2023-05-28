package net.merchantpug.toomanyorigins.action.factory;

import io.github.apace100.calio.data.SerializableData;

public interface IActionFactory<T> {
    
    default SerializableData getSerializableData() {
        return new SerializableData();
    }
    
    void execute(SerializableData.Instance data, T instance);
    
}
