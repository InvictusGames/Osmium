package cc.invictusgames.hub;

import cc.invictusgames.hub.command.OsmiumCommands;
import cc.invictusgames.hub.command.ParkourCommand;
import cc.invictusgames.hub.command.SpawnCommands;
import cc.invictusgames.hub.cosmetic.CosmeticManager;
import cc.invictusgames.hub.item.CompassItem;
import cc.invictusgames.hub.item.CosmeticItem;
import cc.invictusgames.hub.item.DisguiseItem;
import cc.invictusgames.hub.item.EnderpearlItem;
import cc.invictusgames.hub.listener.PlayerListener;
import cc.invictusgames.hub.listener.WorldListener;
import cc.invictusgames.hub.mongo.MongoManager;
import cc.invictusgames.hub.npc.NpcManager;
import cc.invictusgames.hub.npc.listener.NPCListener;
import cc.invictusgames.hub.parkour.ParkourManager;
import cc.invictusgames.hub.parkour.listener.ParkourListener;
import cc.invictusgames.hub.playersetting.OsmiumSettings;
import cc.invictusgames.hub.profile.ProfileManager;
import cc.invictusgames.hub.scoreboard.HubBoardAdapter;
import cc.invictusgames.hub.tab.HubTabAdapter;
import cc.invictusgames.hub.visibility.HubVisibilityAdapter;
import cc.invictusgames.ilib.command.CommandService;
import cc.invictusgames.ilib.configuration.ConfigurationService;
import cc.invictusgames.ilib.configuration.JsonConfigurationService;
import cc.invictusgames.ilib.playersetting.PlayerSettingService;
import cc.invictusgames.ilib.scoreboard.ScoreboardService;
import cc.invictusgames.ilib.tab.TabService;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.ilib.visibility.VisibilityService;
import cc.invictusgames.invictus.server.ServerInfo;
import cc.invictusgames.invictus.server.ServerInfoProperty;
import cc.invictusgames.marvelspigot.MarvelSpigot;
import cc.invictusgames.network.bukkit.property.BukkitProperty;
import cc.invictusgames.network.protocol.packet.PacketRegistry;
import cc.invictusgames.network.protocol.server.ServerState;
import cc.invictusgames.pluginannotation.annotation.BukkitPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@BukkitPlugin(name = "Osmium", version = "${git.build.version}-${git.commit.id.abbrev}", gitReplacements = true,
              depend = {"Invictus", "iLib"})
public class HubPlugin extends JavaPlugin {

    private ConfigurationService configurationService;
    private HubConfig hubConfig;
    private MongoManager mongoManager;
    private CosmeticManager cosmeticManager;
    private ProfileManager profileManager;
    private NpcManager npcManager;
    private ParkourManager parkourManager;

    @Override
    public void onEnable() {
        configurationService = new JsonConfigurationService();
        loadConfig();

        mongoManager = new MongoManager(this);
        cosmeticManager = new CosmeticManager(this);
        profileManager = new ProfileManager(this);
        npcManager = new NpcManager(this);
        parkourManager = new ParkourManager(this);

        //For some reason skins don't work when we don't spawn this delayed
        Bukkit.getScheduler().runTaskLater(this, npcManager::loadNpcs, 20L);

        if (mongoManager.connect())
            System.out.println("Connected to mongo!");

        VisibilityService.registerVisibilityAdapter(new HubVisibilityAdapter(this));
        new ScoreboardService(this, new HubBoardAdapter(this));
        new TabService(this, new HubTabAdapter(this));

        CommandService.register(this,
                new SpawnCommands(this),
                new OsmiumCommands(this),
                new ParkourCommand(this));

        PlayerListener playerListener = new PlayerListener(this);
        ParkourListener parkourListener = new ParkourListener(this);

        Stream.of(
                new WorldListener(),
                playerListener,
                parkourListener
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        MarvelSpigot.getInstance().addSimpleMovementHandler(playerListener);
        MarvelSpigot.getInstance().addSimpleMovementHandler(parkourListener);

        PlayerSettingService.registerProvider(new OsmiumSettings());
        PacketRegistry.registerListener(new NPCListener());

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(1600);
        }
    }

    public void loadConfig() {
        hubConfig = configurationService.loadConfiguration(HubConfig.class,
                new File(getDataFolder(), "config.json"));
    }

    public static String getStateName(Player player, ServerInfo server) {
        Optional<Boolean> whitelisted = BukkitProperty.WHITELIST.get(server);
        if (whitelisted.isPresent() && whitelisted.get())
            return CC.YELLOW + "Whitelisted";

        switch (ServerInfoProperty.STATE.get(server).orElse(ServerState.UNKNOWN)) {
            case UNKNOWN:
                return CC.format("&cOffline&7%s", player.isOp() ? " (UN)" : "");
            case STARTING:
                return CC.translate("&eStarting");
            case ONLINE:
                return CC.translate("&aOnline");
            case OFFLINE:
                return CC.translate("&cOffline");
        }

        return "";
    }

    public void giveItems(Player player) {
        player.getInventory().setItem(0, new EnderpearlItem(player).getItem());
        if (player.hasPermission("invictus.command.disguise"))
            player.getInventory().setItem(7, new DisguiseItem(this, player).getItem());
        player.getInventory().setItem(4, new CompassItem(this, player).getItem());
        player.getInventory().setItem(8, new CosmeticItem(this, player).getItem());
    }

    public static HubPlugin get() {
        return JavaPlugin.getPlugin(HubPlugin.class);
    }
}
