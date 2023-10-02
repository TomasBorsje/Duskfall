package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityHurtEntityListener implements Listener {
    @EventHandler
    public void OnEntityHurtEntity(EntityDamageByEntityEvent event) {

        // Always disable vanilla damage
        event.setDamage(0);

        // Ensure both entities are custom entities
        MMOEntity attacker = EntityHandler.GetEntity(event.getDamager());
        MMOEntity defender = EntityHandler.GetEntity(event.getEntity());

        // If either is null, return
        if(attacker == null || defender == null) { return; }

        // Get the attacker's damage
        int damage = attacker.getCurrentDamage();

        // Hurt the defender
        defender.hurt(attacker, damage);
    }
}
