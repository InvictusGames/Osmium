package cc.invictusgames.hub.command;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.command.annotation.Command;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SpawnCommands {

    private final HubPlugin plugin;

    @Command(names = "spawn", playerOnly = true)
    public boolean spawnCommand(Player player) {
        if (plugin.getHubConfig().getSpawnLocation() == null) {
            player.sendMessage(ChatColor.RED + "The spawn location is not set, " +
                    "please contact a server administrator.");
            return true;
        }

        player.teleport(plugin.getHubConfig().getSpawnLocation());
        return true;
    }

    @Command(names = "setspawn", playerOnly = true,
            permission = "osmium.command.setspawn",
            async = true)
    public boolean setSpawnCommand(Player player) {
        plugin.getHubConfig().setSpawnLocation(player.getLocation());
        plugin.getHubConfig().saveConfig();

        player.sendMessage(ChatColor.GREEN + "You have set the spawn location.");
        return true;
    }
}
