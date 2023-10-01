package nz.tomasborsje.duskfall.definitions;

import org.bukkit.entity.Player;

/**
 * Represents an item that can be used by a player.
 */
public interface Usable {
    /**
     * Called when a player uses this item.
     * @param user The player that used this item.
     */
    void onPlayerUse(Player user);

    /**
     * Gets the use description for this item.
     * @return A string that describes what using this item does.
     */
    String getUseDescription();

    /**
     * Gets whether this item is consumed when used.
     * @return True if this item is consumed when used, false otherwise.
     */
    boolean isConsumedOnUse();

    /**
     * Get whether the item use event is cancelled when this item is used.
     * E.G. whether vanilla behaviour is enabled.
     * @return True if the vanilla item use event is cancelled when this item is used, false otherwise.
     */
    boolean allowVanillaUse();
}
