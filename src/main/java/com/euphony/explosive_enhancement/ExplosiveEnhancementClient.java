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
        if (FMLLoader.getCurrent().isProduction() && !YaclLoaded()) {
            ExplosiveEnhancement.LOGGER.warn("[Explosive Enhancement]: YetAnotherConfigLib is not installed! If you wish to edit Explosive Enhancement's config, please install it!");
        }

        if (ExplosiveEnhancementClient.YaclLoaded()) {
            ModLoadingContext.get()
                    .registerExtensionPoint(
                            IConfigScreenFactory.class, () -> (client, screen) -> YaclIntegration.makeScreen(screen));
        }
    }

    public static boolean YaclLoaded() {
        return FMLLoader.getCurrent().getLoadingModList().getMods().stream().anyMatch(modInfo -> modInfo.getModId().equals("yet_another_config_lib_v3"));
    }
}
