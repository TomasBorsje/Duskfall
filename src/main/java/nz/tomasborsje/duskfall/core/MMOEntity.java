package nz.tomasborsje.duskfall.core;

import org.bukkit.entity.LivingEntity;

/**
 * This is the in-world representation of both players and custom entities.
 */
public interface MMOEntity {

    /**
     * @return The entity's current melee damage. This is the current equipped weapon's damage for players.
     */
    int getMeleeDamage();

    /**
     * @return The name of the entity.
     */
    String getEntityName();

    /**
     * Kill the entity.
     *
     * @param killingBlow The damage instance that caused death.
     */
    void kill(DamageInstance killingBlow);

    /**
     * @return The entity's Bukkit entity.
     */
    LivingEntity getBukkitEntity();

    /**
     * Tick the entity.
     */
    void tick();

    /**
     * @return The entity's current health.
     */
    int getHealth();

    /**
     * @return The entity's maximum health.
     */
    int getMaxHealth();

    /**
     * Hurt an entity, taking into accounts its defense, buffs, etc.
     *
     * @param damageInstance The damage instance to hurt the entity with
     */
    void hurt(DamageInstance damageInstance);

    /**
     * Heal an entity, taking into accounts its buffs, etc.
     *
     * @param health The amount of health to heal the entity.
     */
    void heal(int health);

    /**
     * Adds a buff to the entity that will be ticked, etc.
     *
     * @param buff The buff to add.
     */
    void addBuff(BuffInstance buff);

    /**
     * Removes a buff from the entity.
     *
     * @param buff The buff to remove.
     */
    void removeBuff(BuffInstance buff);

    /**
     * @return The entity's level.
     */
    int getLevel();

    /**
     * @return Whether the entity is in combat or not.
     */
    boolean isInCombat();
}
