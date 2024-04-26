package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.CompoundTag;
import nz.tomasborsje.duskfall.core.Rarity;
import nz.tomasborsje.duskfall.util.ItemUtil;
import nz.tomasborsje.duskfall.util.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDefinition implements Cloneable {

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
        meta.setCustomModelData((int)((float)(this.id.toLowerCase().hashCode() % 1_000_000)));
        // Log the custom model data
        System.out.println("Custom model data for " + this.id + " is " + meta.getCustomModelData());

        meta.setDisplayName(ChatColor.RESET +""+ this.rarity.colour + (boldName ? ChatColor.BOLD : "") + this.name);

        meta.setLore(ItemUtil.BuildLore(this));
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
     * @return The display name of the item, including the rarity colour.
     */
    public String getDisplayName() {
        return rarity.colour+name;
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
}
