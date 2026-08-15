import java.util.*;

public class SegmentTree {

    public static void build(int index, int low, int high, int[] arr, int[] tree) {
        if (low == high) {
            tree[index] = arr[low];
            return;
        }

        int mid = (low + high) / 2;

        build(2 * index + 1, low, mid, arr, tree);
        build(2 * index + 2, mid + 1, high, arr, tree);
        tree[index] = tree[2 * index + 1] + tree[2 * index + 2];

    }

    public static int query(int low, int high, int l, int r, int index, int[] tree) {
        if (low > r || high < l) {
            return 0;
        }
        if (low >= l && high <= r) {
            return tree[index];
        }

        int mid = (low + high) / 2;
        return query(low, mid, l, r, 2 * index + 1, tree) + query(mid + 1, high, l, r, 2 * index + 2, tree);
    }

    public static void update(int low, int high, int index, int value, int[] arr, int[] tree) {
        if (low == high) {
            arr[low] = value;
            tree[index] = value;
            return;
        }

        int mid = (low + high) / 2;

        if (low <= index && index <= mid) {
            update(low, mid, index, value, arr, tree);
        } else {
            update(mid + 1, high, index, value, arr, tree);
        }
        tree[index] = tree[2 * index + 1] + tree[2 * index + 2];
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        int tree[] = new int[4 * n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        build(0, 0, n - 1, arr, tree);
        System.out.println("Segment Tree: ");
        for (int i = 0; i < tree.length; i++) {
            System.out.print(tree[i] + " ");
        }

        int q = scanner.nextInt();
        int[] queries = new int[q];
        for (int i = 0; i < q; i++) {
            queries[i] = scanner.nextInt();
        }
        System.out.println("Queries: ");
        for (int i = 0; i < q; i++) {
            System.out.print(queries[i] + " ");
        }

        System.out.println();

        scanner.close();
    }

}
