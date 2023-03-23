package cc.invictusgames.hub.selector;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.builder.ItemBuilder;
import cc.invictusgames.ilib.menu.Button;
import cc.invictusgames.ilib.menu.Menu;
import cc.invictusgames.ilib.menu.fill.FillTemplate;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.queue.packet.QueueSendPlayerPacket;
import cc.invictusgames.invictus.server.ServerInfo;
import cc.invictusgames.invictus.server.ServerInfoProperty;
import cc.invictusgames.network.bukkit.property.GenericProperty;
import cc.invictusgames.network.protocol.server.ServerState;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 05.03.2021 / 13:35
 * Osmium / cc.invictusgames.hub.selector.selector
 */

@RequiredArgsConstructor
public class ServerSelectorMenu extends Menu {

    private final HubPlugin plugin;

    @Override
    public String getTitle(Player player) {
        return "Server Selector";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        plugin.getHubConfig().getServerSelector().forEach(entry ->
                buttons.put(entry.getSlot(), new SelectorItem(entry)));

        if (player.hasPermission("servermanager.command.argument.list")) {
            int slot = getSize() - 1;
            while (buttons.containsKey(slot))
                slot--;

            buttons.put(slot, new AllServersButton());
        }

        if (player.hasPermission("osmium.buildserver")) {
            int slot = getSize() - 1;
            while (buttons.containsKey(slot))
                slot--;

            buttons.put(slot, new BuildServerButton());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("UHC-Configurator")
                && player.hasPermission("uhc.command.config.edit")) {
            int slot = getSize() - 1;
            while (buttons.containsKey(slot))
                slot--;

            buttons.put(slot, new PostUhcGameButton());
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return plugin.getHubConfig().getSelectorSize();
    }

    @Override
    public FillTemplate getFillTemplate() {
        return FillTemplate.valueOf(plugin.getHubConfig().getSelectorFiller());
    }

    @RequiredArgsConstructor
    public static class SelectorItem extends Button {

        private final ServerSelectorEntry entry;

        @Override
        public ItemStack getItem(Player player) {
            ItemStack itemStack = entry.toItem(player).clone();

            ServerInfo server = entry.getServer();
            if (server == null || !server.isOnline())
                itemStack.setType(Material.REDSTONE_BLOCK);

            return itemStack;
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            Bukkit.dispatchCommand(player, "joinqueue " + entry.getServerName());
        }
    }

    public static class AllServersButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.COMMAND)
                    .setDisplayName(ChatColor.YELLOW + CC.BOLD + "View all servers")
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            Bukkit.dispatchCommand(player, "servers");
        }
    }

    public static class PostUhcGameButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.GOLDEN_APPLE)
                    .setDisplayName(ChatColor.YELLOW + CC.BOLD + "Setup a UHC game")
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            Bukkit.dispatchCommand(player, "setupgame");
        }
    }

    public static class BuildServerButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ServerInfo server = Invictus.getInstance().getServerService().getServer("builds");
            int players = GenericProperty.ONLINE_PLAYERS.get(server).orElse(0);

            return new ItemBuilder(Material.GRASS)
                    .setDisplayName(ChatColor.YELLOW + CC.BOLD + "Build Server")
                    .setLore(
                            CC.format("&eState: &c%s", HubPlugin.getStateName(player, server)),
                            CC.format("&ePlayers: &c%d", players)
                    ).build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            Invictus.getInstance().getRedisService().publish(new QueueSendPlayerPacket(
                    "builds",
                    player.getUniqueId()
            ));
        }
    }

}
