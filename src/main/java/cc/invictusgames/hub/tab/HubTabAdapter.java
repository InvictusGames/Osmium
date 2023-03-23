package cc.invictusgames.hub.tab;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.selector.ServerSelectorEntry;
import cc.invictusgames.ilib.tab.TabAdapter;
import cc.invictusgames.ilib.tab.TabEntry;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import cc.invictusgames.invictus.server.ServerInfo;
import cc.invictusgames.invictus.server.ServerInfoProperty;
import cc.invictusgames.network.bukkit.property.GenericProperty;
import cc.invictusgames.network.protocol.server.ServerState;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class HubTabAdapter implements TabAdapter {

    private final HubPlugin plugin;

    @Override
    public Table<Integer, Integer, TabEntry> getEntries(Player player) {
        Table<Integer, Integer, TabEntry> entries = HashBasedTable.create();
        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);

        entries.put(0, 4, new TabEntry("§6§lHub"));
        entries.put(0, 5, new TabEntry(Invictus.getInstance().getServerName()));

        entries.put(1, 1, new TabEntry("§6§l" + plugin.getHubConfig().getServerName()));
        entries.put(1, 2, new TabEntry(Invictus.getInstance().getServerService().getGlobalOnlineCount() + " / 1,000"));

        entries.put(1, 4, new TabEntry("§6§lRank"));
        entries.put(1, 5, new TabEntry(profile.getCurrentGrant().getRank().getDisplayName()
                + (profile.isDisguised() ? " &7(" + profile.getRealCurrentGrant().getRank().getDisplayName() + "&7)"
                : "")));

        entries.put(2, 4, new TabEntry("§6§lStore"));
        entries.put(2, 5, new TabEntry(ChatColor.WHITE + plugin.getHubConfig().getStore()));

        int x = 0;
        int y = 7;

        for (ServerSelectorEntry entry : plugin.getHubConfig().getServerSelector()) {
            ServerInfo server = Invictus.getInstance().getServerService().getServer(entry.getServerName());

            ServerState state = ServerInfoProperty.STATE.get(server).orElse(ServerState.UNKNOWN);
            int online = GenericProperty.ONLINE_PLAYERS.get(server).orElse(0);
            int max = GenericProperty.MAX_PLAYERS.get(server).orElse(0);

            entries.put(x, y, new TabEntry(ChatColor.GOLD + ChatColor.BOLD.toString() + entry.getServerName()));
            entries.put(x, y + 1, new TabEntry(ChatColor.GOLD + "Status: " + ChatColor.WHITE
                    + HubPlugin.getStateName(player, server)));
            entries.put(x, y + 2, new TabEntry(ChatColor.GOLD + "Players: "
                    + ChatColor.WHITE + online + " / " + max));

            if (x == 3) {
                y += 2;
                x = 0;
            }

            x++;
        }

        return entries;
    }

    @Override
    public String getHeader(Player player) {
        return ChatColor.GOLD + ChatColor.BOLD.toString() + plugin.getHubConfig().getServerName();
    }

    @Override
    public String getFooter(Player player) {
        return ChatColor.GRAY.toString() + ChatColor.ITALIC + plugin.getHubConfig().getStore();
    }
}
