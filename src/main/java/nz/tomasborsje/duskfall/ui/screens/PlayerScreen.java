package nz.tomasborsje.duskfall.ui.screens;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Base class for all screens that are displayed to the player.
 * Screens are a maximum size chest inventory that can be interacted with.
 */
public abstract class PlayerScreen {
    protected final MMOPlayer player;
    private final ItemStack WHITE_GLASS_PANE = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

    public PlayerScreen(MMOPlayer player) {
        this.player = player;
    }

    public void render(Inventory inventory) {
        // Fill with white glass panes
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, WHITE_GLASS_PANE);
        }
    }

    public void onClose() {}

    public void onClick(int slot) {}

    public int getInventoryRows() {
        return 6;
    }

    public String getTitle() {
        return "";
    }
}
