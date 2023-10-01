package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Represents a food item that can be used by a player to heal over time, outside of combat.
 */
public class HealthFoodDefinition extends ItemDefinition implements Usable {
    @SerializedName("healAmount")
    public int healAmount = 0;

    @SerializedName("consumable")
    public boolean consumable = true;

    @SerializedName("allowVanillaUse")
    public boolean allowVanillaUse = false;

    @Override
    public void onPlayerUse(Player user) {
        user.sendMessage("You ate some food for " + healAmount + " health!");
    }

    @Override
    public String getUseDescription() {
        return ChatColor.WHITE + "Eat to heal " + ChatColor.GREEN + healAmount + " Health" + ChatColor.WHITE + " over 20 seconds.";
    }

    @Override
    public boolean isConsumedOnUse() {
        return consumable;
    }

    @Override
    public boolean allowVanillaUse() {
        return allowVanillaUse;
    }
}
