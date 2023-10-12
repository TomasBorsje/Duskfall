package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Server tick event, ticking all custom features.
 */
public class ServerTickRunner extends BukkitRunnable {
    World overworld;

    @Override
    public void run() {
        // Check world exists before running
        if(overworld == null) {
            overworld = Duskfall.plugin.getServer().getWorlds().get(0);
            if(overworld == null) { return; }
            // World has loaded, init
            initialise();
        }

        // Tick custom entities
        EntityHandler.Tick();

        // Tick chat
        Duskfall.globalChat.tick();
    }

    void initialise() {

    }
}
