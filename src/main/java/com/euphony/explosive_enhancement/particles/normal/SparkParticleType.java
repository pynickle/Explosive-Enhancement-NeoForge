package com.euphony.explosive_enhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class SparkParticleType extends ParticleType<SparkParticleEffect> {
    public SparkParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }

    @Override
    public MapCodec<SparkParticleEffect> codec() {
        return SparkParticleEffect.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, SparkParticleEffect> streamCodec() {
        return SparkParticleEffect.PACKET_CODEC;
    }
}
