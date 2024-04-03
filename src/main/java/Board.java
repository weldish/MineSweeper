import java.util.Random;

public class Board {
    private int numRows;
    private int numCols;
    private Tile[][] tiles;
    private int numberOfMines = 10;   //
    Board(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        tiles = new Tile[numRows][numCols];   // A 2d-array of tiles
        initTheBoard();      // initializing the board
        placeMinesOnBoard();  // placing mines on the board
        countAdjacentMines(); // after randomly placing the mines, we count adjacent mines for each tile
    }

    private void initTheBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                tiles[i][j] = new Tile( i, j);
            }
        }
    }

    private void placeMinesOnBoard() {
        Random rand = new Random();
        int numOfPlacedMines = 0;
        while (numOfPlacedMines < numberOfMines) {
            int row = rand.nextInt(numRows);
            int col = rand.nextInt(numCols);
            Tile tile = tiles[row][col];
            if (!tile.isMine()) {  // checking incase the tile is a mine
                tile.setMine(true);
                numOfPlacedMines++;
            }
        }
    }

    private void countAdjacentMines(){
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!tiles[row][col].isMine()) { // checking if the time is a mine and if it is we don,t need to consider it
                    int mines = countMines(row, col);
                    tiles[row][col].setAdjacentMines(mines);
                }
            }
        }
    }

    private int countMines(int numRows, int numCols){
        // to be done tommorrow
    }


    public void printBoard(boolean revealMines) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Tile tile = tiles[i][j];
                if (tile.isRevealed() || (revealMines && tile.isMine())) {
                    System.out.print(tile.isMine() ? "* " : tile.getAdjacentMines() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }


}
