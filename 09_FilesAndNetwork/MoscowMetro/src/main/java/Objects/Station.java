package Objects;

import java.util.ArrayList;

public class Station {
    public static ArrayList <Station> stChanges = new ArrayList<>();
    private String name;
    private Line line;

    public String getName() {
        return name;
    }

    public Station(String name,Line line) {
        this.line = line;
        this.name = name;
    }

    public Line getLine() {
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
