import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
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

    public static void main(String[] args) throws IOException, JSONException {
        String site = "https://www.moscowmap.ru/metro.html";
        Document doc = Jsoup.connect(site).get();
        String cssQuery = "[class*=js-metro-line t-metrostation-list-header t-icon-metroln]"; //получаем линии
        Elements lines = doc.select(cssQuery);
        linesJSONArray = parseLines(lines);
        Elements stations = doc.select("[class=js-metro-stations t-metrostation-list-table]");

        for (Element station : stations) {
            String line = station.attr("data-line");
            lineJSON.put(line, parseStations(station, line));
        }
        fullJSON.put("lines", linesJSONArray);
        fullJSON.put("connections", changesJSON);
        fullJSON.put("stations", lineJSON);
        try (FileWriter file = new FileWriter("src/main/resources/map.json")) {
            file.write(fullJSON.toString());
        }
    }

    public static JSONArray parseLines(Elements lines) throws JSONException {
        JSONArray linesJSONArray = new JSONArray();
        for (Element line : lines) {
            JSONObject dataLine = new JSONObject();
            dataLine.put("number", line.attr("data-line"));
            dataLine.put("name", line.childNodes().get(0));
            linesJSONArray.put(dataLine);
        }
        return linesJSONArray;
    }

    public static JSONArray parseStations(Element station, String line) throws JSONException {
        JSONArray lineStationsJSON = new JSONArray();
        for (Element element : station.select("p")) {
            String stationLine = element.select("span.name").get(0).childNodes().get(0).toString();
            Station stat = new Station(stationLine, line);
            lineStationsJSON.put(stationLine);
            Elements changes = element.select("[title*=переход на]");
            if (changes.size() > 0 && !Station.isChanges(stat)) {
                parseChanges(stat, changes);
            }
        }
        return lineStationsJSON;
    }

    public static void parseChanges(Station stat, Elements changes) throws JSONException {
        JSONArray chan = new JSONArray();
        JSONObject st = new JSONObject();
        st.put("line", stat.getLine());
        st.put("station", stat.getName());
        chan.put(st);
        Station.stChanges.add(stat);
        for (Element change : changes) {
            JSONObject stCHG = new JSONObject();
            String stationLine = change.attr("title").split("«|»")[1];
            String lineCHG = change.attr("class").replace("t-icon-metroln ln-", "");
            stCHG.put("line", lineCHG);
            stCHG.put("station", stationLine);
            Station.stChanges.add(new Station(stationLine, lineCHG));
            chan.put(stCHG);
        }
        changesJSON.put(chan);
    }
}
