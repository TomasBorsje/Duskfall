package nz.tomasborsje.duskfall.core;

/**
 * An instance of a buff or debuff.
 */
public class BuffInstance {
    public int remainingDuration; // In ticks
    protected final MMOEntity entity;
    public BuffInstance(MMOEntity entity, int duration) {
        this.entity = entity;
        this.remainingDuration = duration;
    }
    public void tick() {
        remainingDuration--;
    }

    public boolean isExpired() {
        return remainingDuration <= 0;
    }
}
