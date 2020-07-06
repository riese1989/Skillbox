package ParseScheme;

import Objects.Line;
import Objects.Station;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseChanges extends LogicRunner {
    private Station stat;
    private Elements changes;

    public ParseChanges(Station stat, Elements changes) {
        this.stat = stat;
        this.changes = changes;
    }

    @Override
    JSONArray get() throws JSONException {
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
            Station.stChanges.add(new Station(stationLine, Line.searchLine(lineCHG)));
            chan.put(stCHG);
        }
        return chan;
    }
}
