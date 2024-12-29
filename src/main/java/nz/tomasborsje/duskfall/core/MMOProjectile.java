package nz.tomasborsje.duskfall.core;

import org.bukkit.util.Vector;

public interface MMOProjectile {
    void onTick();

    void onHitEntity(MMOEntity hitEntity);

    String getProjectileName();

    void onHitBlock();

    void onDeath();

    Vector getVelocity();

    Vector getPosition();

    float getHitboxSize();

    boolean isAlive();

    void setKilled();

    void kill();
}
