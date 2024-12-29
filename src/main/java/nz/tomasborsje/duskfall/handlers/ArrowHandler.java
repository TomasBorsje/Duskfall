package nz.tomasborsje.duskfall.handlers;

import nz.tomasborsje.duskfall.core.FiredArrow;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.definitions.ArrowItemDefinition;
import nz.tomasborsje.duskfall.definitions.BowWeaponDefinition;
import org.bukkit.entity.Arrow;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Maps Bukkit Arrow entities to ItemDefinitions and owners.
 */
public class ArrowHandler {
    private final HashMap<Integer, FiredArrow> map = new HashMap<>();

    public void Add(Arrow arrow, ArrowItemDefinition arrowDef, BowWeaponDefinition bowDef, MMOEntity owner) {
        map.put(arrow.getEntityId(), new FiredArrow(arrowDef, bowDef, owner));
    }

    /**
     * Get the fired arrow details of the given arrow. Null if it is not managed by this arrow handler.
     *
     * @param arrow The arrow to get details for
     * @return FiredArrow details if the arrow is handled by us, null otherwise.
     */
    public @Nullable FiredArrow Get(Arrow arrow) {
        return map.getOrDefault(arrow.getEntityId(), null);
    }

    /**
     * Remove stored arrow details for this arrow. Call this when the arrow is removed, etc.
     *
     * @param arrow The arrow whose details to remove.
     */
    public void Remove(Arrow arrow) {
        map.remove(arrow.getEntityId());
    }
}
