package com.euphony.explosive_enhancement.particles.normal;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;

public class SmokeParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;

    SmokeParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider) {
        super(world, x, y, z);
        this.setSpriteFromAge(spriteProvider);
        this.friction = 0.6F;
        this.spriteProvider = spriteProvider;
        this.lifetime = this.random.nextInt(35) + 1;
        //velX or velZ can be either the size/power or actual velocity in that respective direction
        if (velZ == 0) {
            //for the particles going straight up
            quadSize = (float) velX * 0.25f;
            this.lifetime += (int) (velX * this.random.nextIntBetweenInclusive(3, 22));
            this.xd = 0;
            this.zd = 0;
        } else if (velX == 0.15 || velX == -0.15) {
            //for the particles where velZ is used as the power variable
            quadSize = (float) velZ * 0.25f;
            this.lifetime += (int) (velZ * this.random.nextIntBetweenInclusive(3, 22));
            this.xd = velX * (velZ * 0.5);
            this.zd = 0;
        } else if (velZ == 0.15 || velZ == -0.15) {
            //for the particles where velX is used for the power variable
            quadSize = (float) velX * 0.25f;
            this.lifetime += (int) (velX * this.random.nextIntBetweenInclusive(3, 22));
            this.xd = 0;
            this.zd = velZ * (velX * 0.5);
        }
        this.yd = velY / 1.85;
        this.gravity = 3.0E-6F;
        this.hasPhysics = true;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteProvider);
            if (this.age == 12) {
                this.xd = 0;
                this.yd = 0.05;
                this.zd = 0;
            }
            this.move(this.xd, this.yd, this.zd);
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    //Makes the particle emissive
    @Override
    protected int getLightColor(float tint) {
        if (CONFIG.emissiveExplosion && this.age <= this.lifetime * 0.12) {
            return 15728880;
        } else if (CONFIG.emissiveExplosion && this.age <= this.lifetime * 0.17) {
            return Mth.clamp(super.getLightColor(tint) + this.age + 30, super.getLightColor(tint), 15728880);
        } else {
            return super.getLightColor(tint);
        }
    }

    public record Factory<T extends ParticleOptions>(SpriteSet sprites) implements ParticleProvider<T> {
        public Particle createParticle(T type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmokeParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}