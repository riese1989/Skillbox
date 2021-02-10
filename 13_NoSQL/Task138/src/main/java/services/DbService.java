package services;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import objects.Database;
import objects.Student;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Sorts.*;

public class DbService {
    private final Database database = new Database();

    private MongoCollection<Document> getCollection() {
        MongoClient mongoClient = new MongoClient(database.getIp(), database.getPort());
        ;
        MongoDatabase mongoDatabase = mongoClient.getDatabase("students");
        return mongoDatabase.getCollection("studentsCollection");
    }

    public void write(ArrayList<Student> students) {
        try {
            MongoCollection<Document> collection = getCollection();
            for (Student student : students) {
                String name = student.getName();
                int age = student.getAge();
                String courses = student.getCourses();
                Document doc = new Document("name", name)
                        .append("age", String.valueOf(age))
                        .append("courses", courses);
                collection.insertOne(doc);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printDb() {
        MongoCollection<Document> collection = getCollection();
        FindIterable<Document> iterable = collection.find();
        System.out.println("Общее число " + collection.countDocuments());
        Bson filter = gt("age", "40");
        long count = collection.countDocuments(filter);
        System.out.println("Число студентов старше 40 " + count);
        FindIterable<Document> documents = collection.find().sort(orderBy(ascending("age"))).limit(1);
        System.out.println("Имя самого молодого студента " + getValueFromDb(documents, "name"));
        documents = collection.find().sort(orderBy(descending("age"))).limit(1);
        System.out.println("Курсы самого старого студента " + getValueFromDb(documents, "courses"));
    }

    private String getValueFromDb(FindIterable<Document> documents, String field) {
        String result = "";
        for (Document document : documents) {
            result = document.get(field).toString();
        }
        return result;
    }
}

