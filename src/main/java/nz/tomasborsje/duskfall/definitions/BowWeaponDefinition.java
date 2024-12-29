package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.StatProviderCondition;
import nz.tomasborsje.duskfall.core.Usable;

import javax.annotation.Nonnull;

public class BowWeaponDefinition extends StatProvidingItemDefinition implements Usable {

    @SerializedName("damage")
    private int damage = 1;

    public int getDamage() {
        return damage;
    }

    @Nonnull
    @Override
    public StatProviderCondition getStatProviderCondition() {
        return StatProviderCondition.WHEN_HELD;
    }

    @Override
    public void onPlayerUse(MMOEntity user) {

    }

    @Override
    public String getUseDescription() {
        return "";
    }

    @Override
    public boolean isConsumedOnUse() {
        return false;
    }

    @Override
    public boolean allowVanillaUse() {
        return true;
    }

    public void OnFireArrow(ArrowItemDefinition arrowDef) {
        // Add bow's damage to arrow
        //Bukkit.broadcastMessage("Someone fired an arrow of type "+arrowDef.name);
    }
}
