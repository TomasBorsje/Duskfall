package nz.tomasborsje.duskfall.core;

import nz.tomasborsje.duskfall.definitions.MobDefinition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

/**
 * Represents a non-player entity capable of combat.
 */
public class MMOMob implements MMOEntity {
    private final MobDefinition definition;
    private final LivingEntity bukkitEntity;
    private int health = 1;
    private int maxHealth = 1;
    private int level = 1;

    public MMOMob(LivingEntity entity, MobDefinition definition) {
        bukkitEntity = entity;
        this.definition = definition;
        this.maxHealth = definition.maxHealth;
        this.health = maxHealth;
    }

    @Override
    public void tick() {
        updateNamePlate();
    }

    /**
     * Updates the name plate of the entity to reflect its current state.
     */
    private void updateNamePlate() {
        bukkitEntity.setCustomName(ChatColor.WHITE+"["+ChatColor.GOLD+level+ChatColor.WHITE+"] "+ChatColor.GREEN+definition.name+ChatColor.GRAY+" ("+health+"/"+maxHealth+")");
    }

    @Override
    public void hurt(MMOEntity source, int damage) {
        if(health <= 0) { return; } // Can't hurt dead entities
        health -= damage;
        if (health <= 0) {
            health = 0;
            updateNamePlate();
            kill(source);
        }
    }

    @Override
    public void heal(int health) {
        this.health += health;
        if (this.health > maxHealth) {
            this.health = maxHealth;
            updateNamePlate();
        }
    }

    @Override
    public void addBuff(Buff buff) {

    }

    /**
     * Called when a mob dies to another attacker. If the killer is a player, they will receive experience and loot, etc.
     * @param killer The entity that killed this entity.
     */
    @Override
    public void kill(MMOEntity killer) {
        bukkitEntity.setHealth(0);

        // If killed by a player...
        if(killer.isPlayer()) {
            // TODO loot exp etc
            killer.getBukkitEntity().sendMessage("You killed "+getEntityName()+"!");
        }
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getCurrentDamage() {
        return definition.damage;
    }

    public String getEntityName() {
        return definition.name;
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }
}
