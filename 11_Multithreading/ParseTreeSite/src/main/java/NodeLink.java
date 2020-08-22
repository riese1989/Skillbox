import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class NodeLink extends RecursiveTask<Node> {
    private Node node;

    public static volatile int i = 0;
    public static volatile ArrayList<Node> links = new ArrayList<>();

    public NodeLink(Node node) {
        this.node = node;
    }

    @Override
    protected Node compute() {
        ArrayList<NodeLink> pageList = new ArrayList<>();
        try {
            if (node.getLevel() > 1) {
                return  node;
            }
            node.parseChildrens();
            for (Node child : node.getChildrens()) {
                i++;
                NodeLink link = new NodeLink(child);
                link.fork();
                pageList.add(link);
            }
            for(NodeLink link : pageList)   {
                //System.out.println(node.getLink());
                synchronized (link) {
                    links.add(link.join());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return node;
    }
}
