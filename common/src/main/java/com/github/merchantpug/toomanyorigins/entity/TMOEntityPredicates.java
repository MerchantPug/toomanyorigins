package com.github.merchantpug.toomanyorigins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Predicate;

public final class TMOEntityPredicates {

    public static final Predicate<Entity> EXCEPT_PLAYER = (entity) -> !(entity instanceof PlayerEntity);
}
