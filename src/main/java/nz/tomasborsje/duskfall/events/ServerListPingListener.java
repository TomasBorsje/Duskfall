package nz.tomasborsje.duskfall.events;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Displays a custom MOTD for the server on the server list.
 */
public class ServerListPingListener implements Listener {
    @EventHandler
    public static void onServerListPing(ServerListPingEvent event) {
        String version = "v0.0.2";
        // 44 characters per line (the 45th goes offscreen, but must still be accounted for)
        String firstLine = ChatColor.BLUE+ChatColor.BOLD.toString()+StringUtils.center(ChatColor.RESET.toString()+ChatColor.BLUE+"☽ "+ChatColor.DARK_PURPLE+ChatColor.BOLD+" Duskfall "+ChatColor.RESET+ChatColor.BLUE+" ☽", 60)+" "+ChatColor.WHITE+version;
        // Set motd
        event.setMotd(firstLine + "\n" + centerText(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "v0.0.2 - Pre Alpha", 58));

        // Set player count
        event.setMaxPlayers(999);
    }

    // lineLength = 80 (Chat Length)
    // lineLength = 45 (MOTD Length)
    // (This doesn't work)
    public static String centerText(String text, int lineLength) {
        char[] chars = text.toCharArray(); // Get a list of all characters in text
        boolean isBold = false;
        double length = 0;
        ChatColor pholder;
        for (int i = 0; i < chars.length; i++) { // Loop through all characters
            // Check if the character is a ColorCode..
            if (chars[i] == '&' && chars.length != (i + 1) && (pholder = ChatColor.getByChar(chars[i + 1])) != null) {
                if (pholder != ChatColor.UNDERLINE && pholder != ChatColor.ITALIC
                        && pholder != ChatColor.STRIKETHROUGH && pholder != ChatColor.MAGIC) {
                    isBold = (chars[i + 1] == 'l'); // Setting bold  to true or false, depending on if the ChatColor is Bold.
                    length--; // Removing one from the length, of the string, because we don't wanna count color codes.
                    i += isBold ? 1 : 0;
                }
            } else {
                // If the character is not a color code:
                length++; // Adding a space
                length += (isBold ? (chars[i] != ' ' ? 0.1555555555555556 : 0) : 0); // Adding 0.156 spaces if the character is bold.
            }
        }

        double spaces = (lineLength - length) / 2; // Getting the spaces to add by (max line length - length) / 2

        // Adding the spaces
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            builder.append(' ');
        }
        String copy = builder.toString();
        builder.append(text).append(copy);

        return builder.toString();
    }
}
