package cc.invictusgames.hub.cosmetic.armor.impl;

import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.AdminBypass;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import cc.invictusgames.invictus.rank.Rank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class RankArmor implements ArmorCosmetic {

    private final String id;
    private final String displayName;
    private final int weight;
    private final Color color;
    private final EquipmentSlot slot;

    public RankArmor(Rank rank, EquipmentSlot slot) {
        this.id = rank.getName().toUpperCase() + "_" + slot.name();
        this.displayName = rank.getDisplayName();
        this.weight = rank.getWeight();
        this.color = rank.getBukkitColor();
        this.slot = slot;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getIcon() {
        Material material = Material.LEATHER_HELMET;

        if (slot == EquipmentSlot.CHESTPLATE)
            material = Material.LEATHER_CHESTPLATE;

        if (slot == EquipmentSlot.LEGGINGS)
            material = Material.LEATHER_LEGGINGS;

        if (slot == EquipmentSlot.BOOTS)
            material = Material.LEATHER_BOOTS;

        return new ItemBuilder(material)
                .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(slot.name()))
                .setArmorColor(color)
                .build();
    }

    @Override
    public boolean canAccess(Player player) {
        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
        return profile.getRealCurrentGrant().getRank().getWeight() >= weight
                || AdminBypass.isBypassing(player);
    }

    @Override
    public void onEnable(Player player) {
        if (slot == EquipmentSlot.HELMET)
            player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(slot.name()))
                    .setArmorColor(color)
                    .build());

        if (slot == EquipmentSlot.CHESTPLATE)
            player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(slot.name()))
                    .setArmorColor(color)
                    .build());

        if (slot == EquipmentSlot.LEGGINGS)
            player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(slot.name()))
                    .setArmorColor(color)
                    .build());

        if (slot == EquipmentSlot.BOOTS)
            player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(slot.name()))
                    .setArmorColor(color)
                    .build());
    }

    @Override
    public void onDisable(Player player) {
        if (slot == EquipmentSlot.HELMET)
            player.getInventory().setHelmet(null);
        if (slot == EquipmentSlot.CHESTPLATE)
            player.getInventory().setChestplate(null);
        if (slot == EquipmentSlot.LEGGINGS)
            player.getInventory().setLeggings(null);
        if (slot == EquipmentSlot.BOOTS)
            player.getInventory().setBoots(null);
    }

    @Override
    public void tick(Player player) {

    }
}
