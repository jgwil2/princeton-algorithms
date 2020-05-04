import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {
    SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pqInitial = initPQ(initial);
        MinPQ<SearchNode> pqTwin = initPQ(initial.twin());
        SearchNode minInitial = pqInitial.delMin();
        SearchNode minTwin = pqTwin.delMin();
        while (!minInitial.board.isGoal() && !minTwin.board.isGoal()) {
            ArrayList<Board> neighborsInitial = minInitial.board.neighbors();
            for (Board neighbor :
                    neighborsInitial) {
                if (minInitial.previous == null || neighbor != minInitial.previous.board) {
                    pqInitial.insert(new SearchNode(neighbor, minInitial.moves + 1, minInitial));
                }
            }
            minInitial = pqInitial.delMin();

            ArrayList<Board> neighborsTwin = minTwin.board.neighbors();
            for (Board neighbor :
                    neighborsTwin) {
                if (minTwin.previous == null || neighbor != minTwin.previous.board) {
                    pqTwin.insert(new SearchNode(neighbor, minTwin.moves + 1, minTwin));
                }
            }
            minTwin = pqTwin.delMin();
        }

        if (minInitial.board.isGoal()){
            solution = minInitial;
        }
        else if (minTwin.board.isGoal()) {
            solution = null;
        }
    }

    private class SearchNode {
        Board board;
        int moves;
        SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    private MinPQ<SearchNode> initPQ(Board board) {
        MinPQ<SearchNode> pq = new MinPQ(new ManhattanComparator());
        pq.insert(new SearchNode(board, 0, null));
        return pq;
    }

    /**
     * Hamming priority function: number of moves + Hamming distance
     **/
    private static class HammingComparator implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return Integer.compare(a.moves + a.board.hamming(), b.moves + b.board.hamming());
        }
    }

    /**
     * Manhattan priority function: number of moves + Manhattan distance
     */
    private static class ManhattanComparator implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return Integer.compare(a.moves + a.board.manhattan(), b.moves + b.board.manhattan());
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        ArrayList<Board> list = new ArrayList<>();
        SearchNode node = solution;
        while (node.previous != null) {
            list.add(node.board);
            node = node.previous;
        }

        Collections.reverse(list);
        return list;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}