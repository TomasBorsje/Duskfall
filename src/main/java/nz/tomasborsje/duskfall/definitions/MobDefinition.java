package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;

public class MobDefinition implements Cloneable {

    @SerializedName("id")
    public String id;
    @SerializedName("classname")
    public String classname;

    @SerializedName("name")
    public String name;
    @SerializedName("damage")
    public int damage = 1;

    @SerializedName("health")
    public int maxHealth = 1;

    @Override
    public MobDefinition clone() {
        try {
            MobDefinition clone = (MobDefinition) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
