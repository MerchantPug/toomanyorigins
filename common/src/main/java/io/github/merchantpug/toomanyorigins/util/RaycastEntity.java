package io.github.merchantpug.toomanyorigins.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class RaycastEntity {
    @Nullable
    public static EntityHitResult raycast(Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate, double d) {
        World world = entity.world;
        double e = d;
        Entity entity2 = null;
        Vec3d vec3d3 = null;
        Iterator var12 = world.getOtherEntities(entity, box, predicate).iterator();

        while(true) {
            while(var12.hasNext()) {
                Entity entity3 = (Entity)var12.next();
                Box box2 = entity3.getBoundingBox().expand((double)entity3.getTargetingMargin());
                Optional<Vec3d> optional = box2.raycast(vec3d, vec3d2);
                if (box2.contains(vec3d)) {
                    if (e >= 0.0D) {
                        entity2 = entity3;
                        vec3d3 = (Vec3d)optional.orElse(vec3d);
                        e = 0.0D;
                    }
                } else if (optional.isPresent()) {
                    Vec3d vec3d4 = (Vec3d)optional.get();
                    double f = vec3d.squaredDistanceTo(vec3d4);
                    if (f < e || e == 0.0D) {
                        if (entity3.getRootVehicle() == entity.getRootVehicle()) {
                            if (e == 0.0D) {
                                entity2 = entity3;
                                vec3d3 = vec3d4;
                            }
                        } else {
                            entity2 = entity3;
                            vec3d3 = vec3d4;
                            e = f;
                        }
                    }
                }
            }

            if (entity2 == null) {
                return null;
            }

            return new EntityHitResult(entity2, vec3d3);
        }
    }
}
