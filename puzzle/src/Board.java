import java.util.ArrayList;

public class Board {
    public int[][] rows;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.rows = tiles;
    }

    // string representation of this board
    public String toString() {
        String repr = Integer.toString(dimension());
        for (int[] row : rows) {
            repr = repr.concat("\r\n\t");
            for (int tile : row) {
                repr = repr.concat(Integer.toString(tile).concat(" "));
            }
        }
        return repr.concat("\r\n");
    }

    // board dimension n
    public int dimension() {
        return rows.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        int len = dimension();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                boolean isLast = i == len - 1 && j == len - 1;
                int target = i * len + j + 1;
                if (target != rows[i][j] && !isLast) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int len = dimension();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int tile = rows[i][j], targetRow, targetCol;
                if (tile != 0) {
                    targetRow = (tile - 1) / len;
                    targetCol = (tile - 1) % len;
                    manhattan += Math.abs(targetRow - i) + Math.abs(targetCol - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0 && manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Board y) {
        int size = dimension();
        if (y.dimension() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.rows[i][j] != y.rows[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public ArrayList<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        // find 0 (empty tile) and, for each adjacent tile,
        // add a board to neighbors that swaps that tile and 0
        int len = dimension();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (rows[i][j] == 0) {
                    // swap up
                    if (i > 0) {
                        Board board = new Board(copyTiles(rows));
                        board.rows[i][j] = board.rows[i - 1][j];
                        board.rows[i - 1][j] = 0;
                        neighbors.add(board);
                    }
                    // swap down
                    if (i < len - 1) {
                        Board board = new Board(copyTiles(rows));
                        board.rows[i][j] = board.rows[i + 1][j];
                        board.rows[i + 1][j] = 0;
                        neighbors.add(board);
                    }
                    // swap left
                    if (j > 0) {
                        Board board = new Board(copyTiles(rows));
                        board.rows[i][j] = board.rows[i][j - 1];
                        board.rows[i][j - 1] = 0;
                        neighbors.add(board);
                    }
                    // swap right
                    if (j < len - 1) {
                        Board board = new Board(copyTiles(rows));
                        board.rows[i][j] = board.rows[i][j + 1];
                        board.rows[i][j + 1] = 0;
                        neighbors.add(board);
                    }
                    return neighbors;
                }
            }
        }
        // HACK needed to fool the type system, but this
        // statement is never reached for a valid board
        return neighbors;
    }

    private int[][] copyTiles(int[][] tiles) {
        int len = dimension();
        int[][] ret = new int[len][];
        for (int i = 0; i < len; i++) {
            ret[i] = tiles[i].clone();
        }
        return ret;
    }

    // a board that is obtained by exchanging any pair of tiles
    // where 0 is not a tile
    // used to detect unsolvable puzzles
    public Board twin() {
        int len = dimension();
        Board board = new Board(copyTiles(rows));
        if (board.rows[0][0] != 0) {
            int temp = board.rows[0][0];
            if (board.rows[0][1] != 0) {
                // swap right
                board.rows[0][0] = board.rows[0][1];
                board.rows[0][1] = temp;
            }
            else {
                // swap down
                board.rows[0][0] = board.rows[1][0];
                board.rows[1][0] = temp;
            }
        }
        else {
            // top left tile is empty
            int temp = board.rows[0][1];
            board.rows[0][1] = board.rows[1][1];
            board.rows[1][1] = temp;
        }
        return board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board solvedBoard = new Board(tiles);
        assert solvedBoard.hamming() == 0;
        assert solvedBoard.manhattan() == 0;
        assert solvedBoard.isGoal();
        assert solvedBoard.neighbors().size() == 2;
        assert solvedBoard.twin().rows[0][0] == 2;

        int[][] moreTiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board unsolvedBoard = new Board(moreTiles);
        assert unsolvedBoard.hamming() == 5;
        assert unsolvedBoard.manhattan() == 10;
        assert !unsolvedBoard.isGoal();
        assert unsolvedBoard.neighbors().size() == 4;
        assert unsolvedBoard.twin().rows[0][0] == 1;

        assert !solvedBoard.equals(unsolvedBoard);
    }

}