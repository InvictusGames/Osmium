package cc.invictusgames.hub.mongo;

import cc.invictusgames.hub.HubPlugin;
import cc.invictusgames.ilib.mongo.MongoService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoManager extends MongoService {

    public MongoManager(HubPlugin hubPlugin) {
        super(hubPlugin.getHubConfig().getMongoConfig(), "hub");
    }

    public MongoCollection<Document> getProfiles() {
        return getCollection("profiles");
    }

    public MongoDatabase getDatabase(String name) {
       return executeCommand((mongoClient, mongoDatabase)
               -> mongoClient.getDatabase(name));
    }

}
