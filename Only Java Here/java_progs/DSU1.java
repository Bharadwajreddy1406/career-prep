import java.util.*;

public class DSU1{

    static int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent); // path compression
        }
        return parent[x];
    }

    static void union(){


    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // number of elements
        int m = sc.nextInt(); // number of operations

        int[][] routes = new int[m][2];
        for (int i = 0; i < m; i++) {
            routes[i][0] = sc.nextInt(); // first element of the operation
            routes[i][1] = sc.nextInt(); // second element of the operation
        }

        int[] parent = new int[n]; // parent[i] is the parent of i
        int [] rank = new int[n];
        for (int i = 1; i <= n; i++) {
            parent[i] = i; // initially, each element is its own parent
            rank[i] = 1; // initially, each element has rank 1
        }

        for (int i = 0; i < m; i++) {
            int a = routes[i][0];
            int b = routes[i][1];
            union();
        }
    }
}