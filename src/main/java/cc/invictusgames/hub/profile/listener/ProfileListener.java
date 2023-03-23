package cc.invictusgames.hub.profile.listener;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.Cosmetic;
import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ProfileListener implements Listener {
    private final HubPlugin plugin;

    @EventHandler
    public void onRegister(AsyncPlayerPreLoginEvent event) {
        plugin.getProfileManager().loadProfile(event.getUniqueId());
    }

    @EventHandler
    public void onSave(PlayerQuitEvent event) {
        Profile profile = plugin.getProfileManager()
                .getProfile(event.getPlayer().getUniqueId());

        profile.save();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        for (Cosmetic cosmetic : profile.getEnabledCosmetics()) {
            if (!cosmetic.canAccess(player))
                profile.disableCosmetic(cosmetic);
            else cosmetic.onEnable(player);
        }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ArmorCosmetic armor = profile.getEnabledArmor(slot);
            if (armor != null) {
                if (!armor.canAccess(player))
                    profile.disableAmor(slot, armor);
                else armor.onEnable(player);
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin,
                () -> profile.pullHcfProfile(true), 5L);
    }
}
