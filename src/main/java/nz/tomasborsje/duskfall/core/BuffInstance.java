package nz.tomasborsje.duskfall.core;

/**
 * An instance of a buff or debuff.
 */
public class BuffInstance {
    protected final MMOEntity entity;
    public int remainingDuration; // In ticks

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
