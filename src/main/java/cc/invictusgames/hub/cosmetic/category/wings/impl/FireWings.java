package cc.invictusgames.hub.cosmetic.category.wings.impl;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.wings.PatternWings;
import cc.invictusgames.hub.cosmetic.category.wings.WingsCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class FireWings extends PatternWings {

    public static final String[][] PATTERN = {
            {"-1", "-1", "-1", "11", "-1", "-1", "-1", "-1", "-1", "11", "-1", "-1", "-1"},
            {"-1", "-1", "11", "11", "11", "-1", "-1", "-1", "11", "11", "11", "-1", "-1"},
            {"-1", "11", "11", "11", "11", "11", "-1", "11", "11", "11", "11", "11", "-1"},
            {"-1", "22", "22", "11", "11", "11", "11", "11", "11", "11", "22", "22", "-1"},
            {"33", "22", "22", "22", "11", "11", "11", "11", "11", "22", "22", "22", "33"},
            {"33", "22", "22", "22", "22", "11", "-1", "11", "22", "22", "22", "22", "33"},
            {"33", "33", "22", "22", "22", "-1", "-1", "-1", "22", "22", "22", "33", "33"},
            {"33", "33", "33", "22", "22", "-1", "-1", "-1", "22", "22", "33", "33", "33"},
            {"-1", "33", "33", "33", "22", "-1", "-1", "-1", "22", "33", "33", "33", "-1"},
            {"-1", "-1", "33", "33", "33", "-1", "-1", "-1", "33", "33", "33", "-1", "-1"},
            {"-1", "-1", "-1", "33", "-1", "-1", "-1", "-1", "-1", "33", "-1", "-1", "-1"}
    };

    private static final ParticleMeta RED_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            Color.RED.getRed() / 255.0F,
            Color.RED.getGreen() / 255.0F,
            Color.RED.getBlue() / 255.0F,
            1F,
            0
    );

    private static final ParticleMeta ORANGE_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            Color.ORANGE.getRed() / 255.0F,
            Color.ORANGE.getGreen() / 255.0F,
            Color.ORANGE.getBlue() / 255.0F,
            1F,
            0
    );

    private static final ParticleMeta YELLOW_META = new ParticleMeta(
            EnumParticle.REDSTONE,
            Color.YELLOW.getRed() / 255.0F,
            Color.YELLOW.getGreen() / 255.0F,
            Color.YELLOW.getBlue() / 255.0F,
            1F,
            0
    );

    private static final MaterialData ICON = new MaterialData(Material.FLINT_AND_STEEL);
    private static final String[] DESCRIPTION = new String[] {
            "&7a"
    };

    @Override
    public CosmeticCategory getCategory() {
        return WingsCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "FIRE_WINGS";
    }

    @Override
    public String getName() {
        return "Fire Wings";
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
        return PATTERN;
    }

    @Override
    public ParticleMeta getEffect(Player player, String index) {
        if (index.equals("11"))
            return RED_META;

        if (index.equals("22"))
            return ORANGE_META;

        return YELLOW_META;
    }

    @Override
    public double getHeightOffset() {
        return 0.1;
    }
}
