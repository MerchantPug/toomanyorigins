package net.merchantpug.toomanyorigins.client.particle;

import net.merchantpug.toomanyorigins.content.particle.CustomDragonBreathParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class CustomDragonBreathParticle extends TextureSheetParticle {
   private boolean hasHitGround;
   private final SpriteSet sprites;

   CustomDragonBreathParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites, Vector3f color) {
      super(pLevel, pX, pY, pZ);
      this.friction = 0.96F;
      this.xd = pXSpeed;
      this.yd = pYSpeed;
      this.zd = pZSpeed;
      this.rCol = Mth.nextFloat(this.random, color.x() * 0.85F, color.x());
      this.gCol = Mth.nextFloat(this.random, color.y() * 0.85F, color.y());
      this.bCol = Mth.nextFloat(this.random, color.z() * 0.85F, color.z());
      this.quadSize *= 0.75F;
      this.lifetime = (int)(20.0D / ((double)this.random.nextFloat() * 0.8D + 0.2D));
      this.hasHitGround = false;
      this.hasPhysics = false;
      this.sprites = pSprites;
      this.setSpriteFromAge(pSprites);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
         if (this.onGround) {
            this.yd = 0.0D;
            this.hasHitGround = true;
         }

         if (this.hasHitGround) {
            this.yd += 0.002D;
         }

         this.move(this.xd, this.yd, this.zd);
         if (this.y == this.yo) {
            this.xd *= 1.1D;
            this.zd *= 1.1D;
         }

         this.xd *= this.friction;
         this.zd *= this.friction;
         if (this.hasHitGround) {
            this.yd *= this.friction;
         }

      }
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getQuadSize(float pScaleFactor) {
      return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public static class Provider implements ParticleProvider<CustomDragonBreathParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet pSprites) {
         this.sprites = pSprites;
      }

      public Particle createParticle(CustomDragonBreathParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
          Vector3f color = pType.color();
          return new CustomDragonBreathParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites, color);
      }
   }

}