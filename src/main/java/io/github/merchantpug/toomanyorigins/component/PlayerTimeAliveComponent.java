package io.github.merchantpug.toomanyorigins.component;

import net.minecraft.nbt.CompoundTag;

public class PlayerTimeAliveComponent implements TimeAliveComponent {

    private int timeAlive;

    public PlayerTimeAliveComponent(int timeAlive) {
        this.timeAlive = timeAlive;
    }

    @Override
    public int getTimeAlive() {
        return this.timeAlive;
    }

    @Override
    public void setTimeAlive(int value) {
        this.timeAlive = value;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.timeAlive = tag.getInt("TimeAlive");
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        compoundTag.putInt("TimeAlive", this.timeAlive);
    }
}
