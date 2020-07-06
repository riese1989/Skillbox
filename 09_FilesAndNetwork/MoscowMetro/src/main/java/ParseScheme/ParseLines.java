package ParseScheme;

import Objects.Line;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

class ParseLines extends LogicRunner  {
    private Elements lines;

    public ParseLines(Elements lines) {
        this.lines = lines;
    }

    @Override
    public JSONArray get() throws JSONException {
        JSONArray linesJSONArray = new JSONArray();
        for (Element line : lines) {
            JSONObject dataLine = new JSONObject();
            String number = line.attr("data-line");
            Node name = line.childNodes().get(0);
            dataLine.put("number", number);
            dataLine.put("name", name);
            Line.lines.add(new Line (name.toString(), number));
            linesJSONArray.put(dataLine);
        }
        return linesJSONArray;
    }
}
