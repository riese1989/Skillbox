package service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import pojo.*;
import settings.DbSettings;

public class DbService {
    private final Database database = new Database(new DbSettings());

    private MongoCollection<Document> getCollection(String nameCollection)   {
        MongoClient mongoClient = new MongoClient(database.getIp(), database.getPort());
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getName());
        return mongoDatabase.getCollection(nameCollection);
    }
    public boolean addShop(String command)    {
        MongoCollection<Document> collection = getCollection("shop");
        String name = command.split(" ")[1];
        Document document = new Document("name", name);
        collection.insertOne(document);
        return true;
    }
    public boolean addProduct(String command)    {
        MongoCollection<Document> collection = getCollection("product");
        String name = command.split(" ")[1];
        int price = Integer.parseInt(command.split(" ")[2]);
        Document document = new Document("name", name)
                .append("price", price);
        return false;
    }
    public boolean exposeProduct(String command)  {
        MongoCollection<Document> collectionShop = getCollection("shop");
        MongoCollection<Document> collectionProduct = getCollection("product");
        return false;
    }
    public boolean printStat()  {
        return true;
    }
}
