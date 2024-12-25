package nz.tomasborsje.duskfall.definitions.custom;

import net.minecraft.server.level.ServerLevel;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.Usable;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.projectiles.BaseProjectile;
import org.bukkit.craftbukkit.v1_21_R3.CraftWorld;
import nz.tomasborsje.duskfall.handlers.ProjectileHandler;

public class WandOfSparkingDefinition extends ItemDefinition implements Usable {

    @Override
    public void onPlayerUse(MMOEntity user) {
        ServerLevel level = ((CraftWorld)user.getBukkitEntity().getWorld()).getHandle();
        // Spawn fireball in the direction the player is facing
        BaseProjectile fireball = new BaseProjectile(user, level, user.getBukkitEntity().getEyeLocation().toVector(), user.getBukkitEntity().getLocation().getDirection().multiply(1));
        // Add fireball to projectile handler
        ProjectileHandler.AddProjectile(fireball);
    }

    @Override
    public String getUseDescription() {
        return "Cast a fireball";
    }

    @Override
    public boolean isConsumedOnUse() {
        return false;
    }

    @Override
    public boolean allowVanillaUse() {
        return false;
    }
}
