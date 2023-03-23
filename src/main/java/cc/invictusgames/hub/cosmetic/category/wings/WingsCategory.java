package cc.invictusgames.hub.cosmetic.category.wings;

import cc.invictusgames.hub.cosmetic.Cosmetic;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.wings.impl.*;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.utils.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WingsCategory implements CosmeticCategory {

    public static WingsCategory INSTANCE = new WingsCategory();

    private static final ItemStack ICON = new ItemBuilder(Material.FEATHER)
            .setDisplayName(CC.PINK + CC.BOLD + "Wings")
            .build();

    private static final List<Cosmetic> COSMETICS = Arrays.asList(
            new ArchangelWings(),
            new NitroWings(),
            new FireWings(),
            new WaterWings(),
            new IceWings()
    );

    @Override
    public String getName() {
        return "Wings";
    }

    @Override
    public int getSlot() {
        return 12;
    }

    @Override
    public ItemStack getDisplayItem() {
        return ICON;
    }

    @Override
    public List<Cosmetic> getCosmetics() {
        return COSMETICS;
    }
}
