package nz.tomasborsje.duskfall.util;

/**
 * Public helper class that calculates differing stats for entities.
 */
public class Stats {
    /**
     * Calculate the base maximum health for a player at a given level.
     * @param level The player's level.
     * @return The player's base maximum health.
     */
    public static int BaseMaxHealthForPlayer(int level) {
        // TODO DEBUG: Player gains 20 max health per level
        return 50 + (15 * level);
    }

    /**
     * Calculate the base maximum health for a mob at a given level.
     * @param level The mob's level.
     * @return The mob's base maximum health.
     */
    public static int BaseMaxHealthForMob(int level) {
        // TODO DEBUG: Mob gains 10 max health per level
        return 20 + (10 * level);
    }

    public static int BaseMaxManaForPlayer(int level) {
        return 30 + (10 * level);
    }
}
