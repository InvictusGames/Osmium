package cc.invictusgames.hub.cosmetic.category.wings.impl;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.wings.PatternWings;
import cc.invictusgames.hub.cosmetic.category.wings.WingsCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class WaterWings extends PatternWings {

    private static final ParticleMeta BLUE_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            85 / 255.0F,
            85 / 255.0F,
            255 / 255.0F,
            1F,
            0
    );

    private static final ParticleMeta DARK_AQUA_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            1 / 255.0F,
            170 / 255.0F,
            170 / 255.0F,
            1F,
            0
    );

    private static final ParticleMeta AQUA_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            85 / 255.0F,
            255 / 255.0F,
            255 / 255.0F,
            1F,
            0
    );

    private static final MaterialData ICON = new MaterialData(Material.WATER_BUCKET);
    private static final String[] DESCRIPTION = new String[] {
            "&7a"
    };

    @Override
    public CosmeticCategory getCategory() {
        return WingsCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "WATER_WINGS";
    }

    @Override
    public String getName() {
        return "Water Wings";
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
    public void onEnable(Player player) {

    }

    @Override
    public void onDisable(Player player) {

    }

    @Override
    public String[][] getPattern() {
        return FireWings.PATTERN;
    }

    @Override
    public ParticleMeta getEffect(Player player, String index) {
        if (index.equals("11"))
            return BLUE_META;

        if (index.equals("22"))
            return DARK_AQUA_META;

        return AQUA_META;
    }

    @Override
    public double getHeightOffset() {
        return 0.1;
    }
}
