package cc.invictusgames.hub.cosmetic.menu;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.Cosmetic;
import cc.invictusgames.hub.cosmetic.category.CosmeticCategory;
import cc.invictusgames.hub.profile.Profile;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.Button;
import cc.invictusgames.ilib.menu.Menu;
import cc.invictusgames.ilib.menu.fill.FillTemplate;
import cc.invictusgames.ilib.utils.AdminBypass;
import cc.invictusgames.ilib.utils.CC;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CosmeticsMenu extends Menu {

    private final HubPlugin plugin;
    private final CosmeticCategory category;

    @Override
    public String getTitle(Player player) {
        return category.getName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 1;
        int row = 1;
        for (Cosmetic cosmetic : category.getCosmetics()) {
            if (cosmetic.isHidden() && !AdminBypass.isBypassing(player))
                continue;

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

        for (Cosmetic cosmetic : category.getCosmetics()) {
            if (cosmetic.isHidden())
                continue;

            if (slot++ > 7) {
                slot = 1;
                row++;
            }
        }

        return (int) (Math.ceil((double) (getSlot(row, slot) + 1) / 9.0D) * 9.0D) + 9;
    }

    @RequiredArgsConstructor
    public class CosmeticButton extends Button {

        private final Cosmetic cosmetic;

        @Override
        public ItemStack getItem(Player player) {
            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            ItemBuilder builder = new ItemBuilder(cosmetic.getIcon().getItemType(), cosmetic.getIcon().getData());

            if (!cosmetic.canAccess(player))
                builder.setDisplayName(CC.RED + CC.BOLD + cosmetic.getName());
            else builder.setDisplayName((profile.isCosmeticEnabled(cosmetic) ? CC.GREEN : CC.RED)
                    + CC.BOLD + cosmetic.getName());

            for (String s : cosmetic.getDescription()) {
                builder.addToLore(CC.translate(s));
            }

            if (cosmetic.getDescription().length > 0)
                builder.addToLore(" ");

            if (!cosmetic.canAccess(player))
                builder.addToLore(CC.RED + CC.BOLD + "You don't own this cosmetic.");
            else builder.addToLore(CC.format("&eClick to %s &ethis cosmetic.",
                    profile.isCosmeticEnabled(cosmetic) ? CC.RED + "disable" : CC.GREEN + "enable"));

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
                player.sendMessage(CC.RED + "You don't own this cosmetic.");
                return;
            }

            Profile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
            if (profile.isCosmeticEnabled(cosmetic))
                profile.disableCosmetic(cosmetic);
            else profile.enableCosmetic(cosmetic);
        }
    }

}
