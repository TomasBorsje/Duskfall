package nz.tomasborsje.duskfall.core;

import net.md_5.bungee.api.chat.TextComponent;
import nz.tomasborsje.duskfall.definitions.ArmourDefinition;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.definitions.MeleeWeaponDefinition;
import nz.tomasborsje.duskfall.ui.screens.PlayerScreen;
import nz.tomasborsje.duskfall.ui.screens.ScreenManager;
import nz.tomasborsje.duskfall.util.Icons;
import nz.tomasborsje.duskfall.util.ItemUtil;
import nz.tomasborsje.duskfall.util.MathUtil;
import nz.tomasborsje.duskfall.util.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.md_5.bungee.api.ChatMessageType.ACTION_BAR;

/**
 * MMO-entity implementation of Player stats.
 */
public class MMOPlayer implements MMOEntity {
    private final Player bukkitPlayer;
    public final ScreenManager ui;
    public final InventoryManager inventory;
    private final List<BuffInstance> buffs = new ArrayList<>();
    private int level = 1;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int defense = 0;
    private int strength = 0;
    private int focus = 0;
    private int intelligence = 0;
    private int regenTimer = 0;
    private int ticksSinceCombat = 0;

    /**
     * Creates a new MMOPlayer with the given Bukkit player.
     * @param player The Bukkit player.
     */
    public MMOPlayer(Player player) {
        bukkitPlayer = player;
        ui = new ScreenManager(this);
        inventory = new InventoryManager(this);
        ticksSinceCombat = 250; // Out of combat
        recalculateStats();
        fillHealthAndMana();
    }

    /**
     * Fills the player's health and mana to their maximum.
     */
    private void fillHealthAndMana() {
        health = maxHealth;
        mana = maxMana;
    }

    /**
     * Recalculates the player's stats.
     */
    public void recalculateStats() {
        // Reset stats
        maxHealth = Stats.BaseMaxHealthForPlayer(level);
        maxMana = Stats.BaseMaxManaForPlayer(level);
        defense = 0;
        strength = 0;
        focus = 0;
        intelligence = 0;

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
            if (definition instanceof StatProvider stats && !(definition instanceof ArmourDefinition)) {
                addStatsForItem(stats);
            }
        }

        // Clamp health and mana to max
        if (health > maxHealth) {
            health = maxHealth;
        }
        if (mana > maxMana) {
            mana = maxMana;
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
        maxHealth += item.getHealthBoost();
        maxMana += item.getManaBoost();
        defense += item.getDefense();
    }

    @Override
    public void tick() {
        // Tick combat tracker
        ticksSinceCombat++;

        // Tick buffs
        for(BuffInstance buff : buffs) {
            buff.tick();
        }
        // Remove expired buffs
        buffs.removeIf(BuffInstance::isExpired);

        // Recalculate stats.
        recalculateStats();

        // Do regen tick
        regenStats();

        // Display action bar
        displayActionBarInfo();

        // Set vanilla health to be a representation of the MMO health (+0.1 to ensure we never die on MC's side)
        bukkitPlayer.setHealth(MathUtil.clamp((double)health / (double)maxHealth * 20.0, 0.1, 20.0));
        // Set food level to max
        bukkitPlayer.setFoodLevel(20);
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
            gainMana(1);
        }
    }

    /**
     * Increases the player's current mana.
     * @param mana The amount of mana to gain.
     */
    public void gainMana(int mana) {
        this.mana += mana;
        if(this.mana > maxMana) {
            this.mana = maxMana;
        }
    }


    /**
     * Hurt the player, taking into accounts their defense, buffs, etc.
     * @param damage The amount of damage to deal to the entity.
     */
    @Override
    public void hurt(MMOEntity source, int damage) {
        // TODO DEBUG: Use defense values, etc.
        markCombat();
        health -= damage;
        // If health is below 0, kill the player
        if(health <= 0) {
            kill(source);
        }
    }

    /**
     * Mark the player as being in combat.
     */
    public void markCombat() {
        ticksSinceCombat = 0;
    }

    /**
     * @return Whether the player is in combat or not.
     */
    public boolean isInCombat() {
        return ticksSinceCombat < 200;
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

    @Override
    public int getMeleeDamage() {
        // Get the player's held custom item
        ItemStack heldItem = bukkitPlayer.getEquipment().getItemInMainHand();

        // If it's a melee weapon, return its damage
        if (ItemUtil.IsCustomItem(heldItem)) {
            ItemDefinition definition = ItemUtil.GetPopulatedDefinition(heldItem);
            if (definition instanceof MeleeWeaponDefinition meleeWeaponDefinition) {
                return meleeWeaponDefinition.damage;
            }
        }

        return 1; // Return base melee damage of 1
    }

    @Override
    public String getEntityName() {
        return bukkitPlayer.getName();
    }

    /**
     * Kill the player, teleporting them to spawn and taking away some of their money, etc.
     */
    @Override
    public void kill(MMOEntity killer) {
        // TODO
        ticksSinceCombat = 1000; // Exit combat
        bukkitPlayer.sendMessage(ChatColor.RED + "You died to " + killer.getEntityName() + "!");
        fillHealthAndMana();
    }

    @Override
    public void addBuff(BuffInstance buffInstance) {
        buffs.add(buffInstance);
    }

    @Override
    public void removeBuff(BuffInstance buff) {
        buffs.remove(buff);
    }

    /**
     * Displays the info message above the player's hot-bar, showing their health and mana.
     */
    void displayActionBarInfo() {
        String message = "";

        // If we're in combat, add the combat icon to the left
        if(isInCombat()) {
            message += ChatColor.DARK_RED + "!"+Icons.StrengthIcon + "!  ";
        }

        // Add health/max health display
        message += ChatColor.RED + Icons.HealthIcon + " " + ChatColor.WHITE + health + "/" + maxHealth;
        // Add defense display
        message += " " + ChatColor.GREEN + Icons.DefenseIcon + " " + ChatColor.WHITE + defense;
        // Add mana display
        message += " " + ChatColor.BLUE + Icons.ManaIcon + " " + ChatColor.WHITE + mana + "/" + maxMana;

        // Add combat indicator to the right too
        if(isInCombat()) {
            message += "  " + ChatColor.DARK_RED +"!"+ Icons.StrengthIcon +"!";
        }

        // Display
        bukkitPlayer.spigot().sendMessage(ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    @Override
    public Player getBukkitEntity() {
        return bukkitPlayer;
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
