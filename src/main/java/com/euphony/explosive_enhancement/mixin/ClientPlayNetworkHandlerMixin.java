package com.euphony.explosive_enhancement.mixin;

import com.euphony.explosive_enhancement.api.ExplosionParticleType;
import com.euphony.explosive_enhancement.api.ExplosiveApi;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow
    public abstract ClientLevel getLevel();

    @WrapOperation(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void explosiveenhancement$spawnExplosiveParticles(ClientLevel instance, ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Operation<Void> original, @Local(argsOnly = true) ClientboundExplodePacket packet) {
        boolean callOriginal = true;
        if (CONFIG.modEnabled) {
            if (CONFIG.debugLogs) {
                LOGGER.info("[Explosive Enhancement]: affectWorld has been called!");
            }

            Level world = this.getLevel();
            Vec3 pos = packet.center();
            ParticleOptions particle = packet.explosionParticle();
            float power = packet.radius();

            ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(world, pos, particle);
            if (explosionParticleType != ExplosionParticleType.WIND) { // allows normal wind particles to be shown
                ExplosiveApi.spawnParticles(world, pos.x(), pos.y(), pos.z(), power, explosionParticleType);
                boolean showVanillaParticles =
                        (CONFIG.showDefaultExplosion && explosionParticleType == ExplosionParticleType.NORMAL)
                                || (CONFIG.showDefaultExplosionUnderwater && explosionParticleType == ExplosionParticleType.WATER);

                if (!showVanillaParticles) {
                    callOriginal = false;
                }
            }
        }

        if (callOriginal) {
            original.call(instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

    @WrapOperation(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;trackExplosionEffects(Lnet/minecraft/world/phys/Vec3;FILnet/minecraft/util/random/WeightedList;)V"))
    public void explosiveenhancement$replaceVanillaSmokeParticles(ClientLevel instance, Vec3 center, float radius, int blockCount, WeightedList<ExplosionParticleInfo> blockParticles, Operation<Void> original, @Local(argsOnly = true) ClientboundExplodePacket packet) {
        boolean callOriginal = true;
        if (CONFIG.modEnabled) {
            ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(instance, center, packet.explosionParticle());

            boolean showVanillaParticles =
                    (CONFIG.showDefaultSmoke && explosionParticleType == ExplosionParticleType.NORMAL)
                            || (CONFIG.showDefaultSmokeUnderwater && explosionParticleType == ExplosionParticleType.WATER);

            if (!showVanillaParticles) {
                callOriginal = false;
            }
        }

        if (callOriginal) {
            original.call(instance, center, radius, blockCount, blockParticles);
        }
    }
}
