package cc.invictusgames.hub;

import cc.invictusgames.hub.selector.ServerSelectorEntry;
import cc.invictusgames.ilib.configuration.StaticConfiguration;
import cc.invictusgames.ilib.configuration.defaults.MongoConfig;
import cc.invictusgames.ilib.configuration.defaults.SimpleLocationConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class HubConfig implements StaticConfiguration {

    private MongoConfig mongoConfig = new MongoConfig();

    private boolean hidePlayers = false;

    private List<String> hcfServers
            = Arrays.asList("HCF");

    //private String scoreboardTitle = "&6&lArson &7┃ &fHub";
    private String scoreboardTitle = "Hub";
    private String serverName = "Arson";
    private String serverAddress = "arson.gg";
    private String store = "store.arson.net";

    private List<String> scoreBoardLines =
            Arrays.asList(
                    "&1&7&m--------------------",
                    "&6Online:",
                    " &f%onlinecount% / 1,000",
                    " ",
                    "&6Rank:",
                    " %rank%",
                    " ",
                    "&7&o%server_address%",
                    "&7&m--------------------");

    private List<String> scoreBoardQueueLines =
            Arrays.asList(
                    "&6Queue:",
                    " %queue_name% &7(#%queue_position% / %queue_total%)",
                    ""
            );

    private int selectorSize = 45;
    private String selectorFiller = "BORDER";
    private List<ServerSelectorEntry> serverSelector = Collections.singletonList(new ServerSelectorEntry());

    private Location spawnLocation;
    private Location parkourStart;

    private List<SimpleLocationConfig> staffSignLocations = new ArrayList<>();

    public void addStaffSign(Location location) {
        staffSignLocations.add(new SimpleLocationConfig(location, true));
    }

    public boolean removeStaffSignAt(Location location) {
        return staffSignLocations.removeIf(config -> config.getX() == location.getBlockX()
                && config.getY() == location.getBlockY()
                && config.getZ() == location.getBlockZ()
                && config.getWorld().equals(location.getWorld().getName()));
    }

    public void saveConfig() {
        try {
            HubPlugin.get().getConfigurationService().saveConfiguration(this,
                    new File(HubPlugin.get().getDataFolder(), "config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
