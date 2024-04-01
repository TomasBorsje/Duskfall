package nz.tomasborsje.duskfall.core;

public enum ItemAddReason {
    LOOT("looted"),
    CRAFT("crafted"),
    GENERIC("got");

    public final String verb;

    ItemAddReason(String verb) {
        this.verb = verb;
    }
}
