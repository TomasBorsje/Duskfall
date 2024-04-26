package nz.tomasborsje.duskfall.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapDecoder;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {
    /**
     * Adds an NBT tag to a CraftBukkit ItemStack.
     * @param stack The ItemStack to add the NBT tag to.
     * @param nbt The NBT tag to add.
     */
    public static ItemStack SetNBT(ItemStack stack, CompoundTag nbt) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        nmsStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    /**
     * Gets the NBT tag from a CraftBukkit ItemStack.
     * @param stack The ItemStack to get the NBT tag from.
     * @return The NBT tag.
     */
    public static CompoundTag GetNBT(ItemStack stack) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        return nmsStack.get(DataComponents.CUSTOM_DATA).getUnsafe();
    }
}
