package cc.invictusgames.hub.cosmetic.menu.wardrobe;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.armor.ArmorCosmetic;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.Button;
import cc.invictusgames.ilib.menu.Menu;
import cc.invictusgames.ilib.menu.fill.FillTemplate;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.CC;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class WardrobeMenu extends Menu {

    private final HubPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return "Wardrobe";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(10, new CategoryButton(EquipmentSlot.HELMET));
        buttons.put(11, new CategoryButton(EquipmentSlot.CHESTPLATE));
        buttons.put(12, new CategoryButton(EquipmentSlot.LEGGINGS));
        buttons.put(13, new CategoryButton(EquipmentSlot.BOOTS));

        buttons.put(15, new OutfitsButton());
        buttons.put(16, new ClearButton());

        return buttons;
    }

    @Override
    public FillTemplate getFillTemplate() {
        return FillTemplate.FILL;
    }

    @Override
    public boolean isAutoUpdate() {
        return false;
    }

    @Override
    public boolean isClickUpdate() {
        return true;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @RequiredArgsConstructor
    public class CategoryButton extends Button {

        private final EquipmentSlot equipmentSlot;

        @Override
        public ItemStack getItem(Player player) {
            Profile profile = HubPlugin.get().getProfileManager().getProfile(player.getUniqueId());
            ArmorCosmetic armor = profile.getEnabledArmor(equipmentSlot);
            return armor == null
                    ? new ItemBuilder(Material.STAINED_GLASS_PANE, DyeColor.RED.getWoolData())
                    .setDisplayName(CC.RED + "No " + WordUtils.capitalizeFully(equipmentSlot.name()) + " active.")
                    .build()
                    : armor.getIcon();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            new WardrobeSelectMenu(plugin, equipmentSlot).openMenu(player);
        }
    }

    public class OutfitsButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.CHEST)
                    .setDisplayName(CC.AQUA + "Outfits")
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            new OutfitsMenu(plugin).openMenu(player);
        }
    }

    public class ClearButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.CAULDRON_ITEM)
                    .setDisplayName(CC.RED + "Clear outfit")
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ArmorCosmetic armor = profile.getEnabledArmor(equipmentSlot);
                if (armor != null)
                    profile.disableAmor(equipmentSlot, armor);
            }
        }
    }
}
