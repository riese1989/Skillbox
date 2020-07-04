import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static JSONObject fullJSON = new JSONObject();
    static JSONObject lineJSON = new JSONObject();
    static JSONArray changesJSON = new JSONArray();
    static JSONArray linesJSONArray = new JSONArray();
    static String filePath = "src/main/resources/map.json";

    public static void main(String[] args) throws IOException, JSONException, ParseException {
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
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(fullJSON.toString());
            file.flush();
        }
        countStationsByLines();

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

    public static void countStationsByLines() throws IOException, ParseException {
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(reader);
        org.json.simple.JSONObject listLines = (org.json.simple.JSONObject) jsonObject.get("stations");
        org.json.simple.JSONArray lines = (org.json.simple.JSONArray) jsonObject.get("lines");
        HashMap <String, String> mapLines = new HashMap<>();
        for (Object line : lines)    {
            String number = ((org.json.simple.JSONObject)line).entrySet().toArray()[0].toString().replace("number=", "");
            String name = ((org.json.simple.JSONObject)line).entrySet().toArray()[1].toString().replace("name=", "");
            mapLines.put(number,name);
        }
        for (Map.Entry<String, String> map : mapLines.entrySet())   {
            org.json.simple.JSONArray line = (org.json.simple.JSONArray) listLines.get(map.getKey());
            System.out.println(map.getValue() + " " + line.size());
        }
    }
}
