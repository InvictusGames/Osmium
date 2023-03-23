package cc.invictusgames.hub.cosmetic.category.particle.ring;

import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.particle.ParticleCategory;
import cc.invictusgames.hub.cosmetic.category.particle.ring.RingParticle;
import cc.invictusgames.hub.util.ParticleMeta;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class WaterRingParticle extends RingParticle {

    private static final MaterialData ICON = new MaterialData(Material.WATER_BUCKET);
    private static final String[] DESCRIPTION = new String[]{
            "&7Surround yourself with",
            "&7rings made of water."
    };

    private static final ParticleMeta META = new ParticleMeta(
            EnumParticle.WATER_DROP,
            0F,
            0F,
            0F,
            0F,
            2
    );

    @Override
    public CosmeticCategory getCategory() {
        return ParticleCategory.INSTANCE;
    }

    @Override
    public String getId() {
        return "WATER_RING";
    }

    @Override
    public String getName() {
        return "Water Ring";
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
