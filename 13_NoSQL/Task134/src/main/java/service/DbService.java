package service;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import pojo.*;
import settings.DbSettings;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;

public class DbService {
    private final Database database = new Database(new DbSettings());

    private MongoCollection<Document> getCollection(String nameCollection) {
        MongoClient mongoClient = new MongoClient(database.getIp(), database.getPort());
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getName());
        return mongoDatabase.getCollection(nameCollection);
    }

    public boolean addShop(String command) {
        MongoCollection<Document> collection = getCollection("shop");
        String name = command.split(" ")[1];
        Document document = new Document("name", name);
        collection.insertOne(document);
        System.out.println("Магазин добавлен");
        return true;
    }

    public boolean addProduct(String command) {
        MongoCollection<Document> collection = getCollection("product");
        String name = command.split(" ")[1];
        int price = Integer.parseInt(command.split(" ")[2]);
        Document document = new Document("name", name)
                .append("price", price);
        collection.insertOne(document);
        System.out.println("Продукт добавлен");
        return true;
    }

    public boolean exposeProduct(String command) {
        MongoCollection<Document> collectionShop = getCollection("shop");
        MongoCollection<Document> collectionProduct = getCollection("product");
        String nameShop = command.split(" ")[2];
        String nameProduct = command.split(" ")[1];
        Document searchShop = collectionShop.aggregate(Collections.singletonList(match(Filters.eq("name", nameShop)))).first();
        Document searchProduct = collectionProduct.aggregate(Collections.singletonList(match(Filters.eq("name", nameProduct)))).first();
        if (searchShop != null && searchProduct != null) {
            int price = Integer.parseInt(String.valueOf(searchProduct.get("price")));
            Bson bson = BsonDocument.parse(String.format("{name: \"%s\"}", nameShop));
            collectionShop.updateOne(bson, Updates.addToSet("products", nameProduct));
            collectionShop.updateOne(bson, Updates.addToSet("prices", price));
        } else {
            System.out.println("Магазин / продукт не найден");
        }
        return false;
    }

    public boolean printStat() {
        MongoCollection<Document> collectionShop = getCollection("shop");
        MongoCollection<Document> collectionProduct = getCollection("product");
        DBObject groupFields = new BasicDBObject("_id", "$shop");
        groupFields.put("averagePrice", new BasicDBObject("$avg", "$prices"));
        BasicDBObject avgPrices = new BasicDBObject("$avg", "$prices");
        BasicDBObject fieldAvg = new BasicDBObject("avgPrices", avgPrices);
        BasicDBObject maxPrices = new BasicDBObject("$max", "$prices");
        BasicDBObject minPrices = new BasicDBObject("$min", "$prices");
        BasicDBObject countPrices = new BasicDBObject("$count", "$prices");
        BasicDBObject fields = fieldAvg.append("maxPrices", maxPrices).append("minPrices", minPrices)
                .append("name", "$name")
                .append("prices", "$prices");
        BasicDBObject projectAvg = new BasicDBObject("$project", fields);
        List pipeline = Arrays.asList(projectAvg);
        AggregateIterable<Document> output = collectionShop.aggregate(pipeline);
        for (Document result : output) {
            String nameShop = String.valueOf(result.get("name"));
            System.out.println("\n Статистика для " + nameShop + "\n");
            String avePrice = String.valueOf(result.get("avgPrices"));
            System.out.println("Средняя цена " + avePrice);
            String minPrice = String.valueOf(result.get("minPrices"));
            System.out.println("Самый дешёвый товар " + nameProduct(collectionProduct, minPrice) + " " + minPrice);
            String maxPrice = String.valueOf(result.get("maxPrices"));
            System.out.println("Самый дорогой товар " + nameProduct(collectionProduct, maxPrice) + " " + maxPrice);
            ArrayList<Integer> prices = (ArrayList<Integer>) result.get("prices");
            System.out.println("Число товаров " + prices.size());
            long countLowProducts = prices.stream().filter(p -> p < 100).count();
            System.out.println("Число товаров дешевле 100 " + countLowProducts);
        }
        return true;
    }

    private String nameProduct(MongoCollection<Document> collection, String price) {
        Bson filter = Filters.eq("price", Integer.parseInt(price));
        return (String) collection.find(filter).first().get("name");
    }
}
