package nz.tomasborsje.duskfall.core;

import net.minecraft.nbt.CompoundTag;

/**
 * Indicates that an item definition can be modified based on the ItemStack's NBT tag.
 */
public interface Populateable {
    void populate(CompoundTag nbt);
}
