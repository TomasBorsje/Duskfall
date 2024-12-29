package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.StatProviderCondition;

import javax.annotation.Nonnull;

public class MeleeWeaponDefinition extends StatProvidingItemDefinition {
    @SerializedName("damage")
    private int damage = 0;

    public int getDamage() {
        return damage;
    }

    @Nonnull
    @Override
    public StatProviderCondition getStatProviderCondition() {
        return StatProviderCondition.WHEN_HELD;
    }
}
