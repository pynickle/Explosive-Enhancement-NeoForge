// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
package com.euphony.explosive_enhancement.particles.underwater;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;

public class UnderwaterSparkParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    UnderwaterSparkParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.lifetime = (int) (5 + Math.floor(velX / 5));
        if (velX == 0) {
            this.quadSize = CONFIG.underwaterSparkSize;
        } else {
            this.quadSize = (float) (CONFIG.underwaterSparkSize * (velX * 0.25f));
        }
        this.setParticleSpeed(0, 0, 0);
        this.alpha = CONFIG.underwaterSparkOpacity;
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
        return CONFIG.emissiveWaterExplosion ? 15728880 : super.getLightColor(tint);
    }

    public record Factory<T extends ParticleOptions>(SpriteSet sprites) implements ParticleProvider<T> {
        public Particle createParticle(T type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new UnderwaterSparkParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
