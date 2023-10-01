package nz.tomasborsje.duskfall.definitions;

import nz.tomasborsje.duskfall.registries.MeleeWeaponDefinition;
import org.bukkit.Bukkit;

public class FlamingBowDefinitionMelee extends MeleeWeaponDefinition {
    @Override
    public void onHit() {
        Bukkit.getLogger().info("Flaming bow hit!");
    }
}
