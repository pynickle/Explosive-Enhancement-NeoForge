package com.euphony.explosive_enhancement;

import com.euphony.explosive_enhancement.config.ExplosiveConfig;
import com.euphony.explosive_enhancement.config.YaclIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ExplosiveEnhancement.MODID, dist = Dist.CLIENT)
public class ExplosiveEnhancementClient {
    public ExplosiveEnhancementClient(IEventBus modEventBus, ModContainer modContainer) {
        // Loads the config, GUI powered by YACL
        ExplosiveConfig.load();
        if (FMLLoader.isProduction() && !YaclLoaded()) {
            ExplosiveEnhancement.LOGGER.warn("[Explosive Enhancement]: YetAnotherConfigLib is not installed! If you wish to edit Explosive Enhancement's config, please install it!");
        }

        if (ExplosiveEnhancementClient.YaclLoaded()) {
            ModLoadingContext.get()
                    .registerExtensionPoint(
                            IConfigScreenFactory.class, () -> (client, screen) -> YaclIntegration.makeScreen(screen));
        }
    }

    /*
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ExplosiveEnhancement.BLASTWAVE.get(), BlastWaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.FIREBALL.get(), FireballParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.BLANK_FIREBALL.get(), FireballParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SMOKE.get(), SmokeParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SPARKS.get(), SparkParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.BUBBLE.get(), BubbleParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.SHOCKWAVE.get(), ShockwaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.BLANK_SHOCKWAVE.get(), ShockwaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.UNDERWATERBLASTWAVE.get(), UnderwaterBlastWaveParticle.Factory::new);
        event.registerSpriteSet(ExplosiveEnhancement.UNDERWATERSPARKS.get(), UnderwaterSparkParticle.Factory::new);
    }

     */

    public static boolean YaclLoaded() {
        return FMLLoader.getLoadingModList().getMods().stream().anyMatch(modInfo -> modInfo.getModId().equals("yet_another_config_lib_v3"));
    }
}
