package com.scotsguy.flycreeperfly.flycreeperfly.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    @Unique
    private int eyeOffsetCooldown;

    @Unique
    private float eyeOffset;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    /*@Shadow
    protected boolean onGround;
    @Shadow public abstract Vec3d getVelocity();
    */

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "tick()V", at = @At(value = "HEAD"))
    private void flyCreeperFly(CallbackInfo ci) {
        if ((Object) this instanceof CreeperEntity) {
            if (!this.onGround && this.getVelocity().y < 0.0D) {
                this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
            }

            --this.eyeOffsetCooldown;
            if (this.eyeOffsetCooldown <= 0) {
                this.eyeOffsetCooldown = 100;
                this.eyeOffset = 0.5F + (float)this.random.nextGaussian() * 3.0F;
            }


            LivingEntity target = this.getTarget();
            if (target != null && target.getEyeY() > this.getEyeY() + (double) this.eyeOffset && this.canTarget(target)) {
                Vec3d vec3d = this.getVelocity();
                this.setVelocity(this.getVelocity().add(0.0D, (0.30000001192092896D - vec3d.y) * 0.30000001192092896D, 0.0D));
                this.velocityDirty = true;
            }

            super.mobTick();

        }
    }
    /**
     * @author AppleTheGolden
     * @reason they fly now
     */
    @Overwrite
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) { return false; }
}
