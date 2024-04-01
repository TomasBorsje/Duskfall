package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.buffs.NoncombatHealthRegenBuff;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.core.Usable;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Represents a food item that can be used by a player to heal over time, outside of combat.
 */
public class HealthFoodDefinition extends ItemDefinition implements Usable {
    @SerializedName("healAmount")
    public int healAmount = 0;

    @SerializedName("consumable")
    public boolean consumable = true;

    @SerializedName("allowVanillaUse")
    public boolean allowVanillaUse = false;

    @Override
    public void onPlayerUse(MMOEntity user) {
        if(user instanceof MMOPlayer player) {
            player.getBukkitEntity().sendMessage(ChatColor.GRAY+"You ate "+rarity.colour+name+ChatColor.GRAY+"!");
            player.addBuff(new NoncombatHealthRegenBuff(user, 20*20, healAmount));
            player.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 0));
        }
    }

    @Override
    public String getUseDescription() {
        return ChatColor.WHITE + "Eat to heal " + ChatColor.GREEN + healAmount + " Health" + ChatColor.WHITE + " over 20 seconds.";
    }

    @Override
    public boolean isConsumedOnUse() {
        return consumable;
    }

    @Override
    public boolean allowVanillaUse() {
        return allowVanillaUse;
    }
}
