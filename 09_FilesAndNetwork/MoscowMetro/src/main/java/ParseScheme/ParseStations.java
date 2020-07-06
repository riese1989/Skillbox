package ParseScheme;

import Objects.Line;
import Objects.Station;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseStations extends LogicRunner {
    private Element station;
    private String line;

    public ParseStations(Element station, String line) {
        this.station = station;
        this.line = line;
    }

    @Override
    JSONArray get() throws JSONException {
        JSONArray lineStationsJSON = new JSONArray();
        for (Element element : station.select("p")) {
            String stationLine = element.select("span.name").get(0).childNodes().get(0).toString();
            Station stat = new Station(stationLine, Line.searchLine(line));
            Line.searchLine(line).addStation(stat);
            lineStationsJSON.put(stationLine);
            Elements changes = element.select("[title*=переход на]");
            if (changes.size() > 0 && !Station.isChanges(stat)) {
                LogicRunner.parseChanges(stat, changes);
            }
        }
        return lineStationsJSON;
    }
}
