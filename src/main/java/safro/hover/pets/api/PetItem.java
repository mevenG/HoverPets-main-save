package safro.hover.pets.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import safro.hover.pets.HoverPets;
import safro.hover.pets.util.IEntityDataSaver;
import safro.hover.pets.util.PetData;

import java.util.List;
import java.util.Objects;

public class PetItem extends Item {
    String abilityLore = null;
    String perkLore = null;
    public final EntityType<? extends BasePetEntity> basepet;
    public BasePetEntity pet = null;

    public PetItem(EntityType<? extends BasePetEntity> type, Settings settings) {
        super(settings);
        this.basepet = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (!PetData.hasPet(player)) {
                BasePetEntity basePet = basepet.create(world);
                if (basePet.canBeSummoned(player)) {
                    stack.decrement(1);
                    basePet.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), 0.0F, 0.0F);
                    basePet.setCustomNameVisible(true);
                    basePet.setOwner(player);
                    basePet.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 500);

                    //String name = this.getTranslationKey().split(".")[2];
                    String nn = this.getTranslationKey();
                    String name = nn.substring(nn.lastIndexOf('.') + 1);
                    PetData.setPet(((IEntityDataSaver) player), basePet.getUuidAsString(), name);

                    world.spawnEntity(basePet);
                    //PetData.addXP(((IEntityDataSaver) basePet),0);
                    //PetData.addLevel(((IEntityDataSaver) basePet),1);

                    HoverPets.LOGGER.error(basePet.getUuidAsString());
                    HoverPets.LOGGER.error(player.getUuidAsString());
                    return TypedActionResult.consume(stack);
                } else {
                    basePet = null;
                    player.sendMessage(Text.translatable("tooltip.hoverpets.no_permission").formatted(Formatting.RED), true);
                    return TypedActionResult.fail(stack);
                }
            } else {
                player.sendMessage(Text.translatable("tooltip.hoverpets.one_pet").formatted(Formatting.RED), true);
                return TypedActionResult.fail(stack);
            }
        }
        return TypedActionResult.pass(stack);
    }

    public PetItem setAbilityTooltip(String tooltip) {
        this.abilityLore = tooltip;
        return this;
    }

    public PetItem setPerkTooltip(String tooltip) {
        this.perkLore = tooltip;
        return this;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.perkLore != null || this.abilityLore != null) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("tooltip.hoverpets.shift").formatted(Formatting.GRAY));
            } else {
                if (this.perkLore != null) {
                    tooltip.add(Text.literal("Perk:").formatted(Formatting.WHITE));
                    tooltip.add(Text.literal(Text.translatable("tooltip.hoverpets.shift.perk").getString()).formatted(Formatting.WHITE));
                    tooltip.add(Text.literal("" + Formatting.AQUA + I18n.translate(this.perkLore)));
                }
                if (this.abilityLore != null) {
                    tooltip.add(Text.literal("Ability:").formatted(Formatting.WHITE));
                    tooltip.add(Text.literal(Text.translatable("tooltip.hoverpets.shift.ability").getString()).formatted(Formatting.WHITE));
                    tooltip.add(Text.literal("" + Formatting.YELLOW + I18n.translate(this.abilityLore)));
                }
            }
        }
    }
}
