package cc.invictusgames.hub.cosmetic;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.hub.cosmetic.armor.Outfit;
import cc.invictusgames.hub.cosmetic.armor.impl.outfit.RainbowOutfit;
import cc.invictusgames.hub.cosmetic.armor.impl.outfit.RankOutfit;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.category.particle.ParticleCategory;
import cc.invictusgames.hub.cosmetic.category.wings.WingsCategory;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.rank.Rank;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class CosmeticManager {

    private final List<CosmeticCategory> cosmeticCategoryList = new ArrayList<>();
    private final Map<String, Cosmetic> cosmeticIdMap = new HashMap<>();
    private final List<Outfit> outfitList = new ArrayList<>();
    private final Map<String, ArmorCosmetic> armorCosmeticMap = new HashMap<>();
    private final Color[] rainbowColors = new Color[64];

    public CosmeticManager(HubPlugin plugin) {
        registerCategories(
                ParticleCategory.INSTANCE,
                WingsCategory.INSTANCE
        );

        cacheColors();

        if (!Invictus.getInstance().getRankService().isLoaded())
            return;

        List<Rank> list = new ArrayList<>(Invictus.getInstance().getRankService().getRanksSorted());
        Collections.reverse(list);
        for (Rank rank : list) {
            if (rank.getWeight() <= 0)
                continue;

            registerOutfit(new RankOutfit(rank));
        }

        registerOutfit(new RainbowOutfit());

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
                for (Cosmetic cosmetic : profile.getEnabledCosmetics()) {
                    if (cosmetic instanceof TickableCosmetic)
                        ((TickableCosmetic) cosmetic).tick(player);
                }
            }
        }, 1L, 1L);

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            RainbowOutfit.changeColorIndex();

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Profile profile = HubPlugin.get().getProfileManager().getProfile(onlinePlayer.getUniqueId());

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    ArmorCosmetic enabledArmor = profile.getEnabledArmor(equipmentSlot);
                    if (enabledArmor != null)
                        enabledArmor.tick(onlinePlayer);
                }
            }
        }, 0, 2L);

    }

    public void cacheColors() {
        double f = 6.48 / rainbowColors.length;
        for (int i = 0; i < rainbowColors.length; ++i) {
            double r = Math.sin(f * i + 0.0) * 127.0 + 128.0;
            double g = Math.sin(f * i + 2.0943951023931953) * 127.0 + 128.0;
            double b = Math.sin(f * i + 4.1887902047863905) * 127.0 + 128.0;
            rainbowColors[i] = Color.fromRGB((int) r, (int) g, (int) b);
        }
    }

    public void registerCategories(CosmeticCategory... cosmeticCategories) {
        for (CosmeticCategory category : cosmeticCategories) {
            cosmeticCategoryList.add(category);

            for (Cosmetic cosmetic : category.getCosmetics())
                cosmeticIdMap.put(cosmetic.getId(), cosmetic);
        }
    }

    public void registerOutfit(Outfit... outfits) {
        for (Outfit outfit : outfits) {
            outfitList.add(outfit);

            if (outfit.getHelmet() != null)
                armorCosmeticMap.put(outfit.getHelmet().getId(), outfit.getHelmet());

            if (outfit.getChestplate() != null)
                armorCosmeticMap.put(outfit.getChestplate().getId(), outfit.getChestplate());

            if (outfit.getLeggings() != null)
                armorCosmeticMap.put(outfit.getLeggings().getId(), outfit.getLeggings());

            if (outfit.getBoots() != null)
                armorCosmeticMap.put(outfit.getBoots().getId(), outfit.getBoots());
        }
    }

    public List<ArmorCosmetic> getArmorCosmetics(EquipmentSlot slot) {
        List<ArmorCosmetic> list = new ArrayList<>();
        for (Outfit outfit : outfitList) {
            if (slot == EquipmentSlot.HELMET && outfit.getHelmet() != null)
                list.add(outfit.getHelmet());

            if (slot == EquipmentSlot.CHESTPLATE && outfit.getChestplate() != null)
                list.add(outfit.getChestplate());

            if (slot == EquipmentSlot.LEGGINGS && outfit.getLeggings() != null)
                list.add(outfit.getLeggings());

            if (slot == EquipmentSlot.BOOTS && outfit.getBoots() != null)
                list.add(outfit.getBoots());
        }

        return list;
    }
}
