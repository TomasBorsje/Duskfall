package nz.tomasborsje.duskfall.registries;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.Rarity;

public class ItemDefinition {
    /**
     * The ID of the item.
     */
    @SerializedName("id")
    public String id = "";

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
    protected ItemDefinition clone(){
        try {
            return (ItemDefinition) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
