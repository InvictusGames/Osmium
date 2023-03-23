package cc.invictusgames.hub.item.parkour;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.hotbaritem.HotbarItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ParkourLeaveItem extends HotbarItem {

    private final Player player;

    public ParkourLeaveItem(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.REDSTONE_TORCH_ON)
                .setDisplayName(ChatColor.RED + "Leave Parkour")
                .build();
    }

    @Override
    public void click(Action action, Block block) {
        HubPlugin.get().getParkourManager().leave(player);
    }

    @Override
    public void clickEntity(Entity entity) {

    }
}
