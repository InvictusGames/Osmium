package cc.invictusgames.hub.cosmetic.armor;

import cc.invictusgames.ilib.utils.AdminBypass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ArmorCosmetic {

    String getId();

    ItemStack getIcon();

    default String getPermission() {
        return "cosmetics." + getId().toLowerCase();
    }

    default boolean canAccess(Player player) {
        return getPermission().isEmpty()
                || player.hasPermission(getPermission())
                || AdminBypass.isBypassing(player);
    }

    void onEnable(Player player);

    void onDisable(Player player);

    void tick(Player player);

}
