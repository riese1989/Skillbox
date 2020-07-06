import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseJSON {
    public static void countStationsByLines() throws IOException, ParseException {
        FileReader reader = new FileReader(Main.filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONObject listLines = (JSONObject) jsonObject.get("stations");
        JSONArray lines = (JSONArray) jsonObject.get("lines");
        HashMap<String, String> mapLines = new HashMap<>();
        for (Object line : lines)    {
            String number = ((JSONObject)line).entrySet().toArray()[0].toString().replace("number=", "");
            String name = ((JSONObject)line).entrySet().toArray()[1].toString().replace("name=", "");
            mapLines.put(number,name);
        }
        for (Map.Entry<String, String> map : mapLines.entrySet())   {
            JSONArray line = (JSONArray) listLines.get(map.getKey());
            System.out.println(map.getValue() + " " + line.size());
        }
    }
}
