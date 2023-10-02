package nz.tomasborsje.duskfall.definitions;

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
    int getHealth();
}
