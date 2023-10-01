package nz.tomasborsje.duskfall.core;

import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;

/**
 * Enum for the rarity of an item, containing colour and name.
 */
public enum Rarity {
    @SerializedName("TRASH")
    TRASH("TRASH", ChatColor.GRAY),
    @SerializedName("COMMON")
    COMMON("COMMON", ChatColor.WHITE),
    @SerializedName("UNCOMMON")
    UNCOMMON("UNCOMMON", ChatColor.GREEN),
    @SerializedName("RARE")
    RARE("RARE", ChatColor.BLUE),
    @SerializedName("EPIC")
    EPIC("EPIC", ChatColor.DARK_PURPLE),
    @SerializedName("LEGENDARY")
    LEGENDARY("LEGENDARY", ChatColor.GOLD),
    @SerializedName("DEVELOPER")
    MYTHIC("DEVELOPER", ChatColor.RED);

    public final String name;
    public final ChatColor colour;
    Rarity(String name, ChatColor colour) {
        this.name = name;
        this.colour = colour;
    }
}
