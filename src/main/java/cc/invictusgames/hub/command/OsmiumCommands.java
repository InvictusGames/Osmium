package cc.invictusgames.hub.command;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.hub.selector.ServerSelectorEntry;
import cc.invictusgames.ilib.command.annotation.Command;
import cc.invictusgames.ilib.command.annotation.CommandCooldown;
import cc.invictusgames.ilib.command.annotation.Param;
import cc.invictusgames.ilib.utils.CC;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class OsmiumCommands {

    private final HubPlugin plugin;

    @Command(names = "osmium build", permission = "op",
            description = "Enable build mode.")
    public boolean buildMode(Player player) {
        Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        profile.setBuildMode(!profile.isBuildMode());

        player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.RED
                + (profile.isBuildMode() ? "enabled" : "disabled") + ChatColor.YELLOW + " build mode.");

        return true;
    }

    //Can't run this command async due to "Asynchronous scoreboard creation" when editing anything to do with scoreboard.
    @Command(names = "osmium reload", permission = "op",
            description = "Reload the osmium config.")
    //Command cooldown because the command can be heavy if used multiple times very quickly
    @CommandCooldown(time = 5, global = true)
    public boolean reloadConfig(CommandSender commandSender) {
        plugin.getNpcManager().despawnEverything();
        plugin.loadConfig();
        plugin.getNpcManager().loadNpcs();
        commandSender.sendMessage(ChatColor.GREEN + "You have reloaded the osmium config.");
        return true;
    }

    @Command(names = {"osmium setnpclocation"},
            permission = "op",
            description = "Set the location of a selector npc",
            playerOnly = true)
    public boolean setNpcLocation(Player sender, @Param(name = "serverName") String serverName) {
        ServerSelectorEntry entry = plugin.getHubConfig().getServerSelector().stream()
                .filter(entry1 -> entry1.getServerName().equalsIgnoreCase(serverName))
                .findFirst()
                .orElse(null);

        if (entry == null) {
            sender.sendMessage(ChatColor.GREEN + "Selector entry not found.");
            return false;
        }

        plugin.getNpcManager().despawnEverythingOf(entry.getServerName());
        entry.setNpcLocation(sender.getLocation());
        plugin.getNpcManager().spawnForEntry(entry);
        plugin.getHubConfig().saveConfig();
        sender.sendMessage(CC.format("&aYou set the npc location for &e%s&a.", entry.getServerName()));
        return true;
    }
}
