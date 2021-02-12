package pojo;

import settings.DbSettings;

public class Database {
    private String ip;
    private int port;
    private String name;

    public Database(DbSettings dbSettings) {
        ip = dbSettings.getIp();
        port = dbSettings.getPort();
        name = dbSettings.getNameDb();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
