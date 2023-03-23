package cc.invictusgames.hub.selector;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.profile.HCFProfile;
import cc.invictusgames.hub.profile.deathban.Deathban;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.configuration.StaticConfiguration;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.server.ServerInfo;
import cc.invictusgames.invictus.server.ServerInfoProperty;
import cc.invictusgames.network.bukkit.property.GenericProperty;
import cc.invictusgames.network.protocol.server.ServerState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 05.03.2021 / 13:23
 * Osmium / cc.invictusgames.hub.selector.selector
 */

@NoArgsConstructor
@Data
public class ServerSelectorEntry implements StaticConfiguration {

    private int slot = 22;
    private Material material = Material.COMMAND_MINECART;
    private short subId = 0;
    private String name = "&a&lDev";
    private String serverName = "Dev";
    private List<String> description = Arrays.asList(
            "&7&m------------------------",
            "&7Description",
            "&fStatus: %status%",
            "&fPlayers: &a%online%&f/&a%max%",
            "&fLives: &a%lives%",
            "&fDeathban: &a%deathban_remaining%",
            "&7&m------------------------"
    );

    private Location npcLocation = null;
    private String npcSkin = "Notch";
    private List<String> npcLines = Arrays.asList(
            "&5&lDev",
            "&7&m------------------------",
            "&fPlayers: &d%online%&f/&d%max%",
            "&fIn Queue: &d%in_queue%",
            "&fYour Rank: %rank_on_scope%",
            "&7&m------------------------"
    );

    public ItemStack toItem(Player player) {
        ServerInfo server = Invictus.getInstance().getServerService().getServer(serverName);
        HCFProfile hcfProfile = HubPlugin.get().getProfileManager()
                .getProfile(player.getUniqueId()).getHCFProfile(serverName);
        int lives = hcfProfile != null ? hcfProfile.getLives() : 0;
        Deathban deathban = hcfProfile != null ? hcfProfile.getDeathban() : null;

        ServerState state = ServerInfoProperty.STATE.get(server).orElse(ServerState.UNKNOWN);
        int online = GenericProperty.ONLINE_PLAYERS.get(server).orElse(0);
        int max = GenericProperty.MAX_PLAYERS.get(server).orElse(0);

        return new ItemBuilder(material, subId)
                .setDisplayName(CC.translate(name))
                .setLore(description.stream()
                        .map(s -> s = CC.translate(s)
                                .replaceAll("%status%", HubPlugin.getStateName(player, server))
                                .replaceAll("%online%", String.valueOf(online))
                                .replaceAll("%max%", String.valueOf(max))
                                .replaceAll("%lives%", String.valueOf(lives))
                                .replaceAll("%deathban_remaining%",
                                        (deathban != null ? deathban.getRemaining() : "Not Deathbanned"))

                        ).collect(Collectors.toList()))
                .build();
    }

    public ServerInfo getServer() {
        return Invictus.getInstance().getServerService().getServer(serverName);
    }
}
