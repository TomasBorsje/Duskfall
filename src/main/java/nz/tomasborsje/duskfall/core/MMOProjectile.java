package nz.tomasborsje.duskfall.core;

import org.bukkit.util.Vector;

public interface MMOProjectile {
    public void onTick();
    public void onHitEntity(MMOEntity hitEntity);
    public String getProjectileName();
    public void onHitBlock();
    public void onDeath();
    public Vector getVelocity();
    public Vector getPosition();
    public float getHitboxSize();
    public boolean isAlive();
    public void setKilled();

    void kill();
}
