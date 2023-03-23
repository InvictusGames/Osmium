package cc.invictusgames.hub.cosmetic.category.particle.ring;

import cc.invictusgames.hub.cosmetic.TickableCosmetic;
import cc.invictusgames.hub.util.ParticleMeta;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class RingParticle extends TickableCosmetic {

    public abstract ParticleMeta getEffect(Player player);

    @Override
    public void tick(Player player) {
        int ticks = getTicks().getOrDefault(player.getUniqueId(), 0);
        ticks++;

        buildSmallRing(player, ticks, 0.5);
        buildBigRing(player, ticks, 0.9);
        buildSmallRing(player, ticks, 1.3);

        if (ticks >= 32)
            ticks = 0;

        getTicks().put(player.getUniqueId(), ticks);
    }

    private void buildSmallRing(Player player, int ticks, double y) {
        double angle = ticks * ((2 * Math.PI) / 32);
        Location location = player.getLocation().clone().add(0.6 * Math.cos(angle), y, 0.6 * Math.sin(angle));
        getEffect(player).playAt(player, location);
    }

    private void buildBigRing(Player player, int ticks, double y) {
        double angle = ticks * ((2 * Math.PI) / 32);
        Location location = player.getLocation().clone().add(0.8 * Math.cos(angle), y, 0.8 * Math.sin(angle));
        getEffect(player).playAt(player, location);
    }
}
