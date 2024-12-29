package nz.tomasborsje.duskfall.definitions;

import nz.tomasborsje.duskfall.core.StatProviderCondition;

import javax.annotation.Nonnull;

public class ArmourDefinition extends StatProvidingItemDefinition {

    @Nonnull
    @Override
    public StatProviderCondition getStatProviderCondition() {
        return StatProviderCondition.WHEN_WORN;
    }
}
