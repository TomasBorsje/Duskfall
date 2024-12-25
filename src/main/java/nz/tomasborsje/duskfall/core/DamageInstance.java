package nz.tomasborsje.duskfall.core;

import javax.annotation.Nullable;

/**
 * An instance of damage, detailing the cause of damage, type, source, and attacker.
 */
public class DamageInstance {
    private final MMODamageCause cause;
    private final DamageType type;
    @Nullable
    private final MMOEntity attacker;
    private final int damageAmount;
    public DamageInstance(MMODamageCause cause,
                          DamageType type,
                          @Nullable MMOEntity attacker,
                          int damageAmount) {
        this.cause = cause;
        this.type = type;
        this.attacker = attacker;
        this.damageAmount = damageAmount;
    }

    public MMODamageCause getCause() {
        return cause;
    }
    public DamageType getType() {
        return type;
    }

    /**
     * Get the attacker/source of this damage.
     * For projects, this will be its owner.
     * For environmental damage, like fall damage or block contact damage, this will be null.
     * @return The attacker/source of this damage.
     */
    @Nullable
    public MMOEntity getAttacker() {
        return attacker;
    }
    public int getDamageAmount() {
        return damageAmount;
    }
}

