package com.euphony.explosive_enhancement.particles.normal;

import com.euphony.explosive_enhancement.ExplosiveEnhancement;
import com.euphony.explosive_enhancement.particles.AbstractExplosiveParticleEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class FireballParticleEffect extends AbstractExplosiveParticleEffect {

    public static final MapCodec<FireballParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    getWaterCodec(),
                    getScaleCodec(),
                    getEmissiveCodec(),
                    Codec.BOOL.optionalFieldOf("sparks", false).forGetter(FireballParticleEffect::isSparks),
                    Codec.BOOL.optionalFieldOf("sparks_only", false).forGetter(FireballParticleEffect::isSparksOnly),
                    Codec.BOOL.optionalFieldOf("sparks_important", false).forGetter(FireballParticleEffect::isSparksImportant)
            ).apply(instance, FireballParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FireballParticleEffect> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, FireballParticleEffect::isWater,
            ByteBufCodecs.FLOAT, FireballParticleEffect::getScale,
            ByteBufCodecs.BOOL, FireballParticleEffect::isEmissive,
            ByteBufCodecs.BOOL, FireballParticleEffect::isSparks,
            ByteBufCodecs.BOOL, FireballParticleEffect::isSparksOnly,
            ByteBufCodecs.BOOL, FireballParticleEffect::isSparksImportant,
            FireballParticleEffect::new
    );

    private final boolean sparks;
    private final boolean sparksOnly;
    private final boolean sparksImportant;

    public FireballParticleEffect(boolean water, float scale, boolean emissive, boolean sparks, boolean sparksOnly, boolean sparksImportant) {
        super(water, scale, emissive);
        this.sparks = sparks;
        this.sparksOnly = sparksOnly;
        this.sparksImportant = sparksImportant;
    }

    public boolean isSparks() {
        return sparks;
    }

    public boolean isSparksOnly() {
        return sparksOnly;
    }

    public boolean isSparksImportant() {
        return sparksImportant;
    }

    @Override
    public ParticleType<?> getType() {
        return this.isWater() ? ExplosiveEnhancement.SHOCKWAVE.get() : ExplosiveEnhancement.FIREBALL.get();
    }
}
