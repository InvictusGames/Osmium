package cc.invictusgames.hub.npc;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.selector.ServerSelectorEntry;
import cc.invictusgames.ilib.hologram.HologramBuilder;
import cc.invictusgames.ilib.hologram.HologramService;
import cc.invictusgames.ilib.hologram.updating.UpdatingHologram;
import cc.invictusgames.ilib.npc.NPC;
import cc.invictusgames.ilib.npc.NPCBuilder;
import cc.invictusgames.ilib.npc.NPCService;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import lombok.RequiredArgsConstructor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class NpcManager {

    private final HubPlugin plugin;
    private final Map<String, NPC> npcMap = new HashMap<>();
    private final Map<String, UpdatingHologram> hologramMap = new HashMap<>();

    public void loadNpcs() {
        for (ServerSelectorEntry entry : plugin.getHubConfig().getServerSelector()) {
            if (entry.getNpcLocation() == null)
                continue;

            spawnForEntry(entry);
        }
    }

    public void spawnForEntry(ServerSelectorEntry entry) {
        NPC npc = new NPCBuilder()
                .at(entry.getNpcLocation())
                .command("joinqueue " + entry.getServerName())
                .skinName(entry.getNpcSkin())
                .withEquipment(EquipmentSlot.HELMET, new ItemStack(Material.STAINED_GLASS,
                        1, DyeColor.RED.getWoolData()))
                .buildAndSpawn();

        npcMap.put(entry.getServerName().toLowerCase(), npc);

        UpdatingHologram hologram = new HologramBuilder()
                .at(entry.getNpcLocation().clone().add(0, 2, 0))
                .updating()
                .interval(1, TimeUnit.SECONDS)
                .provider(new NpcHologramProvider(entry))
                .build();
        hologram.start();

        hologramMap.put(entry.getServerName().toLowerCase(), hologram);
    }

    public void despawnEverythingOf(String serverName) {
        if (npcMap.containsKey(serverName.toLowerCase())) {
            NPC npc = npcMap.get(serverName.toLowerCase());
            npc.despawn();
            NPCService.unregisterNpc(npc.getUuid());
            npcMap.remove(serverName.toLowerCase());
        }

        if (hologramMap.containsKey(serverName.toLowerCase())) {
            UpdatingHologram hologram = hologramMap.get(serverName.toLowerCase());
            hologram.cancel();
            hologram.destroy();
            HologramService.unregisterHologram(hologram.getId());
            hologramMap.remove(serverName.toLowerCase());
        }
    }

    public NPC getNpc(String name) {
        return npcMap.get(name.toLowerCase());
    }

    public void despawnEverything() {
        for (NPC npc : npcMap.values()) {
            npc.despawn();
            NPCService.unregisterNpc(npc.getUuid());
        }

        for (UpdatingHologram hologram : hologramMap.values()) {
            hologram.cancel();
            hologram.destroy();
            HologramService.unregisterHologram(hologram.getId());
        }

        npcMap.clear();
        hologramMap.clear();
    }

}
