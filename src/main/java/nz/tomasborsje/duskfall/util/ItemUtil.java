package nz.tomasborsje.duskfall.util;

import net.minecraft.nbt.CompoundTag;
import nz.tomasborsje.duskfall.core.Populateable;
import nz.tomasborsje.duskfall.core.Rarity;
import nz.tomasborsje.duskfall.core.StatProvider;
import nz.tomasborsje.duskfall.core.Usable;
import nz.tomasborsje.duskfall.definitions.*;
import nz.tomasborsje.duskfall.registries.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
     * Creates a new ItemStack with the default properties of the specified item definition.
     *
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

        boolean boldName = (definition.rarity == Rarity.LEGENDARY || definition.rarity == Rarity.DEVELOPER);
        meta.setDisplayName(ChatColor.RESET +""+ definition.rarity.colour + (boldName ? ChatColor.BOLD : "") + definition.name);

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
     * Generates the tooltip ("lore") of the specified ItemStack, assuming it is a custom item.
     *
     * @param stack The ItemStack to get the tooltip from.
     * @return List of strings containing the tooltip.
     */
    public static List<String> BuildLore(ItemStack stack) {
        // Get the definition and pass it on to the other method
        return BuildLore(ItemRegistry.Get(GetCustomId(stack)));
    }

    /**
     * Generates the tooltip ("lore") of the specified item definition.
     *
     * @param def The item definition to get the tooltip from.
     * @return List of strings containing the tooltip.
     */
    public static List<String> BuildLore(ItemDefinition def) {
        List<String> lore = new ArrayList<>();

        // If the definition is a weapon, add the damage
        if (def instanceof MeleeWeaponDefinition weapon) {
            lore.add("");
            lore.add(ChatColor.WHITE + Icons.DamageIcon + " Damage: " + weapon.damage);
        }

        // If the definition provides stats, display them
        if (def instanceof StatProvider statProvider && statProvider.hasStats()) {
            lore.add("");
            if (statProvider.getHealthBoost() > 0) {
                lore.add(ChatColor.GREEN + Icons.HealthIcon + " +" + statProvider.getHealthBoost() + " Health");
            }
            if (statProvider.getDefense() > 0) {
                lore.add(ChatColor.WHITE + Icons.DefenseIcon + " +" + statProvider.getDefense() + " Defense");
            }
            if (statProvider.getStrength() > 0) {
                lore.add(ChatColor.RED + Icons.StrengthIcon + " +" + statProvider.getStrength() + " Strength");
            }
            if (statProvider.getIntelligence() > 0) {
                lore.add(ChatColor.BLUE + Icons.IntelligenceIcon + " +" + statProvider.getIntelligence() + " Intelligence");
            }
            if (statProvider.getFocus() > 0) {
                lore.add(ChatColor.YELLOW + Icons.FocusIcon + " +" + statProvider.getFocus() + " Focus");
            }
        }

        // If the item is useable, add the use description
        if (def instanceof Usable usable) {
            lore.add("");
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK: " + ChatColor.RESET + usable.getUseDescription());
        }

        // If the item has a description, add it
        if (def.description != null) {
            List<String> description = SplitDescription(def.description, 60);
            lore.add("");
            lore.addAll(description);
        }

        // Add rarity last, in bold and in the color of the rarity
        String rarityText = def.rarity.colour + "" + ChatColor.BOLD + def.rarity.name;
        if(def.type != null) {
            rarityText += " " + def.type;
        }
        lore.add("");
        lore.add(rarityText);

        return lore;
    }

    /**
     * Returns the custom ID of the specified ItemStack.
     *
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

    /**
     * Returns the item definition of the specified ItemStack, 'null' if the item is not a custom item.
     * @param stack The ItemStack to get the item definition from.
     * @return The item definition, 'null' if the item is not a custom item.
     */
    public static @Nullable ItemDefinition GetItemDefinition(ItemStack stack) {
        if(!IsCustomItem(stack)) {
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
        if(stack == null || stack.getType() == Material.AIR) {
            return false;
        }
        return !GetCustomId(stack).equals("");
    }

    /**
     * Splits a long description into multiple lines, ensuring that each line has a maximum
     * length of approximately {@code maxLineLength} characters while preserving whole words.
     *
     * @param description   The long description to be split.
     * @param maxLineLength The maximum length of each line, approximately.
     * @return A list of lines, where each line is a substring of the original description with dark gray text.
     */
    public static List<String> SplitDescription(String description, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = description.split(" ");
        StringBuilder currentLine = new StringBuilder(ChatColor.DARK_GRAY.toString());

        for (String word : words) {
            if (currentLine.length() + word.length() + 3 <= maxLineLength) {
                // Add the word to the current line if it doesn't exceed the max length
                if (currentLine.length() > 2) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                // Start a new line if adding the word would exceed the max length
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(ChatColor.DARK_GRAY+word);
            }
        }

        if (currentLine.length() > 0) {
            // Add the remaining part of the description
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
