package binary_tree;

public final class Node {
    private final String data;
    private Node left;
    private Node right;

    public Node(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int compare(Node node)   {
        if (data.compareTo(node.getData()) > 0) {
            return 1;
        }
        if (data.compareTo(node.getData()) < 0) {
            return -1;
        }
        return 0;
    }
}