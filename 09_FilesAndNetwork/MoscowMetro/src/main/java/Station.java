import java.util.ArrayList;

public class Station {
    public static ArrayList <Station> stChanges = new ArrayList<>();
    private String name;
    private String line;

    public String getName() {
        return name;
    }

    public Station(String name,String line) {
        this.line = line;
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public static boolean isChanges (Station station)  {
        for (Station stChange : stChanges)  {
            if (stChange.getName().equals(station.getName()) && stChange.getLine().equals(station.getLine()))  {
                return true;
            }
        }
        return false;
    }
}
