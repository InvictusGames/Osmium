package cc.invictusgames.hub.util;

import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

public class ParticleUtil {

    public static void sendToAll(Player player,
                                 Location location,
                                 EnumParticle effect,
                                 float offsetX,
                                 float offsetY,
                                 float offsetZ,
                                 float speed,
                                 int amount)  {
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

}
