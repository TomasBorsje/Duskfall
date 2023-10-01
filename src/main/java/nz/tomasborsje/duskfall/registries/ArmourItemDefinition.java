package nz.tomasborsje.duskfall.registries;

import com.google.gson.annotations.SerializedName;

public class ArmourItemDefinition extends ItemDefinition {
    @SerializedName("defense")
    public int defense = 0;
    // etc...
}
