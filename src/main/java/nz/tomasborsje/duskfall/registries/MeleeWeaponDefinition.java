package nz.tomasborsje.duskfall.registries;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;

public class MeleeWeaponDefinition extends ItemDefinition {
    @SerializedName("damage")
    public int damage = 1;
    // etc...

    public void onHit() {
        Bukkit.getLogger().info("Weapon hit!");
    }
}
