package cc.invictusgames.hub.cosmetic;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TickableCosmetic implements Cosmetic {

    @Getter
    private final Map<UUID, Integer> ticks = new ConcurrentHashMap<>();

    public abstract void tick(Player player);

}
