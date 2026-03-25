import java.util.LinkedList;
import java.util.Queue;

class tNode {
    int data;
    tNode left, right;

    tNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class BST {
    tNode root;

    BST() {
        root = null;
    }

    tNode insert(tNode node, int data) {
        if (node == null)
            return new tNode(data);
        if (data < node.data)
            node.left = insert(node.left, data);
        else
            node.right = insert(node.right, data);
        return node;
    }

    void insert(int data) {
        root = insert(root, data);
    }

    boolean isBST(tNode node, long min, long max) {
        if (node == null)
            return true;
        if (node.data <= min || node.data >= max)
            return false;
        return isBST(node.left, min, node.data) &&
               isBST(node.right, node.data, max);
    }

    boolean isBST() {
        return isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    void populateBalanced(int n) {
        root = null;
        int total = (1 << n) - 1;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{1, total});

        while (!queue.isEmpty()) {
            int[] range = queue.poll();
            int lo = range[0], hi = range[1];

            if (lo > hi)
                continue;

            int mid = (lo + hi) / 2;
            insert(mid);

            if (lo <= mid - 1)
                queue.add(new int[]{lo, mid - 1});
            if (mid + 1 <= hi)
                queue.add(new int[]{mid + 1, hi});
        }
    }

    tNode findMin(tNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    tNode delete(tNode node, int data) {
        if (node == null)
            return null;

        if (data < node.data)
            node.left = delete(node.left, data);
        else if (data > node.data)
            node.right = delete(node.right, data);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;

            tNode temp = findMin(node.right);
            node.data = temp.data;
            node.right = delete(node.right, temp.data);
        }
        return node;
    }

    void delete(int data) {
        root = delete(root, data);
    }

    void removeEvens(int n) {
        for (int i = 2; i <= n; i += 2) {
            delete(i);
        }
    }
}

public class tryBST {
    public static void main(String[] args) {
        int n = 15;
        int runs = 30;
        long[] insertTimes = new long[runs];
        long[] deleteTimes = new long[runs];

        for (int i = 0; i < runs; i++) {
            BST tree = new BST();

            long startInsert = System.currentTimeMillis();
            tree.populateBalanced(n); // fixed method call
            long endInsert = System.currentTimeMillis();
            insertTimes[i] = endInsert - startInsert;

            if (!tree.isBST()) {
                System.out.println("Tree is NOT a BST");
                return;
            }

            long startDelete = System.currentTimeMillis();
            int maxKey = (1 << n) - 1;
            tree.removeEvens(maxKey);
            long endDelete = System.currentTimeMillis();
            deleteTimes[i] = endDelete - startDelete;
        }

        double avgInsert = average(insertTimes);
        double avgDelete = average(deleteTimes);

        double stdInsert = stdDev(insertTimes, avgInsert);
        double stdDelete = stdDev(deleteTimes, avgDelete);

        System.out.println("Number of keys: " + ((1 << n) - 1) + "   n: " + n);
        System.out.println();
        System.out.println("Populate tree");
        System.out.println("Average time (ms): " + avgInsert);
        System.out.println("Standard deviation: " + stdInsert);
        System.out.println();
        System.out.println("Remove evens from tree");
        System.out.println("Average time (ms): " + avgDelete);
        System.out.println("Standard deviation: " + stdDelete);
    }

    static double average(long[] arr) {
        double sum = 0;
        for (long v : arr) sum += v;
        return sum / arr.length;
    }

    static double stdDev(long[] arr, double mean) {
        double sum = 0;
        for (long v : arr) sum += Math.pow(v - mean, 2);
        return Math.sqrt(sum / arr.length);
    }
}
