import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class NodeLink extends RecursiveTask<Integer> {
    private Node node;

    public static volatile int i = 0;

    public NodeLink(Node node) {
        this.node = node;
    }

    @Override
    protected synchronized Integer compute() {
        ArrayList<RecursiveTask> pageList = new ArrayList<>();
        try {
            WriteFile fw = new WriteFile(node.getLink(), node.getLevel());
            fw.export();
            for (Node child : node.getChildrens()) {
                i++;
                if (node.getLevel() < 3) {
                    NodeLink link = new NodeLink(child);
                    link.fork();
                    pageList.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }
}
