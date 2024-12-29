package nz.tomasborsje.duskfall.handlers;

import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.MMOProjectile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.HashSet;

/**
 * Stores all custom projectiles and their states as the server is running.
 */
public class ProjectileHandler {
    private static final HashSet<MMOProjectile> projectiles = new HashSet<>();

    /**
     * Ticks all custom projectiles, removing them if onRemove() returns true afterward.
     */
    public static void Tick() {
        for (MMOProjectile projectile : projectiles) {
            try {
                projectile.onTick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Check collision of every projectile and every living entity (distance of 0.5 or less between proj and entity)
        World world = Bukkit.getServer().getWorld("world");
        for (MMOProjectile projectile : projectiles) {

            // Check if any nearby entities (within 0.5m of projectile pos)
            Vector3f position = projectile.getPosition().toVector3f();
            float hitboxSize = projectile.getHitboxSize();
            BoundingBox hitbox = new BoundingBox(
                    position.x - hitboxSize, position.y - hitboxSize, position.z - hitboxSize,
                    position.x + hitboxSize, position.y + hitboxSize, position.z + hitboxSize
            );

            // Get all nearby NMS entities, hitting them if they are an MMO entity
            world.getNearbyEntities(hitbox).forEach(entity -> {
                @Nullable MMOEntity mmoEntity = EntityHandler.GetEntity(entity);
                if (mmoEntity != null) {
                    projectile.onHitEntity(mmoEntity);
                }
            });

            // Check if the projectile's position is inside a non-solid block
            if (world.getBlockAt((int) Math.floor(position.x), (int) Math.floor(position.y), (int) Math.floor(position.z)).getType().isSolid()) {
                projectile.onHitBlock();
            }
        }

        // Call onDeath for all projectiles that should be removed, then remove them
        projectiles.removeIf(projectile -> {
            if (!projectile.isAlive()) {
                projectile.kill();
                return true;
            }
            return false;
        });
    }

    /**
     * Adds a custom projectile to the handler.
     *
     * @param projectile The custom projectile to add.
     */
    public static void AddProjectile(MMOProjectile projectile) {
        projectiles.add(projectile);
    }
}
