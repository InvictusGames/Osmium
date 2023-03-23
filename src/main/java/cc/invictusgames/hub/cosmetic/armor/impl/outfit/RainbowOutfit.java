package cc.invictusgames.hub.cosmetic.armor.impl.outfit;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.cosmetic.armor.Outfit;
import cc.invictusgames.hub.cosmetic.armor.impl.RainbowArmor;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.CC;
import lombok.Getter;
import org.bukkit.Color;

@Getter
public class RainbowOutfit extends Outfit {

    private static int colorIndex = -1;

    public RainbowOutfit() {
        super("Rainbow Outfit",
                CC.translate("&cR&6a&ei&2n&2b&5o&dw &7Outfit"),
                new RainbowArmor(EquipmentSlot.HELMET),
                new RainbowArmor(EquipmentSlot.CHESTPLATE),
                new RainbowArmor(EquipmentSlot.LEGGINGS),
                new RainbowArmor(EquipmentSlot.BOOTS)
        );
    }

    public static Color getCurrentColor() {
        return HubPlugin.get().getCosmeticManager().getRainbowColors()[colorIndex];
    }

    public static void changeColorIndex() {
        if (colorIndex >= HubPlugin.get().getCosmeticManager().getRainbowColors().length - 1)
            colorIndex = -1;

        colorIndex++;
    }


}
