package com.euphony.explosive_enhancement;

import com.euphony.explosive_enhancement.config.ExplosiveConfig;
import com.euphony.explosive_enhancement.particles.normal.BlastWaveParticleType;
import com.euphony.explosive_enhancement.particles.normal.FireballParticleType;
import com.euphony.explosive_enhancement.particles.normal.SmokeParticleType;
import com.euphony.explosive_enhancement.particles.normal.SparkParticleType;
import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Mod(ExplosiveEnhancement.MODID)
public class ExplosiveEnhancement {
    public static final String MODID = "explosiveenhancement";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ExplosiveConfig CONFIG = ExplosiveConfig.INSTANCE;

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final Supplier<BlastWaveParticleType> BLASTWAVE = PARTICLE_TYPES.register("blastwave", () -> new BlastWaveParticleType(false));
    public static final Supplier<FireballParticleType> FIREBALL = PARTICLE_TYPES.register("fireball", () -> new FireballParticleType(false));
    public static final Supplier<SparkParticleType> SPARKS = PARTICLE_TYPES.register("sparks", () -> new SparkParticleType(false));
    public static final Supplier<SmokeParticleType> SMOKE = PARTICLE_TYPES.register("smoke", () -> new SmokeParticleType(false));

    public static final Supplier<BlastWaveParticleType> WATER_BLASTWAVE = PARTICLE_TYPES.register("underwaterblastwave", () -> new BlastWaveParticleType(false));
    public static final Supplier<FireballParticleType> SHOCKWAVE = PARTICLE_TYPES.register("shockwave", () -> new FireballParticleType(false));
    public static final Supplier<SparkParticleType> WATER_SPARKS = PARTICLE_TYPES.register("underwatersparks", () -> new SparkParticleType(false));
    public static final Supplier<SimpleParticleType> BUBBLE = PARTICLE_TYPES.register("bubble", () -> new SimpleParticleType(false));

    public ExplosiveEnhancement(IEventBus modEventBus, ModContainer modContainer) {
        PARTICLE_TYPES.register(modEventBus);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
