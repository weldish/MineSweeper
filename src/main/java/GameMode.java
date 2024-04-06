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

    public static GameMode DifficultyLevel(String diffcultyLevel) {
        switch (diffcultyLevel) {
            case "easy":
                return new GameMode(8, 8, 10);
            case "medium":
                return new GameMode(15, 18, 40);
            case "hard":
                return new GameMode(25, 25, 100);
            default:
               return new GameMode(8, 8, 10);
        }
    }

}
