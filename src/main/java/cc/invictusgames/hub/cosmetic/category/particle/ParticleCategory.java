package cc.invictusgames.hub.cosmetic.category.particle;

import cc.invictusgames.hub.cosmetic.Cosmetic;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.particle.cloud.PotionCloudParticle;
import cc.invictusgames.hub.cosmetic.category.particle.helix.BloodHelixParticle;
import cc.invictusgames.hub.cosmetic.category.particle.ring.*;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.utils.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ParticleCategory implements CosmeticCategory {

    public static ParticleCategory INSTANCE = new ParticleCategory();

    private static final ItemStack ICON = new ItemBuilder(Material.POTION)
            .setDisplayName(CC.AQUA + CC.BOLD + "Particles")
            .build();

    private static List<Cosmetic> COSMETICS = Arrays.asList(
            new RainbowRingParticle(),
            new LavaRingParticle(),
            new WaterRingParticle(),
            new FlameRingParticle(),
            new SnowRingParticle(),
            new PotionCloudParticle(),
            new BloodHelixParticle()
    );

    @Override
    public String getName() {
        return "Particles";
    }

    @Override
    public int getSlot() {
        return 13;
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
