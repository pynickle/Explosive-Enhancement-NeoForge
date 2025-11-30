package com.euphony.explosive_enhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class SmokeParticleType extends ParticleType<SmokeParticleEffect> {
    public SmokeParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }

    @Override
    public MapCodec<SmokeParticleEffect> codec() {
        return SmokeParticleEffect.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, SmokeParticleEffect> streamCodec() {
        return SmokeParticleEffect.PACKET_CODEC;
    }
}
