package nz.tomasborsje.duskfall.core;

import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for in-world mobs used by custom entities.
 */
public interface NMSMob {
    default LivingEntity asNMS() {
        return (LivingEntity) this;
    }
}
