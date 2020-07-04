import java.util.ArrayList;
import java.util.Comparator;

public class Station {
    public static ArrayList <Station> stChanges = new ArrayList<>();
    private String name;
    private String line;
    private Integer key;

    public String getName() {
        return name;
    }

    public Station(String name, String line, Integer key) {
        this.name = name;
        this.line = line;
        this.key = key;
    }

    public Integer getKey() {
        return key;
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

    public static Comparator <Station> sorted() {
        Comparator comp = (Comparator<Station>) (o1, o2) -> {
            if (o1.getKey() < o2.getKey())  {
                return -1;
            }
            if (o1.getKey() < o2.getKey())  {
                return 1;
            }
            return 0;
        };
        return comp;
    }
}
