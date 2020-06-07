import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        String site = "https://lenta.ru/";
        String downloadDir = "download/";
        Integer i = 1;
        Document doc = Jsoup.connect(site).get();
        Elements elements = doc.select("img.g-picture");
        for (Element element : elements)   {
            String url = element.absUrl("src");
            String absPathDownload = downloadDir + i.toString() + ".jpg";
            download(url, absPathDownload);
            i++;
        }

    }

    private static void download (String urlStr, String absPathDownload) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel site = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(absPathDownload);
        fos.getChannel().transferFrom(site, 0, Long.MAX_VALUE);
        fos.close();
        site.close();
    }
}
