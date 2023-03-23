package cc.invictusgames.hub.cosmetic.category.wings.impl;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.wings.PatternWings;
import cc.invictusgames.hub.cosmetic.category.wings.WingsCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class ArchangelWings extends PatternWings {

    private static final String[][] PATTERN = new String[][]{
            {"-1", "-1", "-1", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "-1", "-1", "-1"},
            {"-1", "-1", "11", "22", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "22", "11", "-1", "-1"},
            {"-1", "-1", "11", "22", "22", "11", "-1", "-1", "-1", "-1", "-1", "11", "22", "22", "11", "-1", "-1"},
            {"-1", "11", "22", "22", "22", "22", "11", "11", "-1", "11", "11", "22", "22", "22", "22", "11", "-1"},
            {"-1", "11", "22", "22", "22", "22", "22", "22", "11", "22", "22", "22", "22", "22", "22", "11", "-1"},
            {"-1", "11", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "11", "-1"},
            {"-1", "11", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "22", "11", "-1"},
            {"-1", "11", "22", "22", "22", "22", "22", "11", "22", "11", "22", "22", "22", "22", "22", "11", "-1"},
            {"11", "22", "22", "22", "22", "11", "11", "-1", "11", "-1", "11", "11", "22", "22", "22", "22", "11"},
            {"11", "22", "22", "22", "11", "-1", "11", "-1", "-1", "-1", "11", "-1", "11", "22", "22", "22", "11"},
            {"11", "22", "11", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "11", "22", "11"},
            {"-1", "11", "-1", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "-1", "11", "-1"},
            {"-1", "11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "11", "-1"}
    };

    private static final MaterialData ICON = new MaterialData(Material.FEATHER);
    private static final String[] DESCRIPTION = new String[]{
            "&7Equip yourself with the wings of an",
            "&7angel. The trim of these wings will ",
            "&7take the color of your rank."
    };

    @Override
    public CosmeticCategory getCategory() {
        return WingsCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "ARCHANGEL_WINGS";
    }

    @Override
    public String getName() {
        return "Archangel Wings";
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
        Color color = Color.fromRGB(255, 255, 255);

        if (index.equals("11")) {
            Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
            color = profile.getCurrentGrant().getRank().getBukkitColor();
        }

        if (color.getRed() <= 0)
            color = color.setRed(1);

        return new ParticleMeta(
                EnumParticle.REDSTONE,
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                1,
                0
        );
    }


    @Override
    public double getHeightOffset() {
        return 0;
    }
}
