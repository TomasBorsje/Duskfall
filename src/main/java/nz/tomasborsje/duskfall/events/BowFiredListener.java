package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.definitions.ArrowItemDefinition;
import nz.tomasborsje.duskfall.definitions.BowWeaponDefinition;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class BowFiredListener implements Listener {

    @EventHandler
    public static void OnBowFired(EntityShootBowEvent event) {
        // Check projectile is an arrow
        Entity arrow = event.getProjectile();
        if (!(arrow instanceof Arrow arrowEntity)) {
            return;
        }

        // Check arrow is custom
        ItemDefinition consumable = ItemUtil.GetItemDefinition(event.getConsumable());
        if (!(consumable instanceof ArrowItemDefinition arrowDef)) {
            event.getEntity().sendMessage("You can't fire that type of arrow!");
            event.setCancelled(true);
            return;
        }

        // Check bow is custom
        ItemDefinition bowItem = ItemUtil.GetItemDefinition(event.getBow());
        if (!(bowItem instanceof BowWeaponDefinition bowDef)) {
            event.getEntity().sendMessage("That's not a custom bow!");
            event.setCancelled(true);
            return;
        }

        // Check owner is custom
        MMOEntity owner = EntityHandler.GetEntity(event.getEntity());
        if (owner == null) {
            event.setCancelled(true);
            return;
        }

        owner.getBukkitEntity().sendMessage("You fired an arrow of type " + arrowDef.name + " with your bow " + bowDef.name + "!");

        // Inform bow it fired an arrow
        // TODO: Stop bow from editing arrow values
        bowDef.OnFireArrow(arrowDef);

        // Add fired arrow details to arrow handler
        Duskfall.world.ArrowHandler.Add(arrowEntity, arrowDef, bowDef, owner);


    }
}
