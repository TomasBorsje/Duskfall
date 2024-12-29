package nz.tomasborsje.duskfall.handlers;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.GlobalMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Handles chat messages, broadcasting them to the global chat server and receiving messages from other servers.
 */
public class GlobalChatClient extends WebSocketClient {
    private static final URI serverUri = URI.create("ws://localhost:7272");

    public GlobalChatClient() {
        super(serverUri);
    }

    /**
     * Sends a message to the global chat server. The message will be broadcast to all players on all servers.
     *
     * @param type       The type of message to send.
     * @param playerName The name of the player who sent the message.
     * @param message    The message to send.
     * @return Whether the message was sent successfully.
     */
    public boolean sendGlobalMessage(GlobalMessageType type, String playerName, String message) {
        // Check we're connected
        if (!this.isOpen()) {
            return false;
        }

        // If a player chat message, format with [Global] and send to server
        if (type == GlobalMessageType.PLAYER_CHAT) {
            this.send("[MC] " + playerName + ": " + message);
        }
        return true;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        Duskfall.logger.info("Connected to the global chat server.");
    }

    @Override
    public void onMessage(String message) {
        Duskfall.logger.info("Received message: " + message);
        // If a discord message...
        if (message.startsWith("[Discord]")) {
            // colour [Discord] purple and the rest gray
            message = message.replace("[Discord]", ChatColor.LIGHT_PURPLE + "[Discord]" + ChatColor.GRAY);
        }
        // Broadcast to every player
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Duskfall.logger.info("Connection to global chat server lost. Code: " + code + ", reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        Duskfall.logger.severe("WebSocket error: " + ex.getMessage());
    }

    /**
     * Runs every tick.
     */
    public void tick() {

    }
}
