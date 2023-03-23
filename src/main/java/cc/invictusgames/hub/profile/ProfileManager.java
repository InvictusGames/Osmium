package cc.invictusgames.hub.profile;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.hub.profile.listener.ProfileListener;
import cc.invictusgames.ilib.utils.callback.TypeCallable;
import cc.invictusgames.invictus.Invictus;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

public class ProfileManager {

    private final HubPlugin plugin;
    private final Map<UUID, Profile> profileMap = new HashMap<>();

    public ProfileManager(HubPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new ProfileListener(plugin), plugin);
    }

    public Profile loadProfile(UUID uuid) {
        if (profileMap.containsKey(uuid))
            return profileMap.get(uuid);

        Document document = plugin.getMongoManager().getProfiles()
                .find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null)
            return createProfile(uuid);
        else return profileMap.getOrDefault(uuid, profileMap.put(uuid, new Profile(document)));
    }

    public void loadProfileAsync(UUID uuid, Consumer<Profile> consumer) {
        ForkJoinPool.commonPool().execute(() -> {
            if (profileMap.containsKey(uuid)) {
                consumer.accept(profileMap.get(uuid));
                return;
            }

            Document document = plugin.getMongoManager().getProfiles()
                    .find(Filters.eq("uuid", uuid.toString())).first();

            if (document == null)
                consumer.accept(createProfile(uuid));
            else consumer.accept(profileMap.getOrDefault(uuid, profileMap.put(uuid, new Profile(document))));
        });
    }

    public Profile getProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public Profile createProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        profileMap.put(uuid, profile);
        return profile;
    }
}
