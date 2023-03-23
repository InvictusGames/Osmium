package cc.invictusgames.hub.cosmetic.menu.wardrobe;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.armor.Outfit;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.Button;
import cc.invictusgames.ilib.menu.Menu;
import cc.invictusgames.ilib.menu.fill.FillTemplate;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.CC;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class OutfitsMenu extends Menu {

    private final HubPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return "Outfits";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 1;
        int row = 1;
        for (Outfit outfit : plugin.getCosmeticManager().getOutfitList()) {
            buttons.put(getSlot(row, slot++), new OutfitButton(outfit));

            if (slot > 7) {
                slot = 1;
                row++;
            }
        }

        return buttons;
    }

    @Override
    public FillTemplate getFillTemplate() {
        return FillTemplate.BORDER;
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
        int slot = 1;
        int row = 1;

        for (Outfit outfit : plugin.getCosmeticManager().getOutfitList()) {
            slot++;

            if (slot > 7) {
                slot = 1;
                row++;
            }
        }

        return (int) (Math.ceil((double) (getSlot(row, slot) + 1) / 9.0D) * 9.0D) + 9;
    }

    @RequiredArgsConstructor
    public class OutfitButton extends Button {

        private final Outfit outfit;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder builder = new ItemBuilder(outfit.getIcon().clone());
            builder.setDisplayName(outfit.getDisplayName());

            if (!outfit.canAccess(player))
                builder.addToLore(CC.RED + CC.BOLD + "You don't own this outfit.");
            else builder.addToLore(CC.GREEN + "Click to equip this outfit.");

            return builder.build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!outfit.canAccess(player)) {
                player.sendMessage(CC.RED + "You don't own this outfit.");
                return;
            }

            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            if (outfit.getHelmet() != null)
                profile.enableArmor(EquipmentSlot.HELMET, outfit.getHelmet());

            if (outfit.getChestplate() != null)
                profile.enableArmor(EquipmentSlot.CHESTPLATE, outfit.getChestplate());

            if (outfit.getLeggings() != null)
                profile.enableArmor(EquipmentSlot.LEGGINGS, outfit.getLeggings());

            if (outfit.getBoots() != null)
                profile.enableArmor(EquipmentSlot.BOOTS, outfit.getBoots());

            new WardrobeMenu(plugin).openMenu(player);
        }
    }
}
