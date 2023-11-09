package nz.tomasborsje.duskfall.core;

/**
 * Interface for item definitions that provide stats.
 */
public interface StatProvider {
    /**
     * @return The defense bonus of the item.
     */
    int getDefense();

    /**
     * @return The strength bonus of the item.
     */
    int getStrength();

    /**
     * @return The dexterity bonus of the item.
     */
    int getIntelligence();

    /**
     * @return The focus bonus of the item.
     */
    int getFocus();

    /**
     * @return The health bonus of the item.
     */
    int getHealthBoost();

    /**
     * @return The mana bonus of the item.
     */
    int getManaBoost();

    /**
     * @return Whether the item actually has any stat bonuses. Included as not every melee weapon will have stats despite
     * the base class implementing this interface.
     */
    default boolean hasStats() {
        return getDefense() > 0 || getStrength() > 0 || getIntelligence() > 0 || getFocus() > 0 || getHealthBoost() > 0 || getManaBoost() > 0;
    }
}
