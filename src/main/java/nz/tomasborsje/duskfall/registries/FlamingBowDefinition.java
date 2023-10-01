package nz.tomasborsje.duskfall.registries;

import org.bukkit.Bukkit;

public class FlamingBowDefinition extends WeaponItemDefinition {
    @Override
    public void onHit() {
        Bukkit.getLogger().info("Flaming bow hit!");
    }
}
