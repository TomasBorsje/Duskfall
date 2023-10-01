package nz.tomasborsje.duskfall.util;

import net.minecraft.nbt.CompoundTag;
import nz.tomasborsje.duskfall.registries.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for items and ItemStacks.
 */
public class ItemUtil {

    /**
     * Returns a populated (e.g. enchantments/stat changes applied) ItemDefinition from the specified ItemStack.
     * @param stack The ItemStack to get the ItemDefinition from.
     * @return The populated ItemDefinition.
     */
    public static ItemDefinition GetPopulatedDefinition(ItemStack stack) {
        if(!IsCustomItem(stack)) {
            throw new IllegalArgumentException("ItemStack is not a custom item!");
        }
        ItemDefinition definition = ItemRegistry.Get(GetCustomId(stack));

        // If the item definition can be populated, supply the NBT tag
        if (definition instanceof PopulateableDefinition populateable) {
            populateable.populate(NBTUtil.GetNBT(stack));
        }

        return definition;
    }

    /**
     * Creates a new ItemStack with the default properties of the specified item definition.
     * @param definition The item definition to create the ItemStack from.
     * @return The created ItemStack.
     */
    public static ItemStack CreateDefaultStack(ItemDefinition definition) {
        // Get the material of the itemstack
        Material material = Material.getMaterial(definition.material);
        if (material == null) {
            throw new IllegalArgumentException("Unknown material: " + definition.material);
        }

        // Create new stack with the specified material
        ItemStack stack = new ItemStack(material);

        // Create NBT tag for the itemstack
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", definition.id);

        // Add the NBT tag to the itemstack
        stack = NBTUtil.SetNBT(stack, nbt);

        // Set item meta values
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.RESET +""+ definition.rarity.colour + definition.name);
        meta.setLore(BuildLore(definition));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);

        stack.setItemMeta(meta);

        return stack;
    }

    /**
     * Returns the tooltip ("lore") of the specified ItemStack, assuming it is a custom item.
     * @param stack The ItemStack to get the tooltip from.
     * @return List of strings containing the tooltip.
     */
    public static List<String> BuildLore(ItemStack stack) {
        // Get the definition and pass it on to the other method
        return BuildLore(ItemRegistry.Get(GetCustomId(stack)));
    }

    /**
     * Returns the tooltip ("lore") of the specified item definition.
     * @param def The item definition to get the tooltip from.
     * @return List of strings containing the tooltip.
     */
    public static List<String> BuildLore(ItemDefinition def) {
        List<String> lore = new ArrayList<>();

        // If the definition is a weapon, add the damage
        if (def instanceof MeleeWeaponDefinition weapon) {
            lore.add("");
            lore.add(ChatColor.RED + Icons.DamageIcon + " Damage: " + weapon.damage);
        }

        // If the definition is armour, add the armour value
        if (def instanceof ArmourDefinition armour) {
            lore.add("");
            lore.add(ChatColor.WHITE + Icons.DefenseIcon + " Defense: " + armour.defense);
        }

        // Add rarity last, in bold and in the color of the rarity
        lore.add("");
        lore.add(def.rarity.colour + "" + ChatColor.BOLD + def.rarity.name);

        return lore;
    }

    /**
     * Returns the custom ID of the specified ItemStack.
     * @param stack The ItemStack to get the custom ID from.
     * @return The custom ID, "" if the item is not a custom item.
     */
    public static String GetCustomId(ItemStack stack) {
        CompoundTag nbt = CraftItemStack.asNMSCopy(stack).getTag();

        if (nbt == null) {
            return "";
        }

        return nbt.getString("id");
    }

    public static boolean IsCustomItem(ItemStack stack) {
        return !GetCustomId(stack).equals("");
    }
}
