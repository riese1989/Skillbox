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
    public static void main(String[] args) throws IOException, JSONException {
        String site = "https://www.moscowmap.ru/metro.html";
        Integer keyStat = 1;
        Document doc = Jsoup.connect(site).get();
        String cssQuery = "[class*=js-metro-line t-metrostation-list-header t-icon-metroln]"; //получаем линии
        Elements lines = doc.select(cssQuery);
        JSONArray linesJSONArray = new JSONArray();
        for (Element line : lines)  {
            JSONObject dataLine = new JSONObject();
            dataLine.put("number", line.attr("data-line"));
            dataLine.put("name", line.childNodes().get(0));
            linesJSONArray.put(dataLine);

        }
        Elements stations = doc.select("[class=js-metro-stations t-metrostation-list-table]");
        JSONObject fullJSON = new JSONObject();
        JSONObject linesStationsJSON = new JSONObject();
        JSONArray changesJSON = new JSONArray();
        for (Element station : stations) {
            String line = station.attr("data-line");
            JSONArray linesJSON = new JSONArray();
            for(Element element : station.select("p"))  {
                String stationLine = element.select("span.name").get(0).childNodes().get(0).toString();
                Station stat = new Station(stationLine, line, keyStat);
                linesJSON.put(stationLine);
                Elements changes = element.select("[title*=переход на]");
                if (changes.size() > 0)  {
                    JSONArray chan = new JSONArray();
                    JSONObject st = new JSONObject();
                    st.put("line", line);
                    st.put("station", stationLine);
                    chan.put(st);
                    if (!Station.isChanges(stat)) {
                        Station.stChanges.add(stat);
                        for (Element change : changes) {
                            keyStat++;
                            JSONObject stCHG = new JSONObject();
                            stationLine = change.attr("title").split("«|»")[1];
                            String lineCHG = change.attr("class").replace("t-icon-metroln ln-", "");
                            stCHG.put("line", lineCHG);
                            stCHG.put("station", stationLine);
                            Station.stChanges.add(new Station(stationLine,lineCHG, keyStat));
                            chan.put(stCHG);
                        }
                        changesJSON.put(chan);
                    }
                }
                keyStat++;
            }
            linesStationsJSON.put(line,linesJSON);
        }
        fullJSON.put("stations",linesStationsJSON);
        fullJSON.put("lines", linesJSONArray);
        fullJSON.put("changes", changesJSON);

        try (FileWriter file = new FileWriter("map.json")) {
            file.write(fullJSON.toString());
        }
    }
}
