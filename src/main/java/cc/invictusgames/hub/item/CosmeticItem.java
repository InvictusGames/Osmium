package cc.invictusgames.hub.item;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.menu.CategoriesMenu;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.hotbaritem.HotbarItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class CosmeticItem extends HotbarItem {

    private final HubPlugin plugin;
    private final Player player;

    public CosmeticItem(HubPlugin plugin, Player player) {
        super(player);
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.ENDER_CHEST)
                .setDisplayName(ChatColor.AQUA + "Cosmetics")
                .build();
    }

    @Override
    public void click(Action action, Block block) {
        new CategoriesMenu(plugin).openMenu(player);
    }

    @Override
    public void clickEntity(Entity entity) {

    }
}
