// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
package com.euphony.explosive_enhancement.particles.normal;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;

public class SparkParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    SparkParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.lifetime = (int) (5 + Math.floor(velX / 5));
        if (velX == 0) {
            this.quadSize = CONFIG.sparkSize;
        } else {
            this.quadSize = (float) (CONFIG.sparkSize * (velX * 0.25f));
        }
        this.setParticleSpeed(0, 0, 0);
        this.alpha = CONFIG.sparkOpacity;
        this.setSpriteFromAge(spriteProvider);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.setSpriteFromAge(this.spriteProvider);
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    //Makes the particle emissive
    @Override
    protected int getLightColor(float tint) {
        return CONFIG.emissiveExplosion ? 15728880 : super.getLightColor(tint);
    }

    public record Factory<T extends ParticleOptions>(SpriteSet sprites) implements ParticleProvider<T> {
        public Particle createParticle(T type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SparkParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
