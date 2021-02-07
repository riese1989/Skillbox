package services;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import objects.Database;
import objects.Student;
import org.bson.Document;

import java.util.ArrayList;

public class DbService {
    private final Database database = new Database();

    private MongoCollection<Document> getCollection()   {
        MongoClient mongoClient = new MongoClient(database.getIp(), database.getPort());;
        MongoDatabase mongoDatabase = mongoClient.getDatabase("students");
        return mongoDatabase.getCollection("studentsCollection");
    }
    public void write(ArrayList<Student> students)  {
        try {
            MongoCollection<Document> collection = getCollection();
            for (Student student : students)    {
                String name = student.getName();
                int age = student.getAge();
                String courses = student.getCourses();
                Document doc = new Document("name", name)
                        .append("age", String.valueOf(age))
                        .append("courses", courses);
                collection.insertOne(doc);
            }
        }
        catch (Exception ex)    {
            ex.printStackTrace();
        }
    }

    public void printDb()   {
        MongoCollection<Document> collection = getCollection();
        FindIterable<Document> iterable = collection.find();
        for (Document document : iterable) {
            System.out.println(document);
        }
    }


}
