package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.core.Usable;
import nz.tomasborsje.duskfall.ui.screens.LockboxScreen;
import nz.tomasborsje.duskfall.util.Sounds;
import org.bukkit.ChatColor;

/**
 * Represents a lockbox item that can be unboxed by a player to get random items.
 */
public class LockboxDefinition extends ItemDefinition implements Usable {
    @SerializedName("lootTable")
    public String lootTable = "";

    @SerializedName("borderItem")
    public String borderItem = "copper_ingot";

    @Override
    public void onPlayerUse(MMOEntity user) {
        if(user instanceof MMOPlayer player) {
            // Do loot
            Sounds.PlayLockboxOpenSound(player);
            player.ui.openScreen(new LockboxScreen(player, this));
        }
    }

    @Override
    public String getUseDescription() {
        return ChatColor.WHITE + "Open the lockbox, revealing treasure!";
    }

    @Override
    public boolean isConsumedOnUse() {
        return true;
    }

    @Override
    public boolean allowVanillaUse() {
        return false;
    }
}
