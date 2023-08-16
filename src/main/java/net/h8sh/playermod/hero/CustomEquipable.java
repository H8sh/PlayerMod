package net.h8sh.playermod.hero;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface CustomEquipable extends Vanishable {
    CustomEquipmentSlot getEquipmentSlot();

    default SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    default InteractionResultHolder<ItemStack> swapWithEquipmentSlot(Item pItem, Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
        if (!EnchantmentHelper.hasBindingCurse(itemstack1) && !ItemStack.matches(itemstack, itemstack1)) {
            if (!pLevel.isClientSide()) {
                pPlayer.awardStat(Stats.ITEM_USED.get(pItem));
            }

            ItemStack itemstack2 = itemstack1.isEmpty() ? itemstack : itemstack1.copyAndClear();
            ItemStack itemstack3 = itemstack.copyAndClear();
            pPlayer.setItemSlot(equipmentslot, itemstack3);
            return InteractionResultHolder.sidedSuccess(itemstack2, pLevel.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Nullable
    static CustomEquipable get(ItemStack pStack) {
        Item $$3 = pStack.getItem();
        if ($$3 instanceof CustomEquipable equipable) {
            return equipable;
        } else {
            Item item1 = pStack.getItem();
            if (item1 instanceof BlockItem blockitem) {
                Block block = blockitem.getBlock();
                if (block instanceof CustomEquipable equipable1) {
                    return equipable1;
                }
            }

            return null;
        }
    }
}