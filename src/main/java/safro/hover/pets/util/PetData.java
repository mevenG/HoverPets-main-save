package safro.hover.pets.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;

import java.util.UUID;

public class PetData {



    public static NbtCompound getPlayer(PlayerEntity player) {
        return ((IEntityDataSaver) player).getPersistentData();
    }

    public static NbtCompound getPlayer(IEntityDataSaver player) {
        return player.getPersistentData();
    }

    // Get xp amount to level up
    public static int getXPToLevelUp(int level) {
        //return 500 + level * 100; // 600XP for lvl1, 700XP for lvl2, 800XP for lvl3 ...
        int maths = level * level * 16;
        return maths;
    }

    /*
        XP system
     */
    public static int getXP(IEntityDataSaver player) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int xp = playerPet.getInt("xp");
        return xp;
    }
    public static int addXP(IEntityDataSaver player, int amount) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int xp = playerPet.getInt("xp");
        int neededToLevel = getXPToLevelUp(getLevel(player));

        if (xp+amount >= neededToLevel) {
            addLevel(player,1);
            xp = xp + amount - neededToLevel;
        } else {
            xp += amount;
        }
        playerPet.putInt("xp", xp);
        return xp;
    }
    public static int removeXP(IEntityDataSaver player, int amount) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int xp = playerPet.getInt("xp");
        if(xp - amount < 0) {
            xp = 0;
        } else {
            xp -= amount;
        }
        playerPet.putInt("xp", xp);
        return xp;
    }

    /*
        Level system
     */
    public static int getLevel(BasePetEntity pet) {
        LivingEntity player = pet.getOwner();
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            HoverPets.LOGGER.info("nullllllllllll");
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int level = playerPet.getInt("level");
        return level;
    }
    public static int getLevel(IEntityDataSaver player) {
        NbtCompound nbt =  player.getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int level = playerPet.getInt("level");
        return level;
    }
    public static int addLevel(IEntityDataSaver player, int amount) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int level = playerPet.getInt("level");
        level += amount;
        playerPet.putInt("level", level);
        //levelUP((BasePetEntity) pet, level);
        return level;
    }
    /*public static void levelUP(BasePetEntity pet, int level){
        String type = pet.getPetType();
        HoverPets.LOGGER.warn(type);
        pet.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D);
    }*/

    public static int removeLevel(IEntityDataSaver player, int amount) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String petKey = list.getString("petType");

        if(nbt.getCompound(petKey) == null){
            NbtCompound current = new NbtCompound();
            nbt.put(petKey,current);
        }
        NbtCompound playerPet = nbt.getCompound(petKey);
        int level = playerPet.getInt("level");
        if(level - amount < 0) {
            level = 0;
        } else {
            level -= amount;
        }
        nbt.putInt("level", level);
        return level;
    }

    /**
     * Checks if the pet is immune to the DamageSource
     *
     * @param source - The source the pet was damaged by
     * @return - Returns true if the pet is immune to the give source, returns false if not
     */
    public static boolean isImmune(DamageSource source) {
        if (source.isOutOfWorld()) {
            return true;
        }
        if (source.isFire()) {
            return false;
        }
        if (source.isMagic()) {
            return false;
        }
        if (source.isExplosive()) {
            return false;
        }
        if (source.isFallingBlock()) {
            return true;
        }
        if (source.isFromFalling()) {
            return false;
        }
        if (source.isProjectile()) {
            return false;
        }
        if (source.bypassesArmor()) {
            return false;
        }
        if (source.getAttacker() instanceof PlayerEntity) {
            return false;
        }
        return false;
    }

    /**
     * Checks if the Player currently owns a pet
     *
     * @param player - A PlayerEntity object
     * @return Returns if the current pet from ID is null or not
     */
    public static boolean hasPet(PlayerEntity player) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String id = list.getString("uuid");
        return (id.length() > 0);
    }

    /**
     * Sets the provided Player's pet ID with the provided ID
     *
     * @param player - Pet Owner whose ID is being set
     * @param uuid - The new ID to be set
     */
    public static void setPet(IEntityDataSaver player, String uuid, String type) {
        NbtCompound nbt = player.getPersistentData();
        if(!nbt.contains("currentPet")){
            nbt.put("currentPet",new NbtCompound());
        }
        NbtCompound list = nbt.getCompound("currentPet");
        list.putString("uuid", uuid);
        list.putString("petType", type);

        if(!nbt.contains(type)){
            nbt.put(type,new NbtCompound());
        }
        NbtCompound current = nbt.getCompound(type);
    }

    /**
     * Returns the current pet of the Player
     * Note: You should always use the hasPet method before running this to prevent null exceptions
     * @see #hasPet(PlayerEntity)
     *
     * @param player - The owner whose pet is being returned
     * @return - A pet entity object
     */
    public static BasePetEntity getPet(PlayerEntity player) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        NbtCompound list = nbt.getCompound("currentPet");
        String uuid = list.getString("uuid");
        return (BasePetEntity) ((ServerWorld)player.getWorld()).getEntity(UUID.fromString(uuid));
    }

    /**
     * Returns the current pet of the Player
     * Note: You should always use the hasPet method before running this to prevent null exceptions
     * @see #hasPet(PlayerEntity)
     *
     * @param player - The owner whose pet is being returned
     * @param pet - The pet type
     * @return - A pet entity object
     */
    public static NbtCompound getPet(PlayerEntity player,String pet) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        return nbt.getCompound(pet);
    }

    public static NbtCompound playerHasPet(PlayerEntity player, String pet) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        if (!nbt.contains(pet)){
            return null;
        }
        return nbt.getCompound(pet);
        /*NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        if(!nbt.contains(pet)){
            return null;
        }
        return nbt.getCompound(pet);*/
    }

}

//https://minecraft.fandom.com/wiki/Tutorials/Command_NBT_tags