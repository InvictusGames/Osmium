package cc.invictusgames.hub.profile.deathban;

import cc.invictusgames.ilib.utils.TimeUtils;
import lombok.Data;
import org.bson.Document;

@Data
public class Deathban {

    private final String reason;
    private final long creationMillis;
    private final long expiryMillis;

    public Deathban(Document document) {
        reason = document.getString("reason");
        creationMillis = document.getLong("creationMillis");
        expiryMillis = document.getLong("expiryMillis");
    }

    public long getInitialDuration() {
        return expiryMillis - creationMillis;
    }

    public String getRemaining() {
        long time = expiryMillis - System.currentTimeMillis();
        return time > 0L ? TimeUtils.formatTimeShort(time) : "Not Deathbanned";
    }
}
