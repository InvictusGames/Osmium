package cc.invictusgames.hub.npc.listener;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.npc.NPC;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.network.protocol.ChannelConnectionHandler;
import cc.invictusgames.network.protocol.packet.impl.server.info.RemoveServerInfoPacket;
import cc.invictusgames.network.protocol.packet.impl.server.info.ServerInfoPacket;
import cc.invictusgames.network.protocol.packet.listener.PacketHandler;
import cc.invictusgames.network.protocol.packet.listener.PacketListener;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CopyOnWriteArrayList;

public class NPCListener implements PacketListener {

    private final CopyOnWriteArrayList<String> onlineServers = new CopyOnWriteArrayList<>();

    @PacketHandler
    public void onServerInfoUpdate(ChannelConnectionHandler connection,
                                   ServerInfoPacket packet) {

        if (onlineServers.contains(packet.getName().toLowerCase()))
            return;

        NPC npc = HubPlugin.get().getNpcManager().getNpc(packet.getName());
        if (npc != null) {
            npc.setEquipment(EquipmentSlot.HELMET, new ItemStack(Material.STAINED_GLASS,
                    1, DyeColor.LIME.getWoolData()));
            onlineServers.add(packet.getName().toLowerCase());
        }
    }

    @PacketHandler
    public void onServerInfoRemove(ChannelConnectionHandler connection,
                                   RemoveServerInfoPacket packet) {

        onlineServers.remove(packet.getName().toLowerCase());

        NPC npc = HubPlugin.get().getNpcManager().getNpc(packet.getName());
        if (npc != null)
            npc.setEquipment(EquipmentSlot.HELMET, new ItemStack(Material.STAINED_GLASS,
                    1, DyeColor.RED.getWoolData()));
    }
}
