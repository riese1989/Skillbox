import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;

public class Main {
    static String siteOriginal = "https://lenta.ru/";

    public static void main(String[] args) throws IOException, InterruptedException {
        Date start = new Date();
        Node node = new Node(siteOriginal,0);
        Node.listNodes.add(node);
        Node.reestrNodes.add(node);
        Integer result = new ForkJoinPool().invoke(new NodeLink(node));
        Date finish = new Date();
        System.out.println("Парсили " + (finish.getTime() - start.getTime()));
    }
}