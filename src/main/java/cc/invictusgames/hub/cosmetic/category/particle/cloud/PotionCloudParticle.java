package cc.invictusgames.hub.cosmetic.category.particle.cloud;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.particle.ParticleCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class PotionCloudParticle extends CloudParticle {

    private static final MaterialData ICON = new MaterialData(Material.POTION);
    private static final String[] DESCRIPTION = new String[]{
            "&7Walk on a cloud of potion particles.",
            "&7The cloud will take the color of your rank."
    };

    @Override
    public CosmeticCategory getCategory() {
        return ParticleCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "POTION_CLOUD";
    }

    @Override
    public String getName() {
        return "Potion Cloud";
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
    public ParticleMeta getEffect(Player player) {
        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
        Color color = profile.getCurrentGrant().getRank().getBukkitColor();
        return new ParticleMeta(
                EnumParticle.SPELL_INSTANT,
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                1,
                0
        );
    }
}
