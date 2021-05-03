package binary_tree;

public class BinaryTree {

  private Node root;

  public void addNode(String data) {
    Node node = new Node(data);
    if (root == null) {
      root = node;
    } else {
      addNode(root, node);
    }
    //TODO implement method
  }

  private void addNode(Node currentNode, Node node) {
    if (currentNode.compare(node) == 1) {
      if (currentNode.getLeft() == null) {
        currentNode.setLeft(node);
      } else {
        addNode(currentNode.getLeft(), node);
      }
    } else {
      if (currentNode.compare(node) == -1) {
          if (currentNode.getRight() == null) {
              currentNode.setRight(node);
          } else {
              addNode(currentNode.getRight(), node);
          }
      }
    }
  }

  public boolean isContains(String data) {
      Node node = new Node(data);
      return isContains(root, node);
  }
  private boolean isContains (Node currentNode, Node node) {
      int result = currentNode.compare(node);
      if (result == 0) {
          return true;
      }
      if (result == -1) {
          if (currentNode.getRight() == null) {
              return false;
          } else {
              return isContains(currentNode.getRight(), node);
          }
      }
      if (result == 1) {
          if (currentNode.getLeft() == null) {
              return false;
          } else {
              return isContains(currentNode.getLeft(), node);
          }
      }
      return false;
  }

  public Node getRoot() {
    return root;
  }
}
