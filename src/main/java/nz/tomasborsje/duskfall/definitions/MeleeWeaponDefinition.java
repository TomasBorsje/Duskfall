package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;

public class MeleeWeaponDefinition extends ItemDefinition implements StatProvider {
    @SerializedName("damage")
    public int damage = 0;

    @SerializedName("strength")
    public int strength = 0;

    @SerializedName("intelligence")
    public int intelligence = 0;

    @SerializedName("focus")
    public int focus = 0;

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
    public int getDefense() {
        return 0;
    }

    @Override
    public int getHealth() {
        return 0;
    }
}
