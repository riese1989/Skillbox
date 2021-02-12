package settings;

public class DbSettings {
    private String ip = "127.0.0.1";
    private int port = 27017;
    private String nameDb = "retail";

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getNameDb() {
        return nameDb;
    }
}
