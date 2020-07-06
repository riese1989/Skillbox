import ParseScheme.LogicRunner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static JSONObject fullJSON = new JSONObject();
    static JSONObject lineJSON = new JSONObject();
    static JSONArray changesJSON = new JSONArray();
    static JSONArray linesJSONArray = new JSONArray();
    static String filePath = "src/main/resources/map.json";

    public static void main(String[] args) throws IOException, JSONException, ParseException {

        Web web = new Web("https://www.moscowmap.ru/metro.html");
        Document doc = web.getDoc();
        String cssQuery = "[class*=js-metro-line t-metrostation-list-header t-icon-metroln]"; //получаем линии
        Elements lines = doc.select(cssQuery);
        linesJSONArray = LogicRunner.parseLines(lines);
        Elements stations = doc.select("[class=js-metro-stations t-metrostation-list-table]");

        for (Element station : stations) {
            String line = station.attr("data-line");
            lineJSON.put(line, LogicRunner.parseStations(station, line));
        }
        fullJSON.put("lines", linesJSONArray);
        fullJSON.put("connections", changesJSON);
        fullJSON.put("stations", lineJSON);
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(fullJSON.toString());
            file.flush();
        }
        ParseJSON.countStationsByLines();
    }
}
