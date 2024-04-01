package nz.tomasborsje.duskfall.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event) {
        // Disable block breaking
        event.setCancelled(true);
    }
}
