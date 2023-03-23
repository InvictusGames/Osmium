package cc.invictusgames.hub.cosmetic.armor.impl;

import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.hub.cosmetic.armor.impl.outfit.RainbowOutfit;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.ilib.utils.Timings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class RainbowArmor implements ArmorCosmetic {

    private EquipmentSlot equipmentSlot;
    private String displayName;

    public RainbowArmor(EquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
        this.displayName = CC.translate("&cR&6a&ei&an&2b&5o&dw");
    }

    @Override
    public String getId() {
        return "RAINBOW_" + equipmentSlot.name();
    }

    @Override
    public ItemStack getIcon() {
        Material material = Material.LEATHER_HELMET;

        if (equipmentSlot == EquipmentSlot.CHESTPLATE)
            material = Material.LEATHER_CHESTPLATE;

        if (equipmentSlot == EquipmentSlot.LEGGINGS)
            material = Material.LEATHER_LEGGINGS;

        if (equipmentSlot == EquipmentSlot.BOOTS)
            material = Material.LEATHER_BOOTS;

        return new ItemBuilder(material)
                .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(equipmentSlot.name()))
                .setArmorColor(Color.GREEN)
                .build();
    }


    @Override
    public void onEnable(Player player) {
        updateArmor(player, Color.GREEN);
    }

    @Override
    public void onDisable(Player player) {
        if (equipmentSlot == EquipmentSlot.HELMET)
            player.getInventory().setHelmet(null);
        if (equipmentSlot == EquipmentSlot.CHESTPLATE)
            player.getInventory().setChestplate(null);
        if (equipmentSlot == EquipmentSlot.LEGGINGS)
            player.getInventory().setLeggings(null);
        if (equipmentSlot == EquipmentSlot.BOOTS)
            player.getInventory().setBoots(null);
    }

    @Override
    public void tick(Player player) {
        updateArmor(player, RainbowOutfit.getCurrentColor());
    }

    public void updateArmor(Player player, Color color) {
        if (equipmentSlot == EquipmentSlot.HELMET)
            player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(equipmentSlot.name()))
                    .setArmorColor(color)
                    .build());

        if (equipmentSlot == EquipmentSlot.CHESTPLATE)
            player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(equipmentSlot.name()))
                    .setArmorColor(color)
                    .build());

        if (equipmentSlot == EquipmentSlot.LEGGINGS)
            player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(equipmentSlot.name()))
                    .setArmorColor(color)
                    .build());

        if (equipmentSlot == EquipmentSlot.BOOTS)
            player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS)
                    .setDisplayName(displayName + " " + CC.GRAY + WordUtils.capitalizeFully(equipmentSlot.name()))
                    .setArmorColor(color)
                    .build());


        player.updateInventory();
    }
}


