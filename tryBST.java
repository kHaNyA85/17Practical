import java.util.LinkedList;
import java.util.Queue;

class tNode{
  int data;
  tNode left, right;

  tNode(int data){
    this.data = data;
    this.left = null;
    this.right = null;
  }
}
class BST {
  tNode root;

  BST(){
    root = null;
  }
  tNode insert(tNode node, int data){
    if (node == null)
      return new tNode(data);
    if (data <= node.data)
      node.left = insert(node.left, data);
    else if (data > node.data)
      node.right = insert(node.right, data);
    return node;
  }
}
