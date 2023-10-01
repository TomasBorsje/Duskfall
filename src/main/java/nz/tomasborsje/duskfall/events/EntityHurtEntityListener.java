package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.registries.ItemDefinition;
import nz.tomasborsje.duskfall.registries.MeleeWeaponDefinition;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityHurtEntityListener implements Listener {
    @EventHandler
    public void OnEntityHurtEntity(EntityDamageByEntityEvent event) {

        // Always disable vanilla damage
        event.setDamage(0);

        // Ensure both entities are custom entities


        // Debug: If the attacker is a player, get their custom held item
        if(event.getDamager() instanceof Player player) {
            // Get the item in the player's main hand
            ItemStack stack = player.getInventory().getItemInMainHand();

            // If the item isn't custom, ignore
            if(!ItemUtil.IsCustomItem(stack)) return;

            // Get the custom definition
            ItemDefinition itemDefinition = ItemUtil.GetPopulatedDefinition(stack);

            // If the item is a melee weapon, apply its damage
            if(itemDefinition instanceof MeleeWeaponDefinition weapon) {
                player.sendMessage("You hit with a weapon! Damage: " + weapon.damage);
            }
        }
    }
}
