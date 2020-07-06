import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Web {
    private String url;

    public Web(String url) {
        this.url = url;
    }
    public Document getDoc() throws IOException {
        return Jsoup.connect(url).get();
    }
}
