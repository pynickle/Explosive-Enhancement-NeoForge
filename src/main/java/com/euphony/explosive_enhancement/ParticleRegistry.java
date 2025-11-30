package com.euphony.explosive_enhancement;

import com.euphony.explosive_enhancement.particles.normal.BlastWaveParticle;
import com.euphony.explosive_enhancement.particles.normal.FireballParticle;
import com.euphony.explosive_enhancement.particles.normal.SmokeParticle;
import com.euphony.explosive_enhancement.particles.normal.SparkParticle;
import com.euphony.explosive_enhancement.particles.underwater.BubbleParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ExplosiveEnhancement.MODID, value = Dist.CLIENT)
public class ParticleRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ExplosiveEnhancement.BLASTWAVE.get(), BlastWaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.FIREBALL.get(), FireballParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SPARKS.get(), SparkParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SMOKE.get(), SmokeParticle.Factory::new);

        event.registerSpriteSet(ExplosiveEnhancement.WATER_BLASTWAVE.get(), BlastWaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SHOCKWAVE.get(), FireballParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.WATER_SPARKS.get(), SparkParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.BUBBLE.get(), BubbleParticle.Factory::new);
    }
}
