package com.euphony.explosive_enhancement.config;

import com.euphony.explosive_enhancement.ExplosiveEnhancement;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import dev.isxander.yacl3.gui.controllers.slider.DoubleSliderController;
import dev.isxander.yacl3.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class YaclIntegration {
    //? if (>=1.19.4) {
    public static Screen makeScreen(Screen parent) {
        ExplosiveConfig config = ExplosiveEnhancement.CONFIG;
        ExplosiveConfig defaults = new ExplosiveConfig();
        var yacl = YetAnotherConfigLib.createBuilder()
                .title(Component.literal("title"))
                .save(config::save);

        //Default Explosion category
        var defaultCategoryBuilder = ConfigCategory.createBuilder()
                .name(Component.translatable("explosiveenhancement.category.default"));

        var particlesNotice = LabelOption.createBuilder().lines(
                Arrays.asList(
                        Component.translatable("explosiveenhancement.particlenotice"),
                        Component.literal(""),
                        Component.translatable("explosiveenhancement.particlenoticeparttwoelectricboogaloo",
                                Minecraft.getInstance().options.particles().get().getCaption().copy().withStyle(ChatFormatting.BOLD),
                                Component.translatable(ParticleStatus.ALL.getKey()).withStyle(ChatFormatting.BOLD)))
        ).build();

        defaultCategoryBuilder.optionIf(!Minecraft.getInstance().options.particles().get().equals(ParticleStatus.ALL), particlesNotice);

        //Explosion particles group
        var explosionGroup = OptionGroup.createBuilder()
                .name(Component.translatable("explosiveenhancement.explosion.group"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.explosion.group.tooltip"))
                        .build());
        var showFireball = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.fireball.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.fireball.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.showFireball,
                        () -> config.showFireball,
                        val -> config.showFireball = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showBlastWave = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.blastwave.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.blastwave.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.showBlastWave,
                        () -> config.showBlastWave,
                        val -> config.showBlastWave = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showMushroomCloud = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.mushroomcloud.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.mushroomcloud.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.showMushroomCloud,
                        () -> config.showMushroomCloud,
                        val -> config.showMushroomCloud = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var sparkSize = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.sparks.size"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.sparks.size.tooltip"))
                        .build())
                .binding(
                        defaults.sparkSize,
                        () -> config.sparkSize,
                        val -> config.sparkSize = val
                )
                .available(true)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                .build();
        var sparkOpacity = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.sparks.opacity"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.sparks.opacity.tooltip"))
                        .build())
                .binding(
                        defaults.sparkOpacity,
                        () -> config.sparkOpacity,
                        val -> config.sparkOpacity = val
                )
                .available(true)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                .build();
        var showSparks = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.sparks.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.sparks.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.showSparks,
                        () -> config.showSparks,
                        val -> config.showSparks = val
                )
                .listener((opt, val) -> sparkSize.setAvailable(val))
                .listener((opt, val) -> sparkOpacity.setAvailable(val))
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showDefaultExplosion = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.default.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.default.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.showDefaultExplosion,
                        () -> config.showDefaultExplosion,
                        val -> config.showDefaultExplosion = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        explosionGroup.option(showFireball);
        explosionGroup.option(showBlastWave);
        explosionGroup.option(showMushroomCloud);
        explosionGroup.option(showSparks);
        explosionGroup.option(sparkSize);
        explosionGroup.option(sparkOpacity);
        explosionGroup.option(showDefaultExplosion);
        defaultCategoryBuilder.group(explosionGroup.build());

        var underwaterGroup = OptionGroup.createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.group"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.group.tooltip"))
                        .build());

        var underwaterExplosions = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.underwaterExplosions,
                        () -> config.underwaterExplosions,
                        val -> config.underwaterExplosions = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showShockwave = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.shockwave"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.shockwave.tooltip"))
                        .build())
                .binding(
                        defaults.showShockwave,
                        () -> config.showShockwave,
                        val -> config.showShockwave = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showUnderwaterBlast = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.blast"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.blast.tooltip"))
                        .build())
                .binding(
                        defaults.showUnderwaterBlastWave,
                        () -> config.showUnderwaterBlastWave,
                        val -> config.showUnderwaterBlastWave = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var bubbleAmount = Option.<Integer>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.bubbleamount"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.bubbleamount.tooltip"))
                        .text(Component.translatable("explosiveenhancement.underwater.bubbleamount.warningtooltip"))
                        .build())
                .binding(
                        defaults.bubbleAmount,
                        () -> config.bubbleAmount,
                        val -> config.bubbleAmount = val
                )
                .customController(integerOption -> new <Number>IntegerSliderController(integerOption, 0, 500, 5))
                .build();
        var underwaterSparkSize = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.sparks.size"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.sparks.size.tooltip"))
                        .build())
                .binding(
                        defaults.underwaterSparkSize,
                        () -> config.underwaterSparkSize,
                        val -> config.underwaterSparkSize = val
                )
                .available(false)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0F, 10F, 0.1F))
                .build();
        var underwaterSparkOpacity = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.sparks.opacity"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.sparks.opacity.tooltip"))
                        .build())
                .binding(
                        defaults.underwaterSparkOpacity,
                        () -> config.underwaterSparkOpacity,
                        val -> config.underwaterSparkOpacity = val
                )
                .available(false)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, 0.00F, 1.00F, 0.05F))
                .build();
        var showUnderwaterSparks = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.sparks"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.sparks.tooltip"))
                        .build())
                .binding(
                        defaults.showUnderwaterSparks,
                        () -> config.showUnderwaterSparks,
                        val -> config.showUnderwaterSparks = val
                )
                .listener((opt, val) -> underwaterSparkSize.setAvailable(val))
                .listener((opt, val) -> underwaterSparkOpacity.setAvailable(val))
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var showDefaultExplosionUnderwater = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.underwater.default"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.underwater.default.tooltip"))
                        .build())
                .binding(
                        defaults.showDefaultExplosionUnderwater,
                        () -> config.showDefaultExplosionUnderwater,
                        val -> config.showDefaultExplosionUnderwater = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        underwaterGroup.option(underwaterExplosions);
        underwaterGroup.option(showShockwave);
        underwaterGroup.option(showUnderwaterBlast);
        underwaterGroup.option(bubbleAmount);
        underwaterGroup.option(showUnderwaterSparks);
        underwaterGroup.option(underwaterSparkSize);
        underwaterGroup.option(underwaterSparkOpacity);
        underwaterGroup.option(showDefaultExplosionUnderwater);
        defaultCategoryBuilder.group(underwaterGroup.build());

        var dynamicCategoryBuilder = ConfigCategory.createBuilder()
                .name(Component.translatable("explosiveenhancement.category.dynamic"));

        var dynamicExplosionGroup = OptionGroup.createBuilder()
                .name(Component.translatable("explosiveenhancement.dynamic.group"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.dynamic.group.tooltip"))
                        .build());

        var dynamicExplosions = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.dynamicexplosions.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.dynamicexplosions.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.dynamicSize,
                        () -> config.dynamicSize,
                        val -> config.dynamicSize = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var dynamicUnderwater = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.dynamicunderwater.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.dynamicunderwater.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.dynamicUnderwater,
                        () -> config.dynamicUnderwater,
                        val -> config.dynamicUnderwater = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        var bigExtraPower = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.bigextrapower.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.bigextrapower.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.bigExtraPower,
                        () -> config.bigExtraPower,
                        val -> config.bigExtraPower = val
                )
                .available(false)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, -10F, 10F, 0.1F))
                .build();

        var smallExtraPower = Option.<Float>createBuilder()
                .name(Component.translatable("explosiveenhancement.smallextrapower.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.smallextrapower.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.smallExtraPower,
                        () -> config.smallExtraPower,
                        val -> config.smallExtraPower = val
                )
                .available(false)
                .customController(floatOption -> new <Number>FloatSliderController(floatOption, -10F, 10F, 0.1F))
                .build();

        var extraPower = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extrapower.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extrapower.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.extraPower,
                        () -> config.extraPower,
                        val -> config.extraPower = val
                )
                .listener((option, value) -> {
                    bigExtraPower.setAvailable(option.pendingValue());
                    smallExtraPower.setAvailable(option.pendingValue());
                })
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        var smallExplosionYOffset = Option.<Double>createBuilder()
                .name(Component.translatable("explosiveenhancement.yoffset"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.yoffset.tooltip"))
                        .build())
                .binding(
                        defaults.smallExplosionYOffset,
                        () -> config.smallExplosionYOffset,
                        val -> config.smallExplosionYOffset = val
                )
                .available(true)
                .customController(doubleOption -> new <Number>DoubleSliderController(doubleOption, -1.0, 0, 0.1))
                .build();
        var attemptBetterSmallExplosions = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.bettersmallexplosions.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.bettersmallexplosions.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.attemptBetterSmallExplosions,
                        () -> config.attemptBetterSmallExplosions,
                        val -> config.attemptBetterSmallExplosions = val
                )
                .listener((opt, val) -> smallExplosionYOffset.setAvailable(val))
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        var sad121_2notice = LabelOption.createBuilder().lines(
                Arrays.asList(
                        Component.literal(""),
                        Component.translatable("explosiveenhancement.121_2_sadness_message", Component.translatable("explosiveenhancement.singleplayerpowerbypass.enabled"))
                )).build();

        var bypassPowerForSingleplayer = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.singleplayerpowerbypass.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(
                                Component.translatable("explosiveenhancement.singleplayerpowerbypass.enabled.tooltip")
                        )
                        .build())
                .binding(
                        defaults.bypassPowerForSingleplayer,
                        () -> config.bypassPowerForSingleplayer,
                        val -> config.bypassPowerForSingleplayer = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        var attemptPowerKnockbackCalc = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.knockbackcalc.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(
                                Component.translatable("explosiveenhancement.knockbackcalc.enabled.tooltip")
                        )
                        .build())
                .binding(
                        defaults.attemptPowerKnockbackCalc,
                        () -> config.attemptPowerKnockbackCalc,
                        val -> config.attemptPowerKnockbackCalc = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();

        dynamicExplosionGroup.option(dynamicExplosions);
        dynamicExplosionGroup.option(dynamicUnderwater);
        //taking the easy way out for now
        //may figure out how to add this to older versions later
        //? if (>=1.21.2) {
        dynamicExplosionGroup.option(extraPower);
        dynamicExplosionGroup.option(bigExtraPower);
        dynamicExplosionGroup.option(smallExtraPower);
        //?}
        dynamicExplosionGroup.option(attemptBetterSmallExplosions);
        dynamicExplosionGroup.option(smallExplosionYOffset);
        //? if (>=1.21.2) {
        dynamicExplosionGroup.option(sad121_2notice);
        dynamicExplosionGroup.option(bypassPowerForSingleplayer);
        dynamicExplosionGroup.option(attemptPowerKnockbackCalc);
        //?}
        dynamicCategoryBuilder.group(dynamicExplosionGroup.build());


        var extrasCategoryBuilder = ConfigCategory.createBuilder()
                .name(Component.translatable("explosiveenhancement.category.extras"));

        var extrasGroup = OptionGroup.createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.group"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.group.tooltip"))
                        .build());


        var modEnabled = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.enabled"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.enabled.tooltip"))
                        .build())
                .binding(
                        defaults.modEnabled,
                        () -> config.modEnabled,
                        val -> config.modEnabled = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var emissiveExplosion = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.emissive"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.emissive.tooltip"))
                        .build())
                .binding(
                        defaults.emissiveExplosion,
                        () -> config.emissiveExplosion,
                        val -> config.emissiveExplosion = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var emissiveWaterExplosion = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.emissivewater"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.emissivewater.tooltip"))
                        .build())
                .binding(
                        defaults.emissiveWaterExplosion,
                        () -> config.emissiveWaterExplosion,
                        val -> config.emissiveWaterExplosion = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var alwaysShow = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.alwaysshow"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.alwaysshow.tooltip"))
                        .build())
                .binding(
                        defaults.alwaysShow,
                        () -> config.alwaysShow,
                        val -> config.alwaysShow = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        var debugLogs = Option.<Boolean>createBuilder()
                .name(Component.translatable("explosiveenhancement.extras.logs"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("explosiveenhancement.extras.logs.tooltip"))
                        .text(Component.translatable("explosiveenhancement.extras.logs.warningtooltip"))
                        .build())
                .binding(
                        defaults.debugLogs,
                        () -> config.debugLogs,
                        val -> config.debugLogs = val
                )
                .customController(booleanOption -> new BooleanController(booleanOption, true))
                .build();
        extrasGroup.option(modEnabled);
        extrasGroup.option(emissiveExplosion);
        extrasGroup.option(emissiveWaterExplosion);
        extrasGroup.option(alwaysShow);
        extrasGroup.option(debugLogs);
        extrasCategoryBuilder.group(extrasGroup.build());

        yacl.category(defaultCategoryBuilder.build());
        yacl.category(dynamicCategoryBuilder.build());
        yacl.category(extrasCategoryBuilder.build());

        return yacl.build().generateScreen(parent);
    }
    //?}
}
