package com.euphony.explosive_enhancement.particles.normal;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class FireballParticleType extends ParticleType<FireballParticleEffect> {
    public FireballParticleType(boolean overrideLimiter) {
        super(overrideLimiter);
    }

    @Override
    public MapCodec<FireballParticleEffect> codec() {
        return FireballParticleEffect.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, FireballParticleEffect> streamCodec() {
        return FireballParticleEffect.PACKET_CODEC;
    }
}
