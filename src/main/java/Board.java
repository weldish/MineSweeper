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
        countMinesForEachTile(); // after randomly placing the mines, we count adjacent mines for each tile
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

    // counting the number of mines adjacent to every tile that is not a mine.
    private void countMinesForEachTile(){
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!tiles[row][col].isMine()) { // checking if the time is a mine and if it is we don,t need to consider it
                    int mines = countAdjacentMines(row, col);
                    tiles[row][col].setAdjacentMines(mines); // setting the number of mines nearby
                }
            }
        }
    }

    // counting the number of mines that are adjacent to a tile.
    private int countAdjacentMines(int row, int col){
        int numOfNearbyMines = 0;

        // left three of the eight tiles
        numOfNearbyMines += checkForMine(row-1, col-1);
        numOfNearbyMines += checkForMine(row, col-1);
        numOfNearbyMines += checkForMine(row+1, col-1);

        // right three of the eight tiles
        numOfNearbyMines += checkForMine(row-1, col+1);
        numOfNearbyMines += checkForMine(row, col+1);
        numOfNearbyMines += checkForMine(row+1, col+1);

        // top one
        numOfNearbyMines += checkForMine(row-1, col);

        // bottom one
        numOfNearbyMines += checkForMine(row+1, col);


        return numOfNearbyMines;

    }

    // this method checks if this tile is a mine or not and if it is it returns 1
    private int checkForMine(int row, int col){
    // check if it is out of bounds
        if(row < 0 || row >= numRows || col < 0 || col >= numCols){
            return 0;
        }
        Tile tile = tiles[row][col];
        if (tile.isMine()){ // if it is a mine
            return 1;
        }
        return 0;
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
