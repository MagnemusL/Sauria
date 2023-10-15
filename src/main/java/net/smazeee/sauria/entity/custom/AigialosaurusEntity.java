package net.smazeee.sauria.entity.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.smazeee.sauria.Sauria;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class AigialosaurusEntity extends Monster implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);
    public static final EntityDataAccessor<Integer> DATA_ATTACKED_MOBS = SynchedEntityData.defineId(AigialosaurusEntity.class, EntityDataSerializers.INT);
    public static boolean isHungry = false;

    public AigialosaurusEntity(EntityType<? extends Monster> p_30132_, Level p_30133_) {
        super(p_30132_, p_30133_);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DOOR_OPEN, -1.0F);
        this.moveControl = new AigialosaurusEntity.AigialosaurusMoveControl(this);
        this.maxUpStep = 1.0F;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    public int getAmbientSoundInterval() {
        return 200;
    }

    @javax.annotation.Nullable
    protected SoundEvent getAmbientSound() {
        return !this.isInWater() && this.onGround && !this.isBaby() ? SoundEvents.TURTLE_AMBIENT_LAND : super.getAmbientSound();
    }

    protected void playSwimSound(float p_30192_) {
        super.playSwimSound(p_30192_ * 1.5F);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.TURTLE_SWIM;
    }

    @javax.annotation.Nullable
    protected SoundEvent getHurtSound(DamageSource p_30202_) {
        return this.isBaby() ? SoundEvents.TURTLE_HURT_BABY : SoundEvents.TURTLE_HURT;
    }

    @javax.annotation.Nullable
    protected SoundEvent getDeathSound() {
        return this.isBaby() ? SoundEvents.TURTLE_DEATH_BABY : SoundEvents.TURTLE_DEATH;
    }

    protected void playStepSound(BlockPos p_30173_, BlockState p_30174_) {
        SoundEvent soundevent = this.isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
        this.playSound(soundevent, 0.15F, 1.0F);
    }

    protected PathNavigation createNavigation(Level p_30171_) {
        return new AigialosaurusEntity.AigialosaurusPathNavigation(this, p_30171_);
    }

    public void travel(Vec3 p_30218_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, p_30218_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_30218_);
        }

    }

    public boolean canBeLeashed(Player p_30151_) {
        return false;
    }

    public void thunderHit(ServerLevel p_30140_, LightningBolt p_30141_) {
        this.hurt(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0F)
                .add(Attributes.MOVEMENT_SPEED, 1.5F).build();
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(1, new AigialosaurusEntity.AigialosaurusPanicGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new AigialosaurusEntity.AigialosaurusGoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new AigialosaurusEntity.AigialosaurusTravelGoal(this, 0.1D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new AvoidEntityGoal<>(this, Pufferfish.class, 10.0F, 2.2D, 2.2D));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isInWater() && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aigialosaurus.swim", true));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aigialosaurus.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aigialosaurus.idle", true));
        return PlayState.CONTINUE;
    }

    /*@Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ATTACKED_MOBS, 0);
    }

    public final int getAttackedMobs() {
        return this.entityData.get(DATA_ATTACKED_MOBS);
    }

    public final void setAttackedMobs(int attackedMobs) {
        this.entityData.set(DATA_ATTACKED_MOBS, attackedMobs);
    }

     */

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    public AnimationFactory getFactory() {
        return factory;
    }

    /*class AigialosaurusAttackModGoal extends NearestAttackableTargetGoal {
        public AigialosaurusAttackModGoal(Mob mob, Class attackMob, boolean b) {
            super(mob, attackMob, b);
        }

        @Override
        protected boolean canAttack(@Nullable LivingEntity entity, TargetingConditions conditions) {
            return isHungry;
        }

        @Override
        public void tick() {
            if(!isHungry) {
                Sauria.LOGGER.info("The Aigialosaurus is not hungry");
                for(int i = 0; i < 1000; i++) {
                    isHungry = true;
                }
            }
            if(AigialosaurusEntity.this.getAttackedMobs() == 5) {
                isHungry = false;
                Sauria.LOGGER.info("The Aigialosaurus is not hungry anymore");
                AigialosaurusEntity.this.setAttackedMobs(0);
            }
            /*if(mob.doHurtTarget(mob)) {
                AigialosaurusEntity.this.setAttackedMobs(AigialosaurusEntity.this.getAttackedMobs()+ 1);
            }

        }
    }

     */

    static class AigialosaurusGoToWaterGoal extends MoveToBlockGoal {
        private final AigialosaurusEntity aigialosaurus;

        AigialosaurusGoToWaterGoal(AigialosaurusEntity entity, double d) {
            super(entity, 2.0D, 24);
            this.aigialosaurus = entity;
            this.verticalSearchStart = -1;
        }

        public boolean canContinueToUse() {
            return !this.aigialosaurus.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.aigialosaurus.level, this.blockPos);
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 160 == 0;
        }

        protected boolean isValidTarget(LevelReader p_30270_, BlockPos p_30271_) {
            return p_30270_.getBlockState(p_30271_).is(Blocks.WATER);
        }
    }

    static class AigialosaurusMoveControl extends MoveControl {
        private final AigialosaurusEntity aigialosaurus;

        AigialosaurusMoveControl(AigialosaurusEntity p_30286_) {
            super(p_30286_);
            this.aigialosaurus = p_30286_;
        }

        private void updateSpeed() {
            if (this.aigialosaurus.isInWater()) {
                this.aigialosaurus.setDeltaMovement(this.aigialosaurus.getDeltaMovement().add(0.0D, 0.00005D, 0.0D));
            } else if (this.aigialosaurus.onGround) {
                this.aigialosaurus.setSpeed(Math.max(this.aigialosaurus.getSpeed() / 2.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.aigialosaurus.getNavigation().isDone()) {
                double d0 = this.wantedX - this.aigialosaurus.getX();
                double d1 = this.wantedY - this.aigialosaurus.getY();
                double d2 = this.wantedZ - this.aigialosaurus.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.aigialosaurus.setYRot(this.rotlerp(this.aigialosaurus.getYRot(), f, 90.0F));
                this.aigialosaurus.yBodyRot = this.aigialosaurus.getYRot();
                float f1 = (float)(this.speedModifier * this.aigialosaurus.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.aigialosaurus.setSpeed(Mth.lerp(0.125F, this.aigialosaurus.getSpeed(), f1));
                this.aigialosaurus.setDeltaMovement(this.aigialosaurus.getDeltaMovement().add(0.0D, (double)this.aigialosaurus.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.aigialosaurus.setSpeed(0.0F);
            }
        }
    }

    static class AigialosaurusPanicGoal extends PanicGoal {
        AigialosaurusPanicGoal(AigialosaurusEntity p_30290_, double p_30291_) {
            super(p_30290_, p_30291_);
        }

        public boolean canUse() {
            if (!this.shouldPanic()) {
                return true;
            } else {
                BlockPos blockpos = this.lookForWater(this.mob.level, this.mob, 7);
                if (blockpos != null) {
                    this.posX = (double)blockpos.getX();
                    this.posY = (double)blockpos.getY();
                    this.posZ = (double)blockpos.getZ();
                    return true;
                } else {
                    return this.findRandomPosition();
                }
            }
        }
    }

    static class AigialosaurusPathNavigation extends AmphibiousPathNavigation {
        AigialosaurusPathNavigation(AigialosaurusEntity p_30294_, Level p_30295_) {
            super(p_30294_, p_30295_);
        }

        public boolean isStableDestination(BlockPos p_30300_) {
            Mob mob = this.mob;
            if (mob instanceof AigialosaurusEntity aigialosaurus) {
                if (aigialosaurus.isSwimming()) {
                    return this.level.getBlockState(p_30300_).is(Blocks.WATER);
                }
            }

            return !this.level.getBlockState(p_30300_.below()).isAir();
        }
    }

    static class AigialosaurusTravelGoal extends Goal {
        private final AigialosaurusEntity aigialosaurus;
        private final double speedModifier;
        private boolean stuck;

        AigialosaurusTravelGoal(AigialosaurusEntity p_30333_, double p_30334_) {
            this.aigialosaurus = p_30333_;
            this.speedModifier = p_30334_;
        }

        public void start() {
            int i = 512;
            int j = 4;
            RandomSource randomsource = this.aigialosaurus.random;
            int k = randomsource.nextInt(1025) - 512;
            int l = randomsource.nextInt(9) - 4;
            int i1 = randomsource.nextInt(1025) - 512;
            if ((double)l + this.aigialosaurus.getY() > (double)(this.aigialosaurus.level.getSeaLevel() - 1)) {
                l = 0;
            }

            BlockPos blockpos = new BlockPos((double)k + this.aigialosaurus.getX(), (double)l + this.aigialosaurus.getY(), (double)i1 + this.aigialosaurus.getZ());
            this.stuck = false;
        }

        public void tick() {
            if (this.aigialosaurus.getNavigation().isDone()) {
                Vec3 vec3 = Vec3.atBottomCenterOf(this.aigialosaurus.getOnPos());
                Vec3 vec31 = DefaultRandomPos.getPosTowards(this.aigialosaurus, 16, 3, vec3, (double)((float)Math.PI / 10F));
                if (vec31 == null) {
                    vec31 = DefaultRandomPos.getPosTowards(this.aigialosaurus, 8, 7, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 != null) {
                    int i = Mth.floor(vec31.x);
                    int j = Mth.floor(vec31.z);
                    int k = 34;
                    if (!this.aigialosaurus.level.hasChunksAt(i - 34, j - 34, i + 34, j + 34)) {
                        vec31 = null;
                    }
                }

                if (vec31 == null) {
                    this.stuck = true;
                    return;
                }

                this.aigialosaurus.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
            }

        }

        @Override
        public boolean canUse() {
            return true;
        }
    }
}
