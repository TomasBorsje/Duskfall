package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.CompoundTag;
import nz.tomasborsje.duskfall.core.Rarity;
import nz.tomasborsje.duskfall.core.StatProvider;
import nz.tomasborsje.duskfall.core.Usable;
import nz.tomasborsje.duskfall.util.Icons;
import nz.tomasborsje.duskfall.util.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition implements Cloneable {

    /**
     * The ID of the item.
     */
    @SerializedName("id")
    public String id = null;
    /**
     * The type of the item, used only to display at the bottom of the tooltip.
     */
    @SerializedName("type")
    public String type = null;
    /**
     * The description of the item, shown in dark gray at the bottom of the tooltip.
     */
    @SerializedName("description")
    public String description = null;
    /**
     * The name of the item.
     */
    @SerializedName("name")
    public String name = "Unknown Item";
    /**
     * The vanilla item used as the display for this item.
     */
    @SerializedName("material")
    public String material = "STONE";
    /**
     * The rarity of the item.
     */
    @SerializedName("rarity")
    public Rarity rarity = Rarity.COMMON;

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
                currentLine = new StringBuilder(ChatColor.DARK_GRAY + word);
            }
        }

        if (currentLine.length() > 0) {
            // Add the remaining part of the description
            lines.add(currentLine.toString());
        }
        return lines;
    }

    /**
     * Creates a new ItemStack with the default properties of this item definition.
     *
     * @return The created ItemStack.
     */
    public ItemStack createDefaultStack() {
        // Get the material of the itemstack
        Material material = Material.getMaterial(this.material);
        if (material == null) {
            throw new IllegalArgumentException("Unknown material: " + this.material);
        }

        // Create new stack with the specified material
        ItemStack stack = new ItemStack(material);

        // Create NBT tag for the itemstack
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", this.id);

        // Add the NBT tag to the itemstack
        stack = NBTUtil.SetNBT(stack, nbt);

        // Set item meta values
        ItemMeta meta = stack.getItemMeta();

        boolean boldName = (this.rarity == Rarity.LEGENDARY || this.rarity == Rarity.DEVELOPER);
        assert meta != null;

        // Set custom display ID to the hashcode of its lowercase ID
        // We have to clamp the range, see https://bugs.mojang.com/browse/MC-138426
        meta.setCustomModelData((int) ((float) (this.id.toLowerCase().hashCode() % 1_000_000)));
        // Log the custom model data
        System.out.println("Custom model data for " + this.id + " is " + meta.getCustomModelData());

        meta.setDisplayName(ChatColor.RESET + "" + this.rarity.colour + (boldName ? ChatColor.BOLD : "") + this.name);

        meta.setLore(this.GetLoreStrings());
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);

        stack.setItemMeta(meta);

        return stack;
    }

    /**
     * @return The display name of the item, including the rarity colour.
     */
    public String getDisplayName() {
        return rarity.colour + name;
    }

    @Override
    public ItemDefinition clone() {
        try {
            ItemDefinition clone = (ItemDefinition) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Generates the tooltip ("lore") of the specified item definition.
     *
     * @return List of strings containing the tooltip.
     */
    public List<String> GetLoreStrings() {
        List<String> lore = new ArrayList<>();

        // TODO: Move all these methods up into a AddExtraLore() method per class

        // If the definition is a melee weapon, add the damage
        if (this instanceof MeleeWeaponDefinition weapon) {
            lore.add("");
            lore.add(ChatColor.WHITE + Icons.DamageIcon + " Damage: " + weapon.getDamage());
        }

        // If the definition is a bow, add the damage
        if (this instanceof BowWeaponDefinition bow) {
            lore.add("");
            lore.add(ChatColor.WHITE + Icons.BowDamageIcon + " Damage: " + bow.getDamage());
        }

        if (this instanceof ArrowItemDefinition arrow) {
            lore.add("");
            lore.add(ChatColor.WHITE + Icons.BowDamageIcon + " Damage: +" + arrow.getArrowDamage());
        }

        // If the definition provides stats, display them
        if (this instanceof StatProvider statProvider && statProvider.hasStats()) {
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
        if (this instanceof Usable usable) {
            lore.add("");
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK: " + ChatColor.RESET + usable.getUseDescription());
        }

        // If the item has a description, add it
        if (this.description != null) {
            List<String> description = SplitDescription(this.description, 60);
            lore.add("");
            lore.addAll(description);
        }

        // Add rarity last, in bold and in the color of the rarity
        String rarityText = this.rarity.colour + "" + ChatColor.BOLD + this.rarity.name;
        if (this.type != null) {
            rarityText += " " + type;
        }
        lore.add("");
        lore.add(rarityText);

        return lore;
    }
}
