package com.euphony.explosive_enhancement.mixin;

import com.euphony.explosive_enhancement.api.ExplosionParticleType;
import com.euphony.explosive_enhancement.api.ExplosiveApi;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;
import static com.euphony.explosive_enhancement.ExplosiveEnhancement.LOGGER;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private Level level;
    @Shadow
    @Final
    private double x;
    @Shadow
    @Final
    private double y;
    @Shadow
    @Final
    private double z;
    @Shadow
    @Final
    private float radius;
    @Shadow
    @Final
    private ParticleOptions smallExplosionParticles;
    @Shadow
    @Final
    private ParticleOptions largeExplosionParticles;

    @Shadow
    public abstract boolean interactsWithBlocks();

    @Inject(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), cancellable = true)
    public void explosiveenhancement$spawnExplosiveParticles(boolean particles, CallbackInfo ci) {
        if (CONFIG.modEnabled && particles) {
            if (CONFIG.debugLogs) {
                LOGGER.info("[Explosive Enhancement]: affectWorld has been called!");
            }


            ExplosionParticleType explosionParticleType = ExplosiveApi.determineParticleType(level, x, y, z, smallExplosionParticles, largeExplosionParticles);
            if (explosionParticleType != ExplosionParticleType.WIND) { //allows normal wind particles to be shown

                boolean destroyedBlocks = this.interactsWithBlocks();

                ExplosiveApi.spawnParticles(level, x, y, z, radius, explosionParticleType, destroyedBlocks);
                ci.cancel();
            }
        }
    }
}