package nz.tomasborsje.duskfall.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import nz.tomasborsje.duskfall.core.Populateable;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

/**
 * Utility class for items and ItemStacks.
 */
public class ItemUtil {

    /**
     * Returns a populated (e.g. enchantments/stat changes applied) ItemDefinition from the specified ItemStack.
     *
     * @param stack The ItemStack to get the ItemDefinition from.
     * @return The populated ItemDefinition.
     */
    public static ItemDefinition GetPopulatedDefinition(ItemStack stack) {
        if (!IsCustomItem(stack)) {
            throw new IllegalArgumentException("ItemStack is not a custom item!");
        }
        ItemDefinition definition = ItemUtil.GetItemDefinition(stack);

        // If the item definition can be populated, supply the NBT tag
        if (definition instanceof Populateable populateable) {
            populateable.populate(NBTUtil.GetNBT(stack));
        }

        return definition;
    }

    /**
     * Creates a new vanilla ItemStack with the given name.
     *
     * @param material The material of the ItemStack.
     * @param name     The name of the ItemStack.
     * @return The created ItemStack.
     */
    public static ItemStack VanillaStackWithName(Material material, String name) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Returns the custom ID of the specified ItemStack.
     *
     * @param stack The ItemStack to get the custom ID from.
     * @return The custom ID, "" if the item is not a custom item.
     */
    public static String GetCustomId(ItemStack stack) {
        CustomData customData = CraftItemStack.asNMSCopy(stack).get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            return "";
        }
        return customData.getUnsafe().getString("id");
    }

    /**
     * Returns the item definition of the specified ItemStack, 'null' if the item is not a custom item.
     *
     * @param stack The ItemStack to get the item definition from.
     * @return The item definition, 'null' if the item is not a custom item.
     */
    public static @Nullable ItemDefinition GetItemDefinition(ItemStack stack) {
        if (!IsCustomItem(stack)) {
            return null;
        }
        return ItemRegistry.Get(GetCustomId(stack));
    }

    /**
     * Returns whether the specified ItemStack is a custom item.
     *
     * @param stack The ItemStack to check.
     * @return Whether the specified ItemStack is a custom item.
     */
    public static boolean IsCustomItem(ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) {
            return false;
        }
        return !GetCustomId(stack).equals("");
    }
}
