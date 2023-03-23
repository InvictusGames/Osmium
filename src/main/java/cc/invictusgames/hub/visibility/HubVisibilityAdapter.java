package cc.invictusgames.hub.visibility;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.visibility.VisibilityAction;
import cc.invictusgames.ilib.visibility.VisibilityAdapter;
import org.bukkit.entity.Player;

public class HubVisibilityAdapter extends VisibilityAdapter {

    private final HubPlugin plugin;

    public HubVisibilityAdapter(HubPlugin plugin) {
        super("Hub Visibility Adapter", 5);
        this.plugin = plugin;
    }

    @Override
    public VisibilityAction canSee(Player player, Player player1) {
        return (plugin.getHubConfig().isHidePlayers() ? VisibilityAction.HIDE : VisibilityAction.NEUTRAL);
    }
}
