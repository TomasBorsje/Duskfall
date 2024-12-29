package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.DamageInstance;
import nz.tomasborsje.duskfall.core.DamageType;
import nz.tomasborsje.duskfall.core.MMODamageCause;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
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
        if (attacker == null || defender == null) {
            return;
        }

        attacker.getBukkitEntity().sendMessage("You attacked something.");

        // Get the attacker's damage
        int damage = attacker.getMeleeDamage();
        DamageInstance instance = new DamageInstance(MMODamageCause.ENTITY, DamageType.PHYSICAL, attacker, damage);

        // Hurt the defender
        defender.hurt(instance);
    }
}
