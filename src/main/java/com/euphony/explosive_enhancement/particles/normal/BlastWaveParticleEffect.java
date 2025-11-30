package com.euphony.explosive_enhancement.particles.normal;

import com.euphony.explosive_enhancement.ExplosiveEnhancement;
import com.euphony.explosive_enhancement.particles.AbstractExplosiveParticleEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class BlastWaveParticleEffect extends AbstractExplosiveParticleEffect {
    public static final MapCodec<BlastWaveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> createDefaultWaterCodec(instance).apply(instance, BlastWaveParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BlastWaveParticleEffect> PACKET_CODEC = createWaterStreamCodec(BlastWaveParticleEffect::new);

    public BlastWaveParticleEffect(boolean water, float scale, boolean emissive) {
        super(water, scale, emissive);
    }

    @Override
    public ParticleType<?> getType() {
        // This is incredibly cursed only for the fact that it isn't quite expected,
        // but it does work ¯\_(ツ)_/¯
        return this.isWater() ? ExplosiveEnhancement.WATER_BLASTWAVE.get() : ExplosiveEnhancement.BLASTWAVE.get();
    }
}
