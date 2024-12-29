package nz.tomasborsje.duskfall.ui.screens;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Class that handles the creation and management of UI screens for a player.
 */
public class ScreenManager {
    private final MMOPlayer player;
    private final Player playerEntity;
    private PlayerScreen currentScreen = null;
    private Inventory currentInventory = null;

    public ScreenManager(MMOPlayer player) {
        this.player = player;
        this.playerEntity = player.getBukkitEntity();
    }

    /**
     * Opens a screen for the player, replacing any existing screen.
     *
     * @param screen The screen to open.
     */
    public void openScreen(PlayerScreen screen) {
        currentScreen = screen;
        currentInventory = Bukkit.createInventory(null, screen.getInventoryRows() * 9, screen.getTitle());
        screen.render(currentInventory);
        playerEntity.openInventory(currentInventory);
    }

    /**
     * Closes the current screen for the player, closing the actual inventory.
     */
    public void closeScreen() {
        playerEntity.closeInventory();
        sendCloseEvent();
        currentInventory = null;
    }

    /**
     * Sends a close event to the current screen, if there is one.
     * Removes the current screen.
     */
    public void sendCloseEvent() {
        if (currentScreen != null) {
            currentScreen.onClose();
        }
        currentScreen = null;
    }

    /**
     * Sends a click event to the current screen, if there is one.
     *
     * @param slot The slot that was clicked.
     */
    public void sendClickEvent(int slot) {
        if (currentScreen != null) {
            currentScreen.onClick(slot);
            currentScreen.render(currentInventory);
        }
    }

    /**
     * @return Whether the player currently has a screen open.
     */
    public boolean isScreenOpen() {
        return currentScreen != null;
    }
}
