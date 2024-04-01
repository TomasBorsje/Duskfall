package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.GlobalMessageType;
import nz.tomasborsje.duskfall.handlers.GlobalChatClient;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMessageListener implements Listener {
    @EventHandler
    public void OnChatMessage(AsyncPlayerChatEvent event) {
        // Cancel event, only show messages through BroadcastMessage
        event.setCancelled(true);

        // Get the player's name
        String playerName = event.getPlayer().getName();

        // Get the message
        String message = event.getMessage();

        // Broadcast the message
        boolean success = Duskfall.globalChat.sendGlobalMessage(GlobalMessageType.PLAYER_CHAT, playerName, message);
        if(!success) {
            event.getPlayer().sendMessage(ChatColor.RED+"Failed to send message to global chat server.");
        }
    }
}
