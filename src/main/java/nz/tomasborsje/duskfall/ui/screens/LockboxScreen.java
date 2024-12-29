package nz.tomasborsje.duskfall.ui.screens;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.definitions.LockboxDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Screen that allows the player to select two pieces of loot from a lockbox.
 */
public class LockboxScreen extends PlayerScreen {
    private final static int LOOT_SLOT_1 = 2 * 9 + 2;
    private final static int LOOT_SLOT_2 = 2 * 9 + 3;
    private final static int LOOT_SLOT_3 = 2 * 9 + 4;
    private final static int LOOT_SLOT_4 = 2 * 9 + 5;
    private final static int LOOT_SLOT_5 = 2 * 9 + 6;
    private final static int SELECT_SLOT_1 = 3 * 9 + 2;
    private final static int SELECT_SLOT_2 = 3 * 9 + 3;
    private final static int SELECT_SLOT_3 = 3 * 9 + 4;
    private final static int SELECT_SLOT_4 = 3 * 9 + 5;
    private final static int SELECT_SLOT_5 = 3 * 9 + 6;
    private final LockboxDefinition lockbox;
    private final ItemStack borderItem;
    private final ItemStack FILLER_GLASS_PANE = ItemUtil.VanillaStackWithName(Material.WHITE_STAINED_GLASS_PANE, ChatColor.GREEN + "Select two items!");
    ItemStack[] loot = new ItemStack[5];
    int firstItemSlot = -1; // Whether the player has selected the first loot item
    ItemStack firstItem; // The first item the player selected
    ItemStack secondItem; // The second item the player selected

    public LockboxScreen(MMOPlayer player, LockboxDefinition lockbox) {
        super(player);
        this.lockbox = lockbox;
        this.borderItem = ItemUtil.VanillaStackWithName(Material.COPPER_INGOT, ChatColor.GREEN + "Choose two items!");
        // Generate loot
        for (int i = 0; i < loot.length; i++) {
            loot[i] = ItemRegistry.GetRandomItem().createDefaultStack();
        }
    }

    @Override
    public void render(Inventory inventory) {
        super.render(inventory);
        // Iterate through all slots
        for (int i = 0; i < inventory.getSize(); i++) {
            // If far left, far right, top row, or bottom row, fill with copper bars
            if (i % 9 == 0 || i % 9 == 8 || i < 9 || i > 44) {
                inventory.setItem(i, borderItem);
            }
            // Otherwise, fill with white glass panes
            else {
                inventory.setItem(i, FILLER_GLASS_PANE);
            }
        }
        // Fill all loot slots with random loot
        inventory.setItem(LOOT_SLOT_1, loot[0]);
        inventory.setItem(LOOT_SLOT_2, loot[1]);
        inventory.setItem(LOOT_SLOT_3, loot[2]);
        inventory.setItem(LOOT_SLOT_4, loot[3]);
        inventory.setItem(LOOT_SLOT_5, loot[4]);

        // Fill all select slots with blue glass panes
        inventory.setItem(SELECT_SLOT_1, ItemUtil.VanillaStackWithName(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Select"));
        inventory.setItem(SELECT_SLOT_2, ItemUtil.VanillaStackWithName(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Select"));
        inventory.setItem(SELECT_SLOT_3, ItemUtil.VanillaStackWithName(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Select"));
        inventory.setItem(SELECT_SLOT_4, ItemUtil.VanillaStackWithName(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Select"));
        inventory.setItem(SELECT_SLOT_5, ItemUtil.VanillaStackWithName(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE + "Select"));

        // If a first slot was selected, make the slot below it a lime glass pane
        if (firstItemSlot != -1) {
            inventory.setItem(firstItemSlot, ItemUtil.VanillaStackWithName(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Selected!"));
        }
    }

    @Override
    public void onClick(int slot) {
        // If a loot slot was clicked, store the corresponding ItemStack
        if (slot == SELECT_SLOT_1) {
            if (firstItemSlot != -1) {
                secondItem = loot[0];
            } else {
                firstItem = loot[0];
            }
        } else if (slot == SELECT_SLOT_2) {
            if (firstItemSlot != -1) {
                secondItem = loot[1];
            } else {
                firstItem = loot[1];
            }
        } else if (slot == SELECT_SLOT_3) {
            if (firstItemSlot != -1) {
                secondItem = loot[2];
            } else {
                firstItem = loot[2];
            }
        } else if (slot == SELECT_SLOT_4) {
            if (firstItemSlot != -1) {
                secondItem = loot[3];
            } else {
                firstItem = loot[3];
            }
        } else if (slot == SELECT_SLOT_5) {
            if (firstItemSlot != -1) {
                secondItem = loot[4];
            } else {
                firstItem = loot[4];
            }
        }

        // If any loot slot was indeed clicked, do functionality
        if (slot == SELECT_SLOT_1 || slot == SELECT_SLOT_2 || slot == SELECT_SLOT_3 || slot == SELECT_SLOT_4 || slot == SELECT_SLOT_5) {
            // Play a chime
            player.getBukkitEntity().playSound(player.getBukkitEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.5f, 2.0f);

            // If the player has selected two items, close the screen and give them the items
            if (firstItemSlot != -1) {
                player.getBukkitEntity().playSound(player.getBukkitEntity().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f);
                player.inventory.addItem(firstItem);
                player.inventory.addItem(secondItem);
                player.ui.closeScreen();
            }
            // Otherwise, select the first item
            else {
                firstItemSlot = slot;
            }
        }
    }

    @Override
    public void onClose() {
        // If no items were selected, notify the player of the loss
        if (firstItemSlot == -1) {
            player.getBukkitEntity().sendMessage("You discarded the lockbox.");
        }
    }

    @Override
    public String getTitle() {
        return lockbox.name;
    }
}
