package cc.invictusgames.hub.profile;

import cc.invictusgames.hub.profile.deathban.Deathban;
import lombok.Data;
import org.bson.Document;

@Data
public class HCFProfile {

    private int lives;
    private Deathban deathban = null;

    public HCFProfile(Document document) {
        this.lives = document.getInteger("lives");

        if (document.containsKey("deathban")) {
            Document deathbanDocument = document.get("deathban", Document.class);
            if (deathbanDocument != null)
                this.deathban = new Deathban(deathbanDocument);
        }
    }
}
