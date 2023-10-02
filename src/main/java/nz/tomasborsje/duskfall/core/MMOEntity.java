package nz.tomasborsje.duskfall.core;

import org.bukkit.entity.LivingEntity;

/**
 * This is the in-world representation of both players and custom entities.
 */
public interface MMOEntity {

    /**
     * @return The entity's current melee damage. This is the current equipped weapon's damage for players.
     */
    public int getCurrentDamage();

    /**
     * @return The name of the entity.
     */
    public String getEntityName();

    /**
     * Kill the entity.
     * @param killer The entity that killed this entity.
     */
    void kill(MMOEntity killer);

    /**
     * @return The entity's Bukkit entity.
     */
    LivingEntity getBukkitEntity();

    /**
     * Tick the entity.
     */
    void tick();

    /**
     * @return Whether the entity is a player.
     */
    boolean isPlayer();

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
     * @param source The entity that is hurting this entity.
     * @param damage The amount of damage to deal to the entity.
     */
    void hurt(MMOEntity source, int damage);

    /**
     * Heal an entity, taking into accounts its buffs, etc.
     * @param health The amount of health to heal the entity.
     */
    void heal(int health);

    /**
     * @return The entity's level.
     */
    int getLevel();

    /**
     * Add a buff to the entity.
     */
    void addBuff(Buff buff);
}
