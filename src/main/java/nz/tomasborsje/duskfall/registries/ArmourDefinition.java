package nz.tomasborsje.duskfall.registries;

import com.google.gson.annotations.SerializedName;

public class ArmourDefinition extends ItemDefinition {
    @SerializedName("defense")
    public int defense = 0;
    // etc...
}
