package cc.invictusgames.hub.cosmetic.category.wings;

import cc.invictusgames.hub.cosmetic.TickableCosmetic;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.hub.util.ParticleMeta;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class PatternWings extends TickableCosmetic {

    public abstract String[][] getPattern();

    public abstract ParticleMeta getEffect(Player player, String index);

    public abstract double getHeightOffset();

    @Override
    public void tick(Player player) {
        int ticks = getTicks().getOrDefault(player.getUniqueId(), 2);

        Profile profile = PLUGIN.getProfileManager().getProfile(player.getUniqueId());
        if (System.currentTimeMillis() - profile.getLastMoved() < 500L) {
            getTicks().put(player.getUniqueId(), 2);
            return;
        }

        if (ticks++ < 2) {
            getTicks().put(player.getUniqueId(), ticks);
            return;
        }

        String[][] pattern = getPattern();

        int height = pattern.length;
        int width = pattern[0].length - 1;

        Location start = player.getLocation().clone().add(0, getHeightOffset() + (height * 0.2), 0);
        start.setPitch(0.0F);
        start.add(start.getDirection().multiply(-0.4D));
        start.setYaw(start.getYaw() + 90);
        start.subtract(start.getDirection().multiply((width / 2.0D) * -0.2D));

        getTicks().put(player.getUniqueId(), 0);
        for (int y = 0; y < pattern.length; y++) {
            start.subtract(0, 0.2, 0);

            for (int x = 0; x < pattern[y].length; x++) {
                String index = pattern[y][x];
                if (index.equals("-1"))
                    continue;

                getEffect(player, index).playAt(player, start.clone().add(start.getDirection().multiply(x * -0.2)));
            }
        }
    }
}
