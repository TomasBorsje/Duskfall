package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.DamageInstance;
import nz.tomasborsje.duskfall.core.DamageType;
import nz.tomasborsje.duskfall.core.MMODamageCause;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityHurtListener implements Listener {
    @EventHandler
    public void OnEntityHurt(EntityDamageEvent event) {
        double damage = event.getDamage();
        event.setDamage(0);
        if(EntityHandler.GetEntity(event.getEntity()) == null) {
            return;
        }

        MMOEntity entity = EntityHandler.GetEntity(event.getEntity());
        if(entity == null) {
            return;
        }

        DamageCause cause = event.getCause();
        switch(cause) {
            case FALL: {
                float percentMaxHp = (float) (damage * 5f);
                int fallDamage = (int)(entity.getMaxHealth() * percentMaxHp/100f);
                DamageInstance instance = new DamageInstance(MMODamageCause.FALL, DamageType.TRUE, null, fallDamage);

                entity.getBukkitEntity().sendMessage(ChatColor.RED+"You took "+fallDamage+" fall damage!");
                entity.hurt(instance);


                break;
            }
            case CONTACT: {
                int spikeDamage = (int)(entity.getMaxHealth() * 0.05f);
                DamageInstance instance = new DamageInstance(MMODamageCause.BLOCK_CONTACT_DAMAGE, DamageType.TRUE, null, spikeDamage);
                entity.hurt(instance);
            }
        }

    }
}
