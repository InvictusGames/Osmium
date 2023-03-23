package cc.invictusgames.hub.item;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.selector.ServerSelectorMenu;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.hotbaritem.HotbarItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class CompassItem extends HotbarItem {

    private final HubPlugin plugin;
    private final Player player;

    public CompassItem(HubPlugin plugin, Player player) {
        super(player);
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName(ChatColor.GOLD + "Servers")
                .build();
    }

    @Override
    public void click(Action action, Block block) {
        new ServerSelectorMenu(plugin).openMenu(player);
    }

    @Override
    public void clickEntity(Entity entity) { }
}
