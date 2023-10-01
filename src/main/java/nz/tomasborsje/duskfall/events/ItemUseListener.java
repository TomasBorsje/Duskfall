package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.definitions.Usable;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Handles events related to items being right-clicked.
 */
public class ItemUseListener implements Listener {

    @EventHandler
    public void OnItemUsed(PlayerInteractEvent event) {
        // Only handle right clicks
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) { return; }

        // Get used item
        ItemStack usedStack = event.getItem();

        // If null or vanilla, ignore
        if(usedStack == null || usedStack.getType().isAir()) { return; }
        if(!ItemUtil.IsCustomItem(usedStack)) { event.setCancelled(true); return; }

        // Get the custom definition
        ItemDefinition usedItem = ItemUtil.GetPopulatedDefinition(usedStack);

        // If it's useable, use it
        if(usedItem instanceof Usable usableItem) {
            usableItem.onPlayerUse(event.getPlayer());
            // If it's consumable, reduce stack size
            if(usableItem.isConsumedOnUse()) {
                usedStack.setAmount(usedStack.getAmount() - 1);
            }
            // If the vanilla behaviour is disabled, cancel the event
            if(!usableItem.allowVanillaUse()) {
                event.setCancelled(true);
            }
        }
    }
}
