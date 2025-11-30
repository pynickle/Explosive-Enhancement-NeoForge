package com.euphony.explosive_enhancement.particles.normal;

import com.euphony.explosive_enhancement.ExplosiveEnhancement;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;


public class FireballParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
    boolean important;

    FireballParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velX, velY, velZ);
        this.spriteProvider = spriteProvider;
        this.quadSize = (float) velX;
        this.lifetime = (int) (9 + Math.floor(this.quadSize / 5));
        important = CONFIG.alwaysShow;
        this.setParticleSpeed(0, 0, 0);
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
            if (this.age >= this.lifetime * 0.65 && CONFIG.showSparks) {
                this.level.addParticle(ExplosiveEnhancement.SPARKS.get(), important, this.x, this.y, this.z, this.quadSize, this.yd, this.zd);
            }
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
            return new FireballParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }

}
