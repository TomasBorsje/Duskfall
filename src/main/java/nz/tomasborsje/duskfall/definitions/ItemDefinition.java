package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.Rarity;

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
