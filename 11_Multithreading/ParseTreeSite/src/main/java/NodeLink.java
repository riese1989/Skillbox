import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class NodeLink extends RecursiveTask<Integer> {
    private Node node;

    public static volatile int i = 0;

    public NodeLink(Node node) {
        this.node = node;
    }

    @Override
    protected Integer compute() {
        ArrayList<RecursiveTask> pageList = new ArrayList<>();
        try {
            WriteFile wf = new WriteFile(node.getLink(), node.getLevel());
            synchronized (wf) {
                wf.export();
            }
            node.parseChildrens();
            for (Node child : node.getChildrens()) {
                i++;
                NodeLink link = new NodeLink(child);
                link.fork();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }
}
