package cc.invictusgames.hub.command;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.command.annotation.Command;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ParkourCommand {

    private final HubPlugin plugin;

    @Command(names = "parkour setstart", permission = "op", playerOnly = true)
    public boolean parkour(Player player) {
        plugin.getHubConfig().setParkourStart(player.getLocation());
        plugin.getHubConfig().saveConfig();
        player.sendMessage(ChatColor.YELLOW + "You have set the parkour start.");
        return true;
    }

}
