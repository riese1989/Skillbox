package Objects;

import java.util.ArrayList;

public class Line {
    public static ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Station> stations = new ArrayList<>();
    private String name;
    private String number;

    public Line(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public static Line searchLine (String number) {
        for (Line line : lines) {
            if (line.number.equals(number)) {
                return line;
            }
        }
        return null;
    }
    public void addStation (Station stat)   {
        stations.add(stat);
    }
}
