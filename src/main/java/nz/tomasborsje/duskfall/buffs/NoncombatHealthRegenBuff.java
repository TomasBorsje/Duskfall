package nz.tomasborsje.duskfall.buffs;

import nz.tomasborsje.duskfall.core.BuffInstance;
import nz.tomasborsje.duskfall.core.MMOEntity;

/**
 * A buff that regenerates health over time, ending if the entity enters combat.
 */
public class NoncombatHealthRegenBuff extends BuffInstance {
    private final float healPerTick;
    private float fractionalHealing;  // To store fractional healing amounts

    public NoncombatHealthRegenBuff(MMOEntity entity, int duration, float healAmount) {
        super(entity, duration);
        this.healPerTick = healAmount / (float)duration;
        this.fractionalHealing = 0.0f;
    }

    @Override
    public void tick() {
        super.tick();
        // Calculate the total healing (integer part)
        int totalHealing = (int)(healPerTick + fractionalHealing);

        // Apply the integer part of healing to the entity's health
        if(totalHealing > 0) {
            entity.heal(totalHealing);
        }

        // Update fractional healing with the remaining decimal part
        fractionalHealing = (fractionalHealing + healPerTick) % 1.0f;
    }

    @Override
    public boolean isExpired() {
        // Remove if we are in combat or if the player is max health
        return super.isExpired() || entity.isInCombat() || entity.getHealth() >= entity.getMaxHealth();
    }
}