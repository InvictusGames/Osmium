package cc.invictusgames.hub.cosmetic.category.particle.cloud;

import cc.invictusgames.hub.cosmetic.TickableCosmetic;
import cc.invictusgames.hub.util.ParticleMeta;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public abstract class CloudParticle extends TickableCosmetic {

    public abstract ParticleMeta getEffect(Player player);

    @Override
    public void tick(Player player) {
        int ticks = getTicks().getOrDefault(player.getUniqueId(), 2);

        if (ticks++ < 2) {
            getTicks().put(player.getUniqueId(), ticks);
            return;
        }

        getTicks().put(player.getUniqueId(), 0);

        buildSmallRing(player, 0.6);
        buildSmallRing(player, 0.3);
    }

    private void buildSmallRing(Player player, double distance) {
        for (int i = 0; i < 8; i++) {
            Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
            Color color = profile.getCurrentGrant().getRank().getBukkitColor();

            double angle = i * ((2 * Math.PI) / 8F);
            new ParticleMeta(
                    EnumParticle.SPELL_INSTANT,
                    color.getRed() / 255F,
                    color.getGreen() / 255F,
                    color.getBlue() / 255F,
                    1,
                    0
            ).playAt(player, player.getLocation().clone().add(distance * Math.cos(angle), -0.1, distance * Math.sin(angle)));
        }
    }
}
