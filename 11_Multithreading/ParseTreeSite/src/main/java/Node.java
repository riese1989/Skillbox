import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Node {
    private String link;
    private Integer level;
    private volatile ArrayList<Node> childrens = new ArrayList<>();
    public static volatile ArrayList<Node> listNodes = new ArrayList<>();
    public static final ArrayList<Node> reestrNodes = new ArrayList<>();

    public Node(String link, Integer level) {
        this.link = link;
        this.level = level;
    }

    public synchronized ArrayList<Node> getChildrens() throws IOException, InterruptedException {
        return childrens;
    }

    public void setChildrens(ArrayList<Node> childrens) {
        this.childrens = childrens;
    }

    public synchronized Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public synchronized String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Node() {
    }

    public void parseChildrens() throws InterruptedException, IOException {
        Thread.sleep(150);
        Document doc;
        try {
            doc = Jsoup.connect(link).ignoreContentType(true).get();
            Elements links = doc.select("a");
            for (Element linkEl : links) {
                Node subSite;
                if (linkEl.attr("href").equals("")) {
                    continue;
                }
                String cutSymb = linkEl.attr("href");
                if (cutSymb.length() != 1) {
                    cutSymb = linkEl.attr("href").substring(1);
                } else {
                    cutSymb = "";
                }
                String href = linkEl.attr("href");
                if (cutSymb.equals("") || !href.startsWith("/")) {
                    continue;
                }
                synchronized (reestrNodes) {
                    String linkSub = Main.siteOriginal + cutSymb;
                    if(!linkSub.contains(link) &&  linkSub.equals(link))   {
                        continue;
                    }
                    Node returnNode = ReturnNode(linkSub);
                    if(returnNode != null) {
                        continue;
                    }
                    else    {
                        subSite = new Node(linkSub, level + 1);
                        childrens.add(subSite);
                    }
                }
                synchronized (reestrNodes) {
                    ListIterator<Node> iterator = reestrNodes.listIterator();
                    while(iterator.hasNext())   {
                        iterator.next();
                    }
                    iterator.add(subSite);
                }

            }
        } catch (HttpStatusException | SocketTimeoutException ignored) {
        }
    }

    public synchronized static Node ReturnNode(String link) {
        for (Iterator<Node> it = reestrNodes.iterator(); it.hasNext(); )    {
            Node linkNode = it.next();

            if (linkNode.getLink().equals(link))    {
                return linkNode;
            }
        }
        return null;
    }
}
