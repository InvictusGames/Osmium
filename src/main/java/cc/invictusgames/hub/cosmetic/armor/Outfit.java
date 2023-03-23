package cc.invictusgames.hub.cosmetic.armor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public class Outfit {

    private final String name;
    private final String displayName;
    private final ArmorCosmetic helmet;
    private final ArmorCosmetic chestplate;
    private final ArmorCosmetic leggings;
    private final ArmorCosmetic boots;

    public boolean canAccess(Player player) {
        return player.hasPermission("outfit." + name.toLowerCase());
    }

    public ItemStack getIcon() {
        if (chestplate != null)
            return chestplate.getIcon();

        if (helmet != null)
            return helmet.getIcon();

        if (boots != null)
            return boots.getIcon();

        return leggings.getIcon();
    }


}
