package cc.invictusgames.hub.cosmetic.category;

import cc.invictusgames.hub.cosmetic.Cosmetic;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CosmeticCategory {

    String getName();

    int getSlot();

    ItemStack getDisplayItem();

    List<Cosmetic> getCosmetics();

    default boolean isHidden()  {
        return false;
    }

}
