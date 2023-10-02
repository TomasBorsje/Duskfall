package nz.tomasborsje.duskfall.core;

import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;

/**
 * Enum for the rarity of an item, containing colour and name.
 */
public enum Rarity {
    @SerializedName("trash")
    TRASH("TRASH", ChatColor.GRAY),
    @SerializedName("common")
    COMMON("COMMON", ChatColor.WHITE),
    @SerializedName("uncommon")
    UNCOMMON("UNCOMMON", ChatColor.GREEN),
    @SerializedName("rare")
    RARE("RARE", ChatColor.BLUE),
    @SerializedName("epic")
    EPIC("EPIC", ChatColor.DARK_PURPLE),
    @SerializedName("legendary")
    LEGENDARY("LEGENDARY", ChatColor.GOLD),
    @SerializedName("developer")
    DEVELOPER("DEVELOPER", ChatColor.RED);

    public final String name;
    public final ChatColor colour;
    Rarity(String name, ChatColor colour) {
        this.name = name;
        this.colour = colour;
    }
}
