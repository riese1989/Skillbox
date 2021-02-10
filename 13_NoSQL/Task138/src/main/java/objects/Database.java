package objects;

import com.mongodb.MongoClient;
import lombok.Getter;
import settings.DbSettings;

@Getter
public class Database {

    private String ip = "127.0.0.1";
    private int port = 27017;

}
