package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.StatProvider;

public class ArmourDefinition extends ItemDefinition implements StatProvider {
    @SerializedName("defense")
    public int defense = 0;

    @SerializedName("strength")
    public int strength = 0;

    @SerializedName("intelligence")
    public int intelligence = 0;

    @SerializedName("focus")
    public int focus = 0;

    @SerializedName("health")
    public int health = 0;

    @SerializedName("mana")
    public int mana = 0;

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getIntelligence() {
        return intelligence;
    }

    @Override
    public int getFocus() {
        return focus;
    }

    @Override
    public int getHealthBoost() {
        return health;
    }

    @Override
    public int getManaBoost() {
        return mana;
    }
}
