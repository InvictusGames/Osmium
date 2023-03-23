package cc.invictusgames.hub.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticleMeta {

    private EnumParticle effect;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float speed;
    private int amount;

    public void playAt(Player player, Location location) {
        PacketPlayOutWorldParticles packetPlayOutWorldParticles =
                new PacketPlayOutWorldParticles(effect, true,
                        (float) location.getX(),
                        (float) location.getY(),
                        (float) location.getZ(),
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed,
                        amount);

        EntityTrackerEntry tracker = ((CraftWorld) player.getWorld()).getHandle()
                .getTracker().trackedEntities.get(player.getEntityId());
        tracker.broadcastIncludingSelf(packetPlayOutWorldParticles);
    }

    public void playAt(Location location) {
        PacketPlayOutWorldParticles packetPlayOutWorldParticles =
                new PacketPlayOutWorldParticles(effect, true,
                        (float) location.getX(),
                        (float) location.getY(),
                        (float) location.getZ(),
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed,
                        amount);

        for (Player player : location.getWorld().getPlayers()) {
            if (location.distanceSquared(player.getLocation()) <= 100)
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldParticles);
        }
    }

}
