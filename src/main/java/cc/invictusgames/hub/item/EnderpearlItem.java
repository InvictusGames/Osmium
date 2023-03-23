package cc.invictusgames.hub.item;

import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.hotbaritem.HotbarItem;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class EnderpearlItem extends HotbarItem {

    private final Player player;

    public EnderpearlItem(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean hasCoolDown() {
        return false;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.ENDER_PEARL)
                .setDisplayName(ChatColor.DARK_PURPLE + "Enderpearl")
                .build();
    }

    @Override
    public void click(Action action, Block block) {
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.getGameMode() == GameMode.CREATIVE)
                return;

            player.setVelocity(player.getLocation().getDirection().normalize().multiply(4));

            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 10F);
            player.updateInventory();
        }
    }

    @Override
    public void clickEntity(Entity entity) {
    }
}
