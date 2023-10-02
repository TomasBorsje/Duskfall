package nz.tomasborsje.duskfall.core;

import net.md_5.bungee.api.chat.TextComponent;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.definitions.MeleeWeaponDefinition;
import nz.tomasborsje.duskfall.definitions.StatProvider;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.util.Icons;
import nz.tomasborsje.duskfall.util.ItemUtil;
import nz.tomasborsje.duskfall.util.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.md_5.bungee.api.ChatMessageType.ACTION_BAR;

/**
 * MMO-entity implementation of Player stats.
 */
public class MMOPlayer implements MMOEntity {
    private final Player bukkitPlayer;
    private int health;
    private int maxHealth;
    private int level = 1;
    private int defense = 0;
    private int strength = 0;
    private int focus = 0;
    private int intelligence = 0;

    private int regenTimer = 0;

    /**
     * Creates a new MMOPlayer with the given Bukkit player.
     * @param player The Bukkit player.
     */
    public MMOPlayer(Player player) {
        bukkitPlayer = player;
        fillHealthAndMana();
        recalculateStats();
    }

    /**
     * Fills the player's health and mana to their maximum.
     */
    private void fillHealthAndMana() {
        health = maxHealth;
    }

    /**
     * Recalculates the player's stats.
     */
    public void recalculateStats() {
        maxHealth = Stats.BaseMaxHealthForPlayer(level);

        // For each equipped armour piece, gets its definition and add its stats
        for (ItemStack armorItem : bukkitPlayer.getEquipment().getArmorContents()) {
            if (ItemUtil.IsCustomItem(armorItem)) {

                ItemDefinition definition = ItemUtil.GetPopulatedDefinition(armorItem);
                if (definition instanceof StatProvider stats) {
                    addStatsForItem(stats);
                }
            }
        }
        // Do the same for held item, if it is a melee weapon definition (TODO: Staff/bow defs too)
        ItemStack heldItem = bukkitPlayer.getEquipment().getItemInMainHand();
        if (ItemUtil.IsCustomItem(heldItem)) {

            ItemDefinition definition = ItemUtil.GetPopulatedDefinition(heldItem);
            if (definition instanceof MeleeWeaponDefinition) {
                StatProvider stats = (StatProvider) definition;
                addStatsForItem(stats);
            }
        }

        // Clamp health and mana to max
        if(health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Adds the stats from the given item to the player's stats.
     * @param item The item to add the stats from.
     */
    private void addStatsForItem(StatProvider item) {
        strength += item.getStrength();
        focus += item.getFocus();
        intelligence += item.getIntelligence();
        maxHealth += item.getHealth();
        defense += item.getDefense();
    }

    @Override
    public void tick() {
        // Recalculate stats.
        recalculateStats();

        // Do regen tick
        regenStats();

        // Display action bar
        displayActionBarInfo();
    }

    /**
     * Increments an internal counter that regenerates the player's stats every 20 ticks.
     */
    private void regenStats() {
        regenTimer++;

        // Regen every 20 ticks
        if(regenTimer >= 20) {
            regenTimer = 0;

            // Regen health
            // TODO DEBUG: Regen 1 health per tick
            heal(1);
        }
    }


    /**
     * Hurt the player, taking into accounts their defense, buffs, etc.
     * @param damage The amount of damage to deal to the entity.
     */
    @Override
    public void hurt(int damage) {
        // TODO DEBUG: Use defense values, etc.
        health -= damage;
        // If health is below 0, kill the player
        if(health <= 0) {
            kill();
        }
    }

    /**
     * Heal the player, taking into accounts their buffs, etc.
     * @param health The amount of health to heal the player.
     */
    @Override
    public void heal(int health) {
        // Heal and clamp health
        this.health += health;
        if(this.health > maxHealth) {
            this.health = maxHealth;
        }
    }


    /**
     * Kill the player, teleporting them to spawn and taking away some of their money, etc.
     */
    @Override
    public void kill() {
        // TODO
        bukkitPlayer.sendMessage(ChatColor.RED + "You died!");
        fillHealthAndMana();
    }

    /**
     * Add a buff to the player's buff list.
     * @param buff The buff to add.
     */
    @Override
    public void addBuff(Buff buff) {
        // TODO
    }

    /**
     * Displays the info message above the player's hot-bar, showing their health and mana.
     */
    void displayActionBarInfo() {
        String message = "";

        // Add health/max health display
        message += ChatColor.RED + Icons.HealthIcon + " " + ChatColor.WHITE + health + "/" + maxHealth;

        // Display
        bukkitPlayer.spigot().sendMessage(ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitPlayer;
    }

    @Override
    public boolean isPlayer() {
        return true;
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
}
