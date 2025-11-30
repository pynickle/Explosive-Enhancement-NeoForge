// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
// TODO(Ravel): Failed to fully resolve file: null
package com.euphony.explosive_enhancement.particles.underwater;

import com.euphony.explosive_enhancement.particles.normal.BlastWaveParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;

import static com.euphony.explosive_enhancement.ExplosiveEnhancement.CONFIG;

public class UnderwaterBlastWaveParticle extends BlastWaveParticle {
    public UnderwaterBlastWaveParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ, SpriteSet sprites) {
        super(world, x, y, z, velX, velY, velZ, sprites);
    }

    //Makes the particle emissive
    //Doesn't use super.getLightColor because that would cause the particle to appear emissive if
    //emissive underwater explosion is turned off and emissive explosion is turned on
    @Override
    protected int getLightColor(float tint) {
        BlockPos blockPos = BlockPos.containing(x, y, z);
        return CONFIG.emissiveWaterExplosion ? 15728880 : this.level.isLoaded(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
    }

    public record Factory<T extends ParticleOptions>(SpriteSet sprites) implements ParticleProvider<T> {
        public Particle createParticle(T type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new UnderwaterBlastWaveParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
