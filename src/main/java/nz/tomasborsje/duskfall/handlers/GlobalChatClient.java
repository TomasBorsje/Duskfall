package nz.tomasborsje.duskfall.handlers;

import nz.tomasborsje.duskfall.core.GlobalMessageType;
import org.bukkit.Bukkit;
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

    public void sendGlobalMessage(GlobalMessageType type, String playerName, String message) {
        // Check we're connected
        if (!this.isOpen()) {
            return;
        }

        // If a player chat message, format with [Global] and send to server
        if (type == GlobalMessageType.PLAYER_CHAT) {
            this.send("[Global] " + playerName + ": " + message);
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        Bukkit.getLogger().info("Connected to the global chat server.");
    }

    @Override
    public void onMessage(String message) {
        Bukkit.getLogger().info("Received message: " + message);
        // Broadcast to every player
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Bukkit.getLogger().info("Connection to global chat server lost. Code: " + code + ", reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
    }

    /**
     * Runs every tick.
     */
    public void tick() {

    }
}
