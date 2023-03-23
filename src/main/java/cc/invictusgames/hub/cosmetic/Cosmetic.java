package cc.invictusgames.hub.cosmetic;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.ilib.utils.AdminBypass;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public interface Cosmetic {

    HubPlugin PLUGIN = HubPlugin.getPlugin(HubPlugin.class);

    CosmeticCategory getCategory();

    String getId();

    String getName();

    MaterialData getIcon();

    String[] getDescription();

    default String getPermission() {
        return "cosmetics." + getId().toLowerCase();
    }

    default boolean isHidden() {
        return false;
    }

    default boolean canAccess(Player player) {
        return getPermission().isEmpty()
                || player.hasPermission(getPermission())
                || AdminBypass.isBypassing(player);
    }

    void onEnable(Player player);

    void onDisable(Player player);
}
