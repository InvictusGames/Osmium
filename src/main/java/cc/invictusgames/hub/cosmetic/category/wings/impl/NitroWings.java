package cc.invictusgames.hub.cosmetic.category.wings.impl;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.wings.PatternWings;
import cc.invictusgames.hub.cosmetic.category.wings.WingsCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class NitroWings extends PatternWings {

    private static final String[][] PATTERN = new String[][]{
            {"-1", "-1", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "-1", "-1"},
            {"-1", "11", "22", "11", "-1", "-1", "-1", "-1", "-1", "11", "22", "11", "-1"},
            {"-1", "11", "22", "22", "11", "11", "-1", "11", "11", "22", "22", "11", "-1"},
            {"11", "22", "22", "22", "22", "22", "11", "22", "22", "22", "22", "22", "11"},
            {"11", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "11"},
            {"11", "22", "11", "11", "22", "22", "22", "22", "22", "11", "11", "22", "11"},
            {"-1", "11", "-1", "-1", "11", "11", "22", "11", "11", "-1", "-1", "11", "-1"},
            {"-1", "-1", "-1", "-1", "-1", "-1", "11", "-1", "-1", "-1", "-1", "-1", "-1"}
    };

    private static final MaterialData ICON = new MaterialData(Material.INK_SACK, DyeColor.PINK.getDyeData());
    private static final String[] DESCRIPTION = new String[]{
            "&7A special pair of wings for those",
            "&7who boost our server on discord."
    };

    private static final ParticleMeta PINK_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            255 / 255.0F,
            85 / 255.0F,
            255 / 255.0F,
            1F,
            0
    );

    private static final ParticleMeta WHITE_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            255 / 255.0F,
            255 / 255.0F,
            255 / 255.0F,
            1F,
            0
    );

    @Override
    public CosmeticCategory getCategory() {
        return WingsCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "NITRO_WINGS";
    }

    @Override
    public String getName() {
        return "Nitro Wings";
    }

    @Override
    public MaterialData getIcon() {
        return ICON;
    }

    @Override
    public String[] getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getPermission() {
        return "invictus.nitroboost";
    }

    @Override
    public void onEnable(Player player) {

    }

    @Override
    public void onDisable(Player player) {

    }

    @Override
    public String[][] getPattern() {
        return PATTERN;
    }

    @Override
    public ParticleMeta getEffect(Player player, String index) {
        return index.equals("11") ? PINK_META : WHITE_META;
    }

    @Override
    public double getHeightOffset() {
        return 0.6;
    }
}
