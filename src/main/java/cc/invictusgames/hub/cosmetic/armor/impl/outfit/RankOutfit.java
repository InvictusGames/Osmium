package cc.invictusgames.hub.cosmetic.armor.impl.outfit;

import cc.invictusgames.hub.cosmetic.armor.Outfit;
import cc.invictusgames.hub.cosmetic.armor.impl.RankArmor;
import cc.invictusgames.ilib.npc.equipment.EquipmentSlot;
import cc.invictusgames.ilib.utils.AdminBypass;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.profile.Profile;
import cc.invictusgames.invictus.rank.Rank;
import org.bukkit.entity.Player;

public class RankOutfit extends Outfit {

    private final int weight;

    public RankOutfit(Rank rank) {
        super(rank.getName(),
                CC.translate(rank.getDisplayName() + " &7Outfit"),
                new RankArmor(rank, EquipmentSlot.HELMET),
                new RankArmor(rank, EquipmentSlot.CHESTPLATE),
                new RankArmor(rank, EquipmentSlot.LEGGINGS),
                new RankArmor(rank, EquipmentSlot.BOOTS)
        );

        this.weight = rank.getWeight();
    }

    @Override
    public boolean canAccess(Player player) {
        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
        return profile.getRealCurrentGrant().getRank().getWeight() >= weight
                || AdminBypass.isBypassing(player);
    }
}
