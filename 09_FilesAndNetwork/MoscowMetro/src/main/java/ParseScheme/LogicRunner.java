package ParseScheme;

import Objects.Station;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

abstract public class LogicRunner {
    public static JSONArray parseLines (Elements lines) throws JSONException {
        ParseLines parLines = new ParseLines(lines);
        return parLines.get();
    }
    public static JSONArray parseStations(Element station, String line) throws JSONException {
        ParseStations parStations = new ParseStations(station, line);
        return parStations.get();
    }
    public static JSONArray parseChanges (Station stat, Elements changes) throws JSONException {
        ParseChanges parChanges = new ParseChanges(stat, changes);
        return parChanges.get();
    }
    abstract JSONArray get() throws JSONException;
}
