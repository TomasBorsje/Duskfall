package nz.tomasborsje.duskfall.util;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.MMOPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Holds helper methods to play sounds to players.
 */
public class Sounds {
    public static void PlayLockboxOpenSound(MMOPlayer mmoPlayer) {
        Player player = mmoPlayer.getBukkitEntity();
        // Play chest opening sound to player with a higher pitch
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1.5f);
        // Play 3 rising note block sounds over 15 ticks
        Bukkit.getScheduler().runTaskLater(Duskfall.plugin, () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        }, 5);
        Bukkit.getScheduler().runTaskLater(Duskfall.plugin, () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.12f);
        }, 10);
        Bukkit.getScheduler().runTaskLater(Duskfall.plugin, () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1.26f);
        }, 15);
    }
}
