package cc.invictusgames.hub.profile;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.Cosmetic;
import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.hub.item.DisguiseItem;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Data;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

@Data
public class Profile {

    private final UUID uuid;
    private String name;

    private boolean inParkour;
    private Location lastCheckPoint;
    private long parkourStart;

    private boolean buildMode = false;

    private final Map<String, HCFProfile> hcfProfileMap = new HashMap<>();
    private final Map<String, Boolean> cosmeticsMap = new ConcurrentHashMap<>();
    private final Map<EquipmentSlot, String> armorMap = new ConcurrentHashMap<>();

    private DisguiseItem disguiseItem;
    private long lastMoved = System.currentTimeMillis();

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Profile(Document document) {
        this.uuid = UUID.fromString(document.getString("uuid"));
        loadData(document);
    }

    public void pullHcfProfile(boolean async) {
        hcfProfileMap.clear();

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(HubPlugin.get(), () -> pullHcfProfile(false));
            return;
        }

        HubPlugin.get().getHubConfig().getHcfServers().forEach(server -> {
            Document hcfDocument = HubPlugin.get().getMongoManager().getDatabase(server).getCollection("users")
                    .find(Filters.eq("uuid", uuid.toString())).first();

            if (hcfDocument != null)
                hcfProfileMap.put(server.toLowerCase(), new HCFProfile(hcfDocument));
        });
    }

    private void loadData(Document document) {
        Document cosmetics = document.get("cosmetics", Document.class);
        if (cosmetics != null) {
            for (String key : cosmetics.keySet()) {
                Cosmetic cosmetic = HubPlugin.get().getCosmeticManager().getCosmeticIdMap().get(key);
                if (cosmetic != null)
                    cosmeticsMap.put(key, cosmetics.getBoolean(key));
            }
        }

        Document armor = document.get("armor", Document.class);
        if (armor != null) {
            for (String slot : armor.keySet()) {
                String key = armor.getString(slot);
                ArmorCosmetic cosmetic = HubPlugin.get().getCosmeticManager().getArmorCosmeticMap().get(key);
                if (cosmetic != null)
                    armorMap.put(EquipmentSlot.valueOf(slot), key);
            }
        }
    }

    public void save() {
        ForkJoinPool.commonPool().execute(() -> {
            Document document = new Document();
            document.append("uuid", this.uuid.toString());

            Document cosmetics = new Document();
            for (Map.Entry<String, Boolean> entry : cosmeticsMap.entrySet()) {
                if (!entry.getValue())
                    continue;

                cosmetics.append(entry.getKey(), true);
            }

            document.append("cosmetics", cosmetics);

            Document armor = new Document();
            for (Map.Entry<EquipmentSlot, String> entry : armorMap.entrySet()) {
                armor.append(entry.getKey().name(), entry.getValue());
            }

            document.append("armor", armor);

            HubPlugin.get().getMongoManager().getProfiles()
                    .replaceOne(Filters.eq("uuid", uuid.toString()), document,
                            new ReplaceOptions().upsert(true));
        });
    }

    public boolean isCosmeticEnabled(Cosmetic cosmetic) {
        return cosmeticsMap.getOrDefault(cosmetic.getId(), false);
    }

    public List<Cosmetic> getEnabledCosmetics() {
        List<Cosmetic> enabled = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : cosmeticsMap.entrySet()) {
            if (entry.getValue())
                enabled.add(HubPlugin.get().getCosmeticManager().getCosmeticIdMap().get(entry.getKey()));
        }

        return enabled;
    }

    public void enableCosmetic(Cosmetic cosmetic) {
        for (Cosmetic enabled : getEnabledCosmetics()) {
            if (enabled.getCategory() == cosmetic.getCategory())
                disableCosmetic(enabled);
        }

        cosmeticsMap.put(cosmetic.getId(), true);

        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
            cosmetic.onEnable(player);
    }

    public void disableCosmetic(Cosmetic cosmetic) {
        cosmeticsMap.remove(cosmetic.getId());

        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
            cosmetic.onDisable(player);
    }

    public ArmorCosmetic getEnabledArmor(EquipmentSlot slot) {
        if (!armorMap.containsKey(slot))
            return null;

        return HubPlugin.get().getCosmeticManager().getArmorCosmeticMap().get(armorMap.get(slot));
    }

    public void enableArmor(EquipmentSlot slot, ArmorCosmetic cosmetic) {
        ArmorCosmetic enabled = getEnabledArmor(slot);
        if (enabled != null)
            disableAmor(slot, enabled);

        armorMap.put(slot, cosmetic.getId());

        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
            cosmetic.onEnable(player);
    }

    public void disableAmor(EquipmentSlot slot, ArmorCosmetic cosmetic) {
        armorMap.remove(slot);

        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
            cosmetic.onDisable(player);
    }

    public HCFProfile getHCFProfile(String server) {
        return hcfProfileMap.get(server.toLowerCase());
    }

}

