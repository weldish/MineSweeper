public class GameMode {

    private final int numRows;
    private final int numCols;
    private final int numberOfMines;
    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public GameMode(int numRows, int numCols, int numberOfMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numberOfMines = numberOfMines;
    }



}
