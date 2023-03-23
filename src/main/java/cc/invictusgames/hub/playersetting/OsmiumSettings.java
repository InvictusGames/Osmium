package cc.invictusgames.hub.playersetting;

import cc.invictusgames.ilib.playersetting.PlayerSetting;
import cc.invictusgames.ilib.playersetting.PlayerSettingProvider;
import cc.invictusgames.ilib.playersetting.impl.BooleanSetting;
import cc.invictusgames.ilib.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

import java.util.Collections;
import java.util.List;

public class OsmiumSettings implements PlayerSettingProvider {

    public static final BooleanSetting FLY_MODE
            = new BooleanSetting("osmium", "fly_mode") {
        @Override
        public String getDisplayName() {
            return "Fly Mode";
        }

        @Override
        public String getEnabledText() {
            return "Fly is enabled";
        }

        @Override
        public String getDisabledText() {
            return "Fly is disabled";
        }

        @Override
        public List<String> getDescription() {
            return Collections.singletonList(CC.YELLOW + "If enabled, you are able to fly around.");
        }

        @Override
        public MaterialData getMaterial() {
            return new MaterialData(Material.FEATHER);
        }

        @Override
        public Boolean getDefaultValue() {
            return true;
        }

        @Override
        public boolean canUpdate(Player player) {
            return player.hasPermission("osmium.fly");
        }

        @Override
        public void click(Player player, ClickType clickType) {
            super.click(player, clickType);
            player.setAllowFlight(get(player));
            player.setFlying(get(player));
        }
    };

    @Override
    public List<PlayerSetting> getProvidedSettings() {
        return Collections.singletonList(FLY_MODE);
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
