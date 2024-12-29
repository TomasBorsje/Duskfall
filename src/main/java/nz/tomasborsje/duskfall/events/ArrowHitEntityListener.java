package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.*;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowHitEntityListener implements Listener {

    @EventHandler
    public static void OnArrowHitEntity(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        FiredArrow firedArrow = Duskfall.world.ArrowHandler.Get(arrow);
        if (firedArrow == null) {
            return;
        }

        MMOEntity entity = EntityHandler.GetEntity(event.getHitEntity());
        if (entity == null) {
            return;
        }

        DamageInstance damageInstance = new DamageInstance(
                MMODamageCause.ENTITY,
                DamageType.PHYSICAL,
                firedArrow.owner,
                firedArrow.arrowDefinition.getArrowDamage() + firedArrow.bowDefinition.getDamage());
        entity.hurt(damageInstance);

        Bukkit.broadcastMessage("Entity " + firedArrow.owner.getEntityName() + " hit " + entity.getEntityName() +
                " with arrow " + firedArrow.arrowDefinition.name + " for " + damageInstance.getDamageAmount() + " damage!");
    }
}
