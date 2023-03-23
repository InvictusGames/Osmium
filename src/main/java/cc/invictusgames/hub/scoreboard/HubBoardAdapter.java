package cc.invictusgames.hub.scoreboard;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.reboot.RebootService;
import cc.invictusgames.ilib.scoreboard.ScoreboardAdapter;
import cc.invictusgames.ilib.stringanimation.StringAnimation;
import cc.invictusgames.ilib.stringanimation.impl.BlinkAnimation;
import cc.invictusgames.ilib.stringanimation.impl.FadeAnimation;
import cc.invictusgames.ilib.stringanimation.impl.StaticAnimation;
import cc.invictusgames.ilib.utils.CC;
import cc.invictusgames.invictus.Invictus;
import cc.invictusgames.invictus.InvictusBukkit;
import cc.invictusgames.invictus.profile.Profile;
import cc.invictusgames.invictus.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class HubBoardAdapter implements ScoreboardAdapter {

    private static final AtomicReference<String> SCOREBOARD_TITLE = new AtomicReference<>("");

    static {
        StringAnimation animation = new StringAnimation();

        animation.add(new StaticAnimation(
                CC.GOLD + CC.BOLD + HubPlugin.get().getHubConfig().getScoreboardTitle(),
                10
        ));

        animation.add(new FadeAnimation(
                HubPlugin.get().getHubConfig().getScoreboardTitle(),
                CC.GOLD + CC.BOLD,
                CC.YELLOW + CC.BOLD,
                false
        ));

        animation.add(new BlinkAnimation(
                HubPlugin.get().getHubConfig().getScoreboardTitle(),
                CC.GOLD + CC.BOLD,
                CC.YELLOW + CC.BOLD,
                3,
                2
        ));

        animation.add(new StaticAnimation(
                CC.YELLOW + CC.BOLD + HubPlugin.get().getHubConfig().getScoreboardTitle(),
                10
        ));

        animation.add(new FadeAnimation(
                HubPlugin.get().getHubConfig().getScoreboardTitle(),
                CC.GOLD + CC.BOLD,
                CC.YELLOW + CC.BOLD,
                true
        ));

        animation.add(new BlinkAnimation(
                HubPlugin.get().getHubConfig().getScoreboardTitle(),
                CC.YELLOW + CC.BOLD,
                CC.GOLD + CC.BOLD,
                3,
                2
        ));

        animation.whenTicked(SCOREBOARD_TITLE::set);
        animation.start(4L);
    }

    private final HubPlugin plugin;

    public String getTitle(Player player) {
        return SCOREBOARD_TITLE.get();
    }

    public List<String> getLines(Player player) {
        Profile profile = Invictus.getInstance().getProfileService().getProfile(player);
        QueueService queueService = InvictusBukkit.getBukkitInstance().getQueueService();
        String primaryQueue = queueService.getPrimaryQueue(player.getUniqueId());

        List<String> lines = new ArrayList<>();

        boolean doneQueue = false;

        for (String s : plugin.getHubConfig().getScoreBoardLines()) {
            s = s.replaceAll("%rank%",
                    profile.getCurrentGrant().getRank().getDisplayName() + (profile.isDisguised()
                            ? " &7(" + profile.getRealCurrentGrant().getRank().getDisplayName() + "&7)" : ""))
                    .replaceAll("%onlinecount%",
                            String.valueOf(Invictus.getInstance().getServerService().getGlobalOnlineCount()))
                    .replaceAll("%server_address%", plugin.getHubConfig().getServerAddress());

            if (s.equals("%queue%")) {
                doneQueue = true;
                for (String queueLines : plugin.getHubConfig().getScoreBoardQueueLines()) {

                    if (primaryQueue == null)
                        continue;

                    queueLines = queueLines.replaceAll("%queue_position%",
                            String.valueOf(queueService.getPosition(player.getUniqueId(), primaryQueue) + 1))
                            .replaceAll("%queue_total%",
                                    String.valueOf(queueService.getQueueing(primaryQueue).size()))
                            .replaceAll("%queue_name%", primaryQueue);

                    lines.add(queueLines);
                }
                continue;
            }

            /*
             * This is not really the most optimal way to do it, but I cannot be asked to go through all the configs
             * on the hubs to add something for the reboot timer...
             */
            if (doneQueue && RebootService.isRebooting() && RebootService.getScoreboardString() != null) {
                lines.add(" ");
                lines.add(RebootService.getScoreboardString());
                doneQueue = false;
            }

            lines.add(s);
        }

        return lines;
    }

    public boolean showHealth(Player player) {
        return false;
    }
}
