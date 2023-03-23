package cc.invictusgames.hub.cosmetic.menu;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.cosmetic.menu.wardrobe.WardrobeMenu;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.Button;
import cc.invictusgames.ilib.menu.Menu;
import cc.invictusgames.ilib.menu.fill.FillTemplate;
import cc.invictusgames.ilib.utils.CC;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CategoriesMenu extends Menu {

    private final HubPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return "Cosmetics";
    }

    @Override
    public FillTemplate getFillTemplate() {
        return FillTemplate.BORDER;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        for (CosmeticCategory category : plugin.getCosmeticManager().getCosmeticCategoryList()) {
            buttons.put(category.getSlot(), new CategoryButton(category));
        }

        buttons.put(14, new WardrobeButton());
        return buttons;
    }

    @RequiredArgsConstructor
    public class CategoryButton extends Button {

        private final CosmeticCategory category;

        @Override
        public ItemStack getItem(Player player) {
            return category.getDisplayItem();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            new CosmeticsMenu(plugin, category).openMenu(player);
        }
    }

    public class WardrobeButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setDisplayName(CC.YELLOW + CC.BOLD + "Wardrobe")
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            new WardrobeMenu(plugin).openMenu(player);
        }
    }
}
