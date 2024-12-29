package nz.tomasborsje.duskfall.core;

import nz.tomasborsje.duskfall.definitions.ArrowItemDefinition;
import nz.tomasborsje.duskfall.definitions.BowWeaponDefinition;

public class FiredArrow {
    public final ArrowItemDefinition arrowDefinition;
    public final BowWeaponDefinition bowDefinition;
    public final MMOEntity owner;

    public FiredArrow(ArrowItemDefinition arrowDefinition, BowWeaponDefinition bowDefinition, MMOEntity owner) {
        this.arrowDefinition = (ArrowItemDefinition) arrowDefinition.clone();
        this.bowDefinition = bowDefinition;
        this.owner = owner;
    }
}
