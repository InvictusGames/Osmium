package cc.invictusgames.hub.cosmetic.category.particle.helix;

import cc.invictusgames.hub.cosmetic.TickableCosmetic;
import cc.invictusgames.hub.util.ParticleMeta;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class HelixParticle extends TickableCosmetic {

    public abstract ParticleMeta getEffect(Player player);

    @Override
    public void tick(Player player) {
        int ticks = getTicks().getOrDefault(player.getUniqueId(), 0);
        ticks++;

        Location start = player.getLocation().clone().add(0, 2.5, 0);

        double angle = ticks * ((2 * Math.PI) / 32);
        getEffect(player).playAt(player, start.clone().subtract(
                0.6 * (0.03 * ticks) * Math.cos(angle),
                (0.1 * ticks) / 2,
                0.6 * (0.03 * ticks) * Math.sin(angle)
        ));

        getEffect(player).playAt(player, start.clone().subtract(
                0.6 * (0.03 * ticks) * Math.sin(angle),
                (0.1 * ticks) / 2,
                0.6 * (0.03 * ticks) *Math.cos(angle)
        ));

        if (ticks >= 51)
            ticks = 0;

        getTicks().put(player.getUniqueId(), ticks);
    }
}
