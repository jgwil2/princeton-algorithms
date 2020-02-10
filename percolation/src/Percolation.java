import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private int virtualTop;
    private int virtualBottom;
    private boolean[][] grid;
    public int openSitesCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        // union find size is n*n grid plus virtual top and bottom
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        // all cells initialize to false
        this.grid = new boolean[n][n];
        // connect top row to virtual top
        for (int i = 0; i < n; i++) {
            this.uf.union(this.virtualTop, i);
        }
        // connect bottom row to virtual bottom
        for (int i = 0; i < n; i++) {
            this.uf.union(this.virtualBottom, n * n - i - 1);
        }
    }

    private int getIDFromCoordinates(int row, int col) {
        return row * this.n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.grid[row][col]) {
            return;
        }
        this.grid[row][col] = true;
        this.openSitesCount++;
        int currentSite = this.getIDFromCoordinates(row, col);
        // check all adjacent sites and connect if open
        // top
        if (row > 0 && this.grid[row - 1][col]) {
            this.uf.union(currentSite, this.getIDFromCoordinates(row - 1, col));
        }
        // right
        if (col < n - 1 && this.grid[row][col + 1]) {
            this.uf.union(currentSite, this.getIDFromCoordinates(row, col + 1));
        }
        // bottom
        if (row < n - 1 && this.grid[row + 1][col]) {
            this.uf.union(currentSite, this.getIDFromCoordinates(row + 1, col));
        }
        // left
        if (col > 0 && this.grid[row][col - 1]) {
            this.uf.union(currentSite, this.getIDFromCoordinates(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.uf.find(this.getIDFromCoordinates(row, col)) == this.uf.find(this.virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.find(this.virtualTop) == this.uf.find(this.virtualBottom);
    }
}
