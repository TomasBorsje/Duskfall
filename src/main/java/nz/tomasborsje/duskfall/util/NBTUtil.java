package nz.tomasborsje.duskfall.util;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {
    /**
     * Adds an NBT tag to a CraftBukkit ItemStack.
     * @param stack The ItemStack to add the NBT tag to.
     * @param nbt The NBT tag to add.
     */
    public static ItemStack SetNBT(ItemStack stack, CompoundTag nbt) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        nmsStack.setTag(nbt);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    /**
     * Gets the NBT tag from a CraftBukkit ItemStack.
     * @param stack The ItemStack to get the NBT tag from.
     * @return The NBT tag.
     */
    public static CompoundTag GetNBT(ItemStack stack) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        return nmsStack.getTag();
    }
}
