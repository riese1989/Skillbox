package service;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import pojo.*;
import settings.DbSettings;

import java.util.Arrays;
import java.util.Collections;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;

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
        System.out.println("Магазин добавлен");
        return true;
    }
    public boolean addProduct(String command)    {
        MongoCollection<Document> collection = getCollection("product");
        String name = command.split(" ")[1];
        int price = Integer.parseInt(command.split(" ")[2]);
        Document document = new Document("name", name)
                .append("price", price);
        collection.insertOne(document);
        System.out.println("Продукт добавлен");
        return true;
    }
    public boolean exposeProduct(String command)  {
        MongoCollection<Document> collectionShop = getCollection("shop");
        MongoCollection<Document> collectionProduct = getCollection("product");
        MongoCollection<Document> collectionProductShop = getCollection("product_shop");
        String nameShop = command.split(" ")[2];
        String nameProduct = command.split(" ")[1];
        Document searchShop = collectionShop.aggregate(Collections.singletonList(match(Filters.eq("name", nameShop)))).first();
        Document searchProduct = collectionProduct.aggregate(Collections.singletonList(match(Filters.eq("name", nameProduct)))).first();
        if (searchShop != null && searchProduct != null) {
            Document document = new Document("shop", nameShop)
                    .append("product", nameProduct)
                    .append("price", searchProduct.get("price"));
            collectionProductShop.insertOne(document);
        }
        else    {
            System.out.println("Магазин / продукт не найден");
        }
        return false;
    }
    public boolean printStat()  {
        MongoCollection<Document> collectionShop = getCollection("shop");
        MongoCollection<Document> collectionProductShop = getCollection("product_shop");
        FindIterable<Document> shops = collectionShop.find();
        for (Document shop : shops) {
            String nameShop = (String) shop.get("name");
            System.out.println("===================");
            System.out.println("Статистика для " + nameShop + "\n");
            Bson filterShop = Filters.eq("shop", nameShop);
            int count = getCount(filterShop, collectionProductShop);
            AggregateIterable<Document> aggregate = collectionProductShop
                    .aggregate(Arrays.asList(match(filterShop), Aggregates.group("_id" ,new BsonField("averagePrice", new BsonDocument("$avg", new BsonString("$price"))))));
            Document result = aggregate.first();
            double avePrice = (double) result.get("averagePrice");
            System.out.println("Общее количество товаров " + count);
            System.out.println("Средняя цена товара " + avePrice);
            System.out.println("Самый дорогой и самый дешевый товар");
            Document res = getMaxMinProduct("max", filterShop, collectionProductShop);
            System.out.println(printDocumentMaxMin("Дорогой", res));
            res = getMaxMinProduct("min", filterShop, collectionProductShop);
            System.out.println(printDocumentMaxMin("Дешевый", res));
            Bson filter = Filters.and(filterShop, Filters.lt("price", 100));
            count = getCount(filter, collectionProductShop);
            System.out.println("Количество товаров дешевле 100 рублей " + count);
        }
        return true;
    }

    private Document getMaxMinProduct (String m, Bson filterShop, MongoCollection<Document> collection) {
        String field = m + "Price";
        AggregateIterable<Document>aggregate = collection
                .aggregate(Arrays.asList(match(filterShop), Aggregates.group("_id", new BsonField(field, new BsonDocument("$max", new BsonString("$price"))))));
        Document result = aggregate.first();
        Integer maxValue = (Integer) result.get(field);
        Bson filter = Filters.and(filterShop, Filters.eq("price", maxValue));
        Document document = collection.aggregate(Arrays.asList(match(filter))).first();
        return document;
    }

    private String printDocumentMaxMin(String type, Document document) {
        String name = (String) document.get("product");
        Integer value = (Integer) document.get("price");
        return type + " " + name + " " + value;
    }

    private int getCount(Bson filter, MongoCollection<Document> collection)   {
        Document doc = collection
                .aggregate(Arrays.asList(match(filter),count()))
                .first();
        try {
            return  (int) doc.get("count");
        }
        catch (Exception ex)    {
            return  0;
        }
    }
}
