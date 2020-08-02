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
//        if (!Node.listNodes.contains(node))  {
//            Node.listNodes.add(node);
//        }
        try {
            if (node.getLink().equals("https://lenta.ru/rubrics/russia/"))  {
                System.out.println();
            }
            for (Node child : node.getChildrens())   {
                i++;
                    //if (child.getChildrens().size()!=0 && !Node.listNodes.contains(child) && child.getLevel() < 3)
//                if (child.getChildrens().size()!=0 && child.getLevel() < 3){
//                        new ForkJoinPool().invoke(new NodeLink(child));
//                    }
//                    else {
                        NodeLink link = new NodeLink(child);
                        link.fork();
                        //pageList.add(link);
//                        synchronized (Node.listNodes) {
//                            Node.listNodes.add(child);
//                       // }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }
}
