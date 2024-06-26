import java.sql.SQLOutput;
import java.util.Random;

public class Board {
    private int numRows;
    private int numCols;
    private Tile[][] tiles;
    private int numberOfMines;
    Board(int numRows, int numCols, int numberOfMines){
        this.numRows = numRows;
        this.numCols = numCols;
        this.numberOfMines = numberOfMines;
        tiles = new Tile[numRows][numCols];   // create A 2d-array of tiles
        initTheBoard();      // initializing the board
        //placeMinesOnBoard();  // placing mines on the board
        //countMinesForEachTile(); // after randomly placing the mines, we count adjacent mines for each tile
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    // this method retrieves a tile without reveling it.
    public Tile getSelectedTile(int row, int col){
        return tiles[row][col];
    }


    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }


    private void initTheBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    public void placeMinesOnBoard(int firstMoveRow, int firstMoveCol) {
        Random rand = new Random();
        int numOfPlacedMines = 0;
        while (numOfPlacedMines < numberOfMines) {
            int row = rand.nextInt(numRows);
            int col = rand.nextInt(numCols);
            Tile randomlySelectedTile = tiles[row][col];  // randomly selected tile for the placement of a mine
            // if is this randomly selected tile is near the first move , then we don't place a mine on it
            // we also don't a mine on it if it is a mine already.
            if (!isRandomTileNearFirstMove(row, col, firstMoveRow, firstMoveCol) && !randomlySelectedTile.isMine()) {
                randomlySelectedTile.setMine(true);
                updateAdjacentMineCounts(row, col);
                numOfPlacedMines++;
            }
        }
    }

    private boolean isRandomTileNearFirstMove(int row, int col, int firstMoveRow, int firstMoveCol) {
        return Math.abs(row - firstMoveRow) <= 1 && Math.abs(col - firstMoveCol) <= 1;
    }


    private void updateAdjacentMineCounts(int placedMineRow, int placedMineCol) {
        // Looping through all adjacent cells
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                int adjacentTileRow = placedMineRow + rowOffset;
                int adjacentTileCol = placedMineCol + colOffset;

                // Check if the adjacent cell is within the bounds of the board
                if (adjacentTileRow >= 0 && adjacentTileRow < numRows && adjacentTileCol >= 0 && adjacentTileCol < numRows) {
                    // Increment the adjacent mine count if it's not a mine
                    Tile adjacentTile = tiles[adjacentTileRow][adjacentTileCol];
                    if (!adjacentTile.isMine()) {
                        adjacentTile.incrementAdjacentMines();
                    }
                }
            }
        }
    }


    public void revealTile(int row, int col){
        if (row < 0 || row >= numRows|| col < 0 || col >= numCols) {
            return;
        }
        Tile tile = tiles[row][col];   // the tile to be revealed

        if (tile.isRevealed() || tile.isFlagged()) { // checking if the tile is already revealed or flagged.
            return;
        }
        tile.setRevealed(true);      // reveal this tile

        // If the tile has 0 adjacent mines, recursively reveal adjacent tiles
        if (tile.getAdjacentMines() == 0) {
             for (int i = -1; i <= 1; i++) {
                  for (int j = -1; j <= 1; j++) {
                      if (i == 0 && j == 0) continue; // Skip the current tile

                      revealTile(row + i, col + j); // Recursive call for neighboring tiles
                  }
              }
          }
    }

    public void flagTile(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            System.out.println("Invalid coordinates");
            return;
        }
        Tile tile = tiles[row][col];
        if (!tile.isRevealed()) { // This is to prevent flagging a tile that is already revealed
            tile.setFlagged(!tile.isFlagged()); // This is for flagging or unflagging a tile
        }
    }


    public boolean checkIfPlayerHasWon() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Tile tile = tiles[row][col];
                // If there's a non-mine tile that's not revealed, the game is not won yet
                if (!tile.isMine() && !tile.isRevealed()) {
                    return false;
                }
            }
        }
        // All non-mine tiles have been revealed
        return true;
    }

    // a method for revealing all mines if the game is over
    public void revealAllMines() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Tile tile = tiles[row][col];
                // if the tile is a mine and it is not flagged by the player already: set it to true
                if (tile.isMine() && !tile.isFlagged()) {
                    tile.setRevealed(true);
                }
            }
        }
    }

    public void printBoard(int remainingFlags) {
        //System.out.println();
        System.out.println( "Remaining Flags: " + ANSIColors.CYAN_TEXT + remainingFlags + ANSIColors.RESET);
        System.out.println();
        System.out.print("     ");
        for (int col = 0; col < numCols; col++) {
            if( col < 10) {
                System.out.print(ANSIColors.BLUE_TEXT + col + "  " + ANSIColors.RESET);
            } else {
                System.out.print(ANSIColors.BLUE_TEXT + col + " "+ ANSIColors.RESET);
            }

        }
        System.out.println();
        System.out.println();
        for (int row = 0; row < numRows; row++) {

            if( row < 10) {
                System.out.print(ANSIColors.BLUE_TEXT + row + "   " + ANSIColors.RESET);
            } else {
                System.out.print(ANSIColors.BLUE_TEXT + row + "  " + ANSIColors.RESET);
            }
            for (int col = 0; col < numCols; col++) {
                Tile tile = tiles[row][col];
                if (tile.isRevealed()) {
                    if (tile.isMine()) {
                        System.out.print(ANSIColors.RED_TEXT + " M " + ANSIColors.RESET); // print M for Mine
                    } else {
                        System.out.print(tile.getAdjacentMines() > 0 ? ANSIColors.GREEN_TEXT +  " " + tile.getAdjacentMines() + " " + ANSIColors.RESET: ANSIColors.WHITE_TEXT + " - " + ANSIColors.RESET); // print a Number or a space for 0 adjacent mines
                    }
                } else if (tile.isFlagged()) {
                    System.out.print(ANSIColors.BLACK_TEXT + ANSIColors.BG_YELLOW +" F "+ ANSIColors.RESET); // Flagged
                } else {
                    System.out.print(ANSIColors.BLACK_TEXT + ANSIColors.BG_WHITE + " H " + ANSIColors.RESET); // H for a tile that is not revealed yet
                }
            }
            System.out.println();
        }
    }

}


