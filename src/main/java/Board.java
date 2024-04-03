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
            if (!tile.isMine) {  // checking incase the tile is a mine
                tile.isMine = true;
                numOfPlacedMines++;
            }
        }
    }


}
