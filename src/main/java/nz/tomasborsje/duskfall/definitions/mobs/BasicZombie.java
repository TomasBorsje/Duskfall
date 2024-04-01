package nz.tomasborsje.duskfall.definitions.mobs;

import net.minecraft.world.entity.monster.Zombie;
import nz.tomasborsje.duskfall.core.NMSMob;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;

public class BasicZombie extends Zombie implements NMSMob {
    public BasicZombie(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle());
    }
}
