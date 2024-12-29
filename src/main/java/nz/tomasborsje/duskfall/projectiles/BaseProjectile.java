package nz.tomasborsje.duskfall.projectiles;

import com.mojang.math.Transformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import nz.tomasborsje.duskfall.core.*;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;

// TODO: This should be abstract.
public class BaseProjectile implements MMOProjectile {
    private final Vector position;
    private final Vector velocity;
    @Nullable
    private final MMOEntity owner;
    private final float hitboxSize = 0.5f;
    private final Vector3f scale = new Vector3f(0.5f, 0.5f, 0.5f);
    protected ServerLevel level;
    protected int maxLifeTime = 100;
    protected int remainingLifeTime;
    protected Display.BlockDisplay displayEntity;

    public BaseProjectile(@Nullable MMOEntity owner, ServerLevel level, Vector position, Vector velocity) {
        this.owner = owner;
        this.level = level;
        this.position = position;
        this.velocity = velocity;
        remainingLifeTime = maxLifeTime;

        // Add BlockDisplay entity that displays the projectile in world
        displayEntity = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, level);
        displayEntity.setBlockState(Blocks.MAGMA_BLOCK.defaultBlockState());
        displayEntity.setNoGravity(true);
        updatePosition();

        // Spawn display entity in world
        level.addFreshEntity(displayEntity);
    }

    protected void updatePosition() {
        // Move position by velocity
        position.add(velocity);
        displayEntity.setPos(position.getX() - 0.5, position.getY() - 0.5, position.getZ() - 0.5);

        // TODO: Smoother movement? Either using packets, the teleport_duration tag (how to set?) or transformation interpolation
        Quaternionf angle = new Quaternionf(new AxisAngle4f(remainingLifeTime * 10, 0, 1, 0));
        Transformation offset = new Transformation(null, null, scale, null);

        displayEntity.setTransformation(offset);
    }

    @Override
    public void onTick() {
        remainingLifeTime--;

        updatePosition();

        //displayEntity.setYRot(remainingLifeTime * 10);

        // If owner is not null, show them particles
        if (owner != null && owner.getBukkitEntity() instanceof Player bukkitPlayer) {
            // Spawn flame particles at the projectile's pos
            bukkitPlayer.getWorld().spawnParticle(Particle.FIREWORK, position.getX(), position.getY(), position.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void onHitEntity(MMOEntity hitEntity) {
        // If this is not our owner, deal damage
        if (owner != null && hitEntity != owner && isAlive()) {
            DamageInstance instance = new DamageInstance(MMODamageCause.ENTITY, DamageType.MAGIC, owner, 15);
            hitEntity.hurt(instance);
            setKilled();
        }
    }

    @Override
    public String getProjectileName() {
        return "Fireball";
    }

    @Override
    public void onHitBlock() {
        setKilled();
    }

    @Override
    public void onDeath() {
        // Explode, etc. if wanted.
    }

    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public Vector getPosition() {
        return this.position;
    }

    @Override
    public float getHitboxSize() {
        return hitboxSize;
    }

    @Override
    public boolean isAlive() {
        return remainingLifeTime > 0;
    }

    @Override
    public void setKilled() {
        remainingLifeTime = 0;
    }

    @Override
    public void kill() {
        onDeath();
        remainingLifeTime = 0;
        // Delete display entity
        displayEntity.remove(Entity.RemovalReason.KILLED);
    }
}
