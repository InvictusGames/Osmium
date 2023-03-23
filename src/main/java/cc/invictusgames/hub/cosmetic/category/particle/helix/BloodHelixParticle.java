package cc.invictusgames.hub.cosmetic.category.particle.helix;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.particle.ParticleCategory;
import cc.invictusgames.hub.util.ParticleMeta;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class BloodHelixParticle extends HelixParticle {

    private static final MaterialData ICON = new MaterialData(Material.REDSTONE);
    private static final String[] DESCRIPTION = new String[]{
            "&7Surround yourself with",
            "&7a double helix of blood."
    };

    private static final ParticleMeta META = new ParticleMeta(
            EnumParticle.REDSTONE,
            0F,
            0F,
            0F,
            0F,
            1
    );

    @Override
    public CosmeticCategory getCategory() {
        return ParticleCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "BLOOD_HELIX";
    }

    @Override
    public String getName() {
        return "Blood Helix";
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
        return META;
    }
}
