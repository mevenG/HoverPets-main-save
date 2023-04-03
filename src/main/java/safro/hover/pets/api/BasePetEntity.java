package safro.hover.pets.api;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.hover.pets.HoverPets;
import safro.hover.pets.registry.ItemRegistry;
import safro.hover.pets.util.IEntityDataSaver;
import safro.hover.pets.util.PacketHandler;
import safro.hover.pets.util.PetData;
import safro.hover.pets.util.RespawnAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class BasePetEntity extends TameableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private static String petType = "toto";

    public BasePetEntity(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
        //this.bobbingage = this.random.nextInt(100000);
    }

    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.CHICKEN_PET);
    }

    public static DefaultAttributeContainer.Builder createPetAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0f);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        //this.goalSelector.add(2, new LookAroundGoal(this));

        //this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
        //this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1));

        //this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        //this.targetSelector.add(3, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData animationData) {

    }


    public boolean isCollidable() {
        return true;
    }

    public boolean isPushable() {
        return true;
    }

    public boolean isPushedByFluids() {
        return true;
    }

    public boolean collidesWith(Entity entity) {
        return !this.hasOwner() || !(entity instanceof LivingEntity other) || !this.isOwner(other);
    }

    public int getNextAirUnderwater(int air) {
        return air;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public boolean damage(DamageSource source, float amount) {
        if (PetData.isImmune(source)) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean isInsideWall() {
        return false;
    }

    @Override
    public void tick() {
        if (!(world instanceof ServerWorld)) return;
        super.tick();
        if (this.hasOwner()) {
            PlayerEntity owner = (PlayerEntity) this.getOwner();
            //PetData.setPet(((IEntityDataSaver) owner), this.getUuidAsString(), petType);
            this.tickPerk(world, owner, this);

            if (!world.isClient) {
                if (((RespawnAccess) owner).isReadyForRespawn()) {
                    this.teleport(owner.getX(), owner.getY(), owner.getZ(), false);
                }
            }
            PacketHandler.SendPetData(this);
        }
    }

    public boolean hasOwner() {
        return this.getOwner() != null;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isOwner(player) && !player.isSneaking()) {
            HoverPets.LOGGER.info("click");
            //this.level +=1;
            //open menu here
            /*PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeBoolean(true); // Add any data you need to the packet

            ServerPlayNetworking.send((ServerPlayerEntity) player, new Identifier("hoverpets", "open_menu"), data);*/


            /*this.onRemoved(world, player);
            if (!player.getInventory().insertStack(getPetStack())) {
                player.dropItem(getPetStack(), false);
            }
            PetUtil.setPet(player, 0);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;*/
        }
        return super.interactMob(player, hand);
    }

    public void kill() {
        if (this.hasOwner() && this.getOwner() instanceof PlayerEntity player) {
            PetData.setPet(((IEntityDataSaver) player), "", "");
        }
        super.kill();
    }

    /**
     * Used for selecting the pet item that drops when the pet is despawned
     *
     * @return - Returns an ItemStack (Usually new ItemStack(...);)
     */
    /*public abstract ItemStack getPetStack();*/

    /**
     * This method is called every tick while the pet has an owner
     * Most pets use/need this method for their perk functions
     *
     * @param world         - A World object (can be any)
     * @param player        - A Player object (the pet owner)
     * @param basePetEntity - The pet
     */
    public void tickPerk(World world, PlayerEntity player, BasePetEntity basePetEntity) {

    }

    /**
     * This method is called when the player interacts and despawns the pet
     * This method is not abstract due to it not being required for most pets
     * Can be used for turning off abilities or other disabling checks
     *
     * @param world - A World object (can be any)
     * @param player - A Player object (the pet owner)
     */
    public void onRemoved(World world, PlayerEntity player) {
        PetData.setPet(((IEntityDataSaver) player), "", "");
    }

    /**
     * This method is called when the player interacts and despawns the pet
     * This method is not abstract due to it not being required for most pets
     * Can be used for turning off abilities or other disabling checks
     *
     * @param damageSource - the reason of the death
     */
    public void onDeath(DamageSource damageSource) {
        HoverPets.LOGGER.warn(this.getOwner());
        PetData.setPet(((IEntityDataSaver) this.getOwner()), "", "");
    }

    /**
     * Called when the player pressed the 'pet key' while having a pet equipped
     * Used for pets with perks that aren't called every tick
     * NOTE: This method is called on the server
     *
     * @param world - The player's world
     * @param player - The player with the pet
     */
    public void onPetKey(World world, PlayerEntity player) {}

    /**
     * Called when the player is about to summon in their pet using a PetItem
     * Used for permission specific pets
     * @param player - Player summoning pet
     * @return - Returns true if this pet can be summoned by the player
     */
    public boolean canBeSummoned(PlayerEntity player) {
        String type = Registry.ENTITY_TYPE.getId(this.getType()).getPath();
        return Permissions.check(player, "hoverpets.allowed." + type, true);
    }


    public static String getPetType() {
        return petType;
    }

    public void setPetType(String type) {
        petType = type;
    }


}
