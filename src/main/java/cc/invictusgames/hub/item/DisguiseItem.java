package cc.invictusgames.hub.item;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.hotbaritem.HotbarItem;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class DisguiseItem extends HotbarItem {

    private final Profile profile;

    public DisguiseItem(HubPlugin plugin, Player player) {
        super(player);
        this.profile = Invictus.getInstance().getProfileService().getProfile(player);
        plugin.getProfileManager().getProfile(player.getUniqueId()).setDisguiseItem(this);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.NAME_TAG)
                .setDisplayName(CC.DAQUA + (!profile.isDisguised() ? "Enable" : "Disable") + " Disguise")
                .build();
    }

    @Override
    public void click(Action action, Block block) {
        if (profile.isDisguised()) {
            Bukkit.dispatchCommand(profile.player(), "undisguise");
            return;
        }

        if ((action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
                && profile.player().hasPermission("invictus.disguise.admin")) {
            Bukkit.dispatchCommand(profile.player(), "disguise @admin");
            return;
        }

        Bukkit.dispatchCommand(profile.player(), "disguise @random");
    }

    @Override
    public void clickEntity(Entity entity) {

    }
}
