package pojo;

import lombok.Getter;
import settings.DbSettings;

@Getter
public class Database {
    private String ip;
    private int port;
    private String name;

    public Database(DbSettings dbSettings) {
        ip = dbSettings.getIp();
        port = dbSettings.getPort();
        name = dbSettings.getNameDb();
    }
}
