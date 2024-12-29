package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.core.MMOEntity;

public class ArrowItemDefinition extends ItemDefinition {
    @SerializedName("damage")
    private int arrow_damage = 1;

    public int getArrowDamage() {
        return arrow_damage;
    }

    public void HitEntity(MMOEntity owner, MMOEntity hit) {
        // Hit effects, etc.

    }
}
