package cc.invictusgames.hub.npc;

import cc.invictusgames.hub.selector.ServerSelectorEntry;
import cc.invictusgames.ilib.hologram.updating.HologramProvider;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.InvictusBukkit;
import cc.invictusgames.invictus.profile.Profile;
import cc.invictusgames.invictus.rank.Rank;
import cc.invictusgames.invictus.server.ServerInfo;
import cc.invictusgames.invictus.server.ServerInfoProperty;
import cc.invictusgames.network.bukkit.property.GenericProperty;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NpcHologramProvider implements HologramProvider {

    private final ServerSelectorEntry entry;

    @Override
    public List<String> getRawLines(Player player) {
        List<String> lines = new ArrayList<>();

        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
        ServerInfo server = Invictus.getInstance().getServerService().getServer(entry.getServerName());

        int online = GenericProperty.ONLINE_PLAYERS.get(server).orElse(0);
        int max = GenericProperty.MAX_PLAYERS.get(server).orElse(0);

        int inQueue = InvictusBukkit.getBukkitInstance().getQueueService().getQueueing(entry.getServerName()).size();
        Rank rank = profile.getRealCurrentGrantOn(ServerInfoProperty.GROUP.get(server).orElse(entry.getServerName())).getRank();


        for (String line : entry.getNpcLines())
            lines.add(CC.translate(line)
                    .replaceAll("%online%", String.valueOf(online))
                    .replaceAll("%max%", String.valueOf(max))
                    .replaceAll("%in_queue%", String.valueOf(inQueue))
                    .replaceAll("%rank_on_scope%", rank.getDisplayName()));

        return lines;
    }
}
