package nz.tomasborsje.duskfall.registries;

import net.minecraft.nbt.CompoundTag;

/**
 * Indicates that an item definition can be modified based on the ItemStack's NBT tag.
 */
public interface PopulateableDefinition {
    void populate(CompoundTag nbt);
}
