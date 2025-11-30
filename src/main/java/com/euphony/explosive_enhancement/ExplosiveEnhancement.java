package com.euphony.explosive_enhancement;

import com.euphony.explosive_enhancement.config.ExplosiveConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
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

    public static final Supplier<SimpleParticleType> BLASTWAVE = registerParticle("blastwave");
    public static final Supplier<SimpleParticleType> FIREBALL = registerParticle("fireball");
    public static final Supplier<SimpleParticleType> BLANK_FIREBALL = registerParticle("blank_fireball");
    public static final Supplier<SimpleParticleType> SMOKE = registerParticle("smoke");
    public static final Supplier<SimpleParticleType> SPARKS = registerParticle("sparks");
    public static final Supplier<SimpleParticleType> BUBBLE = registerParticle("bubble");
    public static final Supplier<SimpleParticleType> SHOCKWAVE = registerParticle("shockwave");
    public static final Supplier<SimpleParticleType> BLANK_SHOCKWAVE = registerParticle("blank_shockwave");
    public static final Supplier<SimpleParticleType> UNDERWATERBLASTWAVE = registerParticle("underwaterblastwave");
    public static final Supplier<SimpleParticleType> UNDERWATERSPARKS = registerParticle("underwatersparks");

    public ExplosiveEnhancement(IEventBus modEventBus, ModContainer modContainer) {
        // NeoForge.EVENT_BUS.register(this);
        PARTICLE_TYPES.register(modEventBus);
    }

    public static DeferredHolder<ParticleType<?>, SimpleParticleType> registerParticle(String name) {
        return PARTICLE_TYPES.register(
                name,
                () -> new SimpleParticleType(false)
        );
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
