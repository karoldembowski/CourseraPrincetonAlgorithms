import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;

public class Board {

    private int dimension;
    private int[][] tilesArr;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        tilesArr = copy(tiles);
        dimension = tilesArr.length;
    }

    private int[][] copy(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++)
            for (int col = 0; col < tiles.length; col++)
                copy[row][col] = tiles[row][col];

        return copy;
    }

    // string representation of this board
    public String toString() {
        String toStringVal = "";
        toStringVal = toStringVal.concat(Integer.toString(dimension));
        toStringVal = toStringVal.concat("\n");
        for (int i = 0; i < tilesArr.length; i++) {
            for (int j = 0; j < tilesArr[i].length; j++) {
                toStringVal = toStringVal.concat(Integer.toString(tilesArr[i][j]) + " ");
            }
            toStringVal = toStringVal.concat("\n");
        }
        return toStringVal;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingValue = 0;
        for (int i = 0; i < tilesArr.length; i++) {
            for (int j = 0; j < tilesArr[i].length; j++) {
                if (tilesArr[i][j] != 0 && !isInRightPlace(i, j)) hammingValue++;
            }
        }
        return hammingValue;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanValue = 0;
        for (int col = 0; col < dimension; col++) {
            for (int row = 0; row < dimension; row++) {
                if (tilesArr[row][col] != 0 && !isInRightPlace(row, col)) {
                    manhattanValue += getManhattanDistance(row, col);
                }
            }
        }
        return manhattanValue;
    }

    private boolean isInRightPlace(int row, int col) {
        return tilesArr[row][col] == col + row * dimension + 1;
    }

    private int getManhattanDistance(int row, int col) {
        int tileValue = tilesArr[row][col];

        int manhattanVertical;
        int manhattanHorizontal;

        int goalCol = (tileValue % dimension == 0) ? (dimension - 1) : ((tileValue + dimension) % dimension) - 1;
        manhattanVertical = absDiff(col, goalCol);

        int goalRow = (tileValue % dimension == 0) ? Math.floorDiv(tileValue, dimension + 1) : Math.floorDiv(tileValue, dimension);
        manhattanHorizontal = absDiff(row, goalRow);

        return manhattanVertical + manhattanHorizontal;
    }

    private int absDiff(int x, int y) {
        if (x >= y) return Math.abs(x - y);
        else return Math.abs(y - x);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0 && hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board obj = (Board) y;
        return Arrays.deepEquals(obj.tilesArr, this.tilesArr);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();

        int[][] neighboursArr = new int[4][2];
        neighboursArr[0] = new int[]{-1, 0};
        neighboursArr[1] = new int[]{0, -1};
        neighboursArr[2] = new int[]{1, 0};
        neighboursArr[3] = new int[]{0, 1};

        int emptyTileRow = -1;
        int emptyTileCol = -1;
        boolean breakCheck = false;
        for (int col = 0; col < tilesArr.length; col++) {
            if (breakCheck) break;
            for (int row = 0; row < tilesArr.length; row++) {
                if (tilesArr[row][col] == 0) {
                    emptyTileCol = col;
                    emptyTileRow = row;
                    breakCheck = true;
                    break;
                }
            }
        }

        for (int[] neighbour : neighboursArr) {
            if ((emptyTileRow == 0 && neighbour[0] < 0) || (emptyTileRow == dimension - 1 && neighbour[0] > 0))
                continue;
            if ((emptyTileCol == 0 && neighbour[1] < 0) || (emptyTileCol == dimension - 1 && neighbour[1] > 0))
                continue;
            int[][] newTiles = new int[dimension][dimension];
            for (int row = 0; row < tilesArr.length; row++) {
                for (int col = 0; col < tilesArr.length; col++) {
                    newTiles[row][col] = tilesArr[row][col];
                }
            }
            newTiles[emptyTileRow][emptyTileCol] = newTiles[(emptyTileRow + neighbour[0])][(emptyTileCol + neighbour[1])];
            newTiles[emptyTileRow + neighbour[0]][emptyTileCol + neighbour[1]] = 0;
            neighbours.push(new Board(newTiles));
        }

        return neighbours;
    }


    public Board twin() {
        for (int row = 0; row < tilesArr.length; row++)
            for (int col = 0; col < tilesArr.length - 1; col++)
                if (tilesArr[row][col] != 0 && tilesArr[row][col + 1] != 0) {
                    int[][] twinTilesArr = copy(tilesArr);
                    int temp = twinTilesArr[row][col + 1];
                    twinTilesArr[row][col + 1] = twinTilesArr[row][col];
                    twinTilesArr[row][col] = temp;
                    return new Board(twinTilesArr);
                }
        throw new RuntimeException();
    }


    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] tiles = {{5, 8, 7}, {1, 4, 6}, {3, 0, 2}};
        Board test = new Board(tiles);
        StdOut.println(test.toString());
        StdOut.println(test.twin().toString());
    }

}
