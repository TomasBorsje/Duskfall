package nz.tomasborsje.duskfall.definitions.custom;

import nz.tomasborsje.duskfall.definitions.MeleeWeaponDefinition;
import org.bukkit.Bukkit;

public class FlamingBowDefinitionMelee extends MeleeWeaponDefinition {
    @Override
    public void onHit() {
        Bukkit.getLogger().info("Flaming bow hit!");
    }
}
