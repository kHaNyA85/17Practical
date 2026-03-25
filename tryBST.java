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
  void insert(int data){
    root = insert(root, data);
  }
  boolean isBST(tNode node, long min, long max){
    if (node == null)
      return true;
    if (node.data <= min || node.data >= max)
      return false;
    return isBST(node.left, min, node.data) &&
      isBST(node.right, node.data, max)
  }
  boolean isBST(){
    return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }
  void populateBalanced(int n){
    root = null;
    int total = (1 << n) - 1;
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{1, total});
    while(!queue.isEmpty()){
      int[] range = queue.poll();
      int lo = range[0], hi = range[1];
      if (lo > hi)
        continue;
      int mid = (lo + hi) / 2;
      insert(mid);
      if (lo > mid)
        queue.add(new int[]{mid + 1, hi});
    }
  }
}
