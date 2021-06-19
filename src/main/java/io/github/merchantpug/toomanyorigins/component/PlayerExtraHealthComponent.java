package io.github.merchantpug.toomanyorigins.component;

import net.minecraft.nbt.NbtCompound;

public class PlayerExtraHealthComponent implements ExtraHealthComponent {

    private int extraHealth;
    private boolean newPlayer;

    public PlayerExtraHealthComponent(int extraHealth, boolean newPlayer) {
        this.extraHealth = extraHealth;
        this.newPlayer = newPlayer;
    }

    @Override
    public int getExtraHealth() {
        return this.extraHealth;
    }

    @Override
    public void setExtraHealth(int value) {
        this.extraHealth = value;
    }

    @Override
    public boolean getNewPlayer() {
        return this.newPlayer;
    }

    @Override
    public void setNewPlayer(boolean value) {
        this.newPlayer = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.extraHealth = tag.getInt("ExtraHealth");
        this.newPlayer = tag.getBoolean("NewPlayer");
    }

    @Override
    public void writeToNbt(NbtCompound compoundTag) {
        compoundTag.putInt("ExtraHealth", this.extraHealth);
        compoundTag.putBoolean("NewPlayer", this.newPlayer);
    }
}
