//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.euphony.explosive_enhancement.particles.underwater;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;


public class BubbleParticle extends TextureSheetParticle {
    private final SpriteSet spriteProvider;
    int startingAirTick = 0;
    int extraTimeBeforePopping = this.random.nextIntBetweenInclusive(1, 10);
    boolean startAirTick = true;

    BubbleParticle(ClientLevel ClientLevel, double x, double y, double z, double velX, double velY, double velZ, SpriteSet spriteProvider) {
        super(ClientLevel, x, y, z);
        this.spriteProvider = spriteProvider;
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 1.5F + 0.2F;
        this.xd = velX / this.random.nextIntBetweenInclusive(1, 5);
        this.yd = velY / this.random.nextIntBetweenInclusive((int) 1.4, (int) 4.5);
        this.zd = velZ / this.random.nextIntBetweenInclusive(1, 5);
        this.lifetime = 120 + this.random.nextIntBetweenInclusive(0, 40);
        this.setSpriteFromAge(spriteProvider);
        this.age = this.lifetime;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
            this.level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        } else {
            this.yd += 0.002;
            this.move(this.xd, this.yd, this.zd);
            this.yd *= 0.8200000238418579;
            if (this.lifetime >= this.age * 0.97) {
                this.xd *= 0.8300000238418579;
                this.zd *= 0.8300000238418579;
            } else {
                this.xd *= 0.6200000238418579;
                this.zd *= 0.6200000238418579;
            }
            if (!this.level.getFluidState(new BlockPos((int) this.x, (int) this.y, (int) this.z)).is(FluidTags.WATER)) {
                this.yd -= 0.002;
                if (startAirTick) {
                    startingAirTick = this.lifetime;
                    this.yd = 0;
                    startAirTick = false;
                }
                if (!startAirTick) {
                    if (this.lifetime == startingAirTick - extraTimeBeforePopping) {
                        this.remove();
                        this.level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, this.xd, this.yd, this.zd);
                        this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.AMBIENT, 0.5f, 1f, false);
                    }
                }
            }
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory<T extends ParticleOptions> implements ParticleProvider<T> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(T defaultParticleType, ClientLevel ClientLevel, double d, double e, double f, double g, double h, double i) {
            BubbleParticle bubbleParticle = new BubbleParticle(ClientLevel, d, e, f, g, h, i, spriteProvider);
            bubbleParticle.setSpriteFromAge(this.spriteProvider);
            return bubbleParticle;
        }
    }
}