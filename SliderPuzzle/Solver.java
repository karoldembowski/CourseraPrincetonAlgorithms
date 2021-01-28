import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;


public class Solver {
    private int moves;
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("argument is null");
        MinPQ<SearchNode> priorityQue = new MinPQ<>();
        solution = new Stack<>();
        priorityQue.insert(new SearchNode(initial, null));
        while (!priorityQue.min().getBoard().isGoal()) {
            SearchNode removedNode = priorityQue.delMin();
            for (Board neighbour : removedNode.getBoard().neighbors()) {
                if (removedNode.getPrevious() == null || (removedNode.getPrevious() != null && !neighbour.equals(removedNode.getPrevious().getBoard()))) {
                    priorityQue.insert(new SearchNode(neighbour, removedNode));
                }
            }
            if (priorityQue.isEmpty()) {
                moves = -1;
                break;
            }
        }

        moves = priorityQue.min().getMoves();
        solution.push(priorityQue.min().getBoard());
        SearchNode next = priorityQue.min().getPrevious();
        while (next != null) {
            solution.push(next.getBoard());
            next = next.getPrevious();
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous = null;

        public SearchNode(Board searchBoard, SearchNode previousNode) {
            board = searchBoard;
            previous = previousNode;
            if (previous == null) moves = 0;
            else moves = previousNode.getMoves() + 1;
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPrevious() {
            return previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.getBoard().manhattan() + this.getMoves()) - (that.getBoard().manhattan() + that.getMoves());
            return priorityDiff == 0 ? this.getBoard().manhattan() - that.getBoard().manhattan() : priorityDiff;
        }
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
