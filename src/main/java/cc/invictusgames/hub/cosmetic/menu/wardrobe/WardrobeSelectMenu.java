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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class WardrobeSelectMenu extends Menu {

    private final HubPlugin plugin;
    private final EquipmentSlot equipmentSlot;

    @Override
    public String getTitle(Player player) {
        return WordUtils.capitalizeFully(equipmentSlot.name());
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 1;
        int row = 1;
        for (ArmorCosmetic cosmetic : plugin.getCosmeticManager().getArmorCosmetics(equipmentSlot)) {
            buttons.put(getSlot(row, slot++), new CosmeticButton(cosmetic));

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

        for (ArmorCosmetic cosmetic : plugin.getCosmeticManager().getArmorCosmetics(equipmentSlot)) {
            slot++;
            if (slot > 7) {
                slot = 1;
                row++;
            }
        }

        return (int) (Math.ceil((double) (getSlot(row, slot) + 1) / 9.0D) * 9.0D) + 9;
    }

    @RequiredArgsConstructor
    public class CosmeticButton extends Button {

        private final ArmorCosmetic cosmetic;

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder builder = new ItemBuilder(cosmetic.getIcon().clone());
            String word = equipmentSlot == EquipmentSlot.CHESTPLATE || equipmentSlot == EquipmentSlot.HELMET
                    ? "this " : "these ";

            if (!cosmetic.canAccess(player))
                builder.addToLore(CC.RED + CC.BOLD + "You don't own " + word
                        + WordUtils.capitalizeFully(equipmentSlot.name()) + ".");
            else builder.addToLore(CC.GREEN + "Click to equip " + word
                    + WordUtils.capitalizeFully(equipmentSlot.name()) + ".");

            if (player.isOp())
                builder.addToLore(
                        " ",
                        CC.DGRAY + "Permission: " + cosmetic.getPermission()
                );

            return builder.build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!cosmetic.canAccess(player)) {
                player.sendMessage(CC.RED + "You don't own this "
                        + WordUtils.capitalizeFully(equipmentSlot.name()) + ".");
                return;
            }

            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            profile.enableArmor(equipmentSlot, cosmetic);
            new WardrobeMenu(plugin).openMenu(player);
        }
    }
}
