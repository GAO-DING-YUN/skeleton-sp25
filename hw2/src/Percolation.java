import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private boolean[][] grid;
    private int N;
    private int numberOpen;
    private WeightedQuickUnionUF UF;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must > 0");
        }
        numberOpen = 0;
        this.N = N;
        UF = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int next[][] = new int[][] {
                {0, 1},
                {1, 0},
                {0, -1},
                {-1, 0}
        };
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("must between 0 to N - 1");
        }
        numberOpen++;
        grid[row][col] = true;
        int target = xyTo1D(col, row);
        for (int i = 0; i <= 3; i++) {
            int mx = row + next[i][0];
            int my = col + next[i][1];
            int index = xyTo1D(my, mx);

            if (mx == -1) {
                UF.union(N * N, target);
            } else if (mx == N) {
                UF.union(N * N + 1, target);
            }

            if (mx < 0 || mx >= N || my < 0 || my >= N) {
                continue;
            }

            if (UF.connected(index, target)) {
                continue;
            }

            if (isOpen(mx, my)) {
                UF.union(target, index);
            }
        }
    }

    public int xyTo1D(int c, int r) {
        return r + c * N;
    }

    public boolean isOpen(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw  new IllegalArgumentException("row or col error");
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw  new IllegalArgumentException("row or col error");
        }
        return UF.connected(xyTo1D(col, row), N * N);
    }

    public int numberOfOpenSites() {
        return numberOpen;
    }

    public boolean percolates() {
        return UF.connected(N * N, N * N + 1);
    }

}
