package com.euphony.explosive_enhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class BlastWaveParticleType extends ParticleType<BlastWaveParticleEffect> {
    public BlastWaveParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }

    @Override
    public MapCodec<BlastWaveParticleEffect> codec() {
        return BlastWaveParticleEffect.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, BlastWaveParticleEffect> streamCodec() {
        return BlastWaveParticleEffect.PACKET_CODEC;
    }
}
