package cc.invictusgames.hub.listener;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.item.DisguiseItem;
import cc.invictusgames.hub.playersetting.OsmiumSettings;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.invictus.disguise.event.ProfileDisguiseEvent;
import cc.invictusgames.invictus.disguise.event.ProfileUnDisguiseEvent;
import cc.invictusgames.marvelspigot.handler.SimpleMovementHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener, SimpleMovementHandler {

    private final HubPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawnLocation = plugin.getHubConfig().getSpawnLocation();

        event.setJoinMessage(null);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setWalkSpeed(0.5F);

        if (spawnLocation != null)
            player.teleport(spawnLocation);

        if (OsmiumSettings.FLY_MODE.canUpdate(player) && OsmiumSettings.FLY_MODE.get(player)) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }

        plugin.giveItems(player);
        //plugin.checkOnlinePlayers(false);
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Profile profile = HubPlugin.get().getProfileManager()
                .getProfile(player.getUniqueId());

        if (profile.isBuildMode())
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        plugin.getParkourManager().leave(event.getPlayer());
        //plugin.checkOnlinePlayers(true);
    }

    @EventHandler
    public void onDisguise(ProfileDisguiseEvent event) {
        Player player = event.getPlayer();
        DisguiseItem item = plugin.getProfileManager().getProfile(player.getUniqueId()).getDisguiseItem();
        if (item != null)
            player.getInventory().setItem(7, item.getItem());
    }

    @EventHandler
    public void onUnDisguise(ProfileUnDisguiseEvent event) {
        Player player = event.getPlayer();
        DisguiseItem item = plugin.getProfileManager().getProfile(player.getUniqueId()).getDisguiseItem();
        if (item != null)
            player.getInventory().setItem(7, item.getItem());
    }

    @Override
    public boolean handleUpdateLocation(Player player, Location from, Location to) {
        if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ())
            plugin.getProfileManager().getProfile(player.getUniqueId()).setLastMoved(System.currentTimeMillis());

        return false;
    }
}
