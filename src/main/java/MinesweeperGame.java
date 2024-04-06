import java.util.Scanner;

public class MinesweeperGame {
    private Board board;
    private boolean gameIsOver;
    private boolean gameIsWon= false;

    public MinesweeperGame(int numRows, int numCols) {
        this.board = new Board(numRows, numCols);

    }

    public void startGame() {

        InputHandler inputHandler = new InputHandler();
        int remainingFlags = board.getNumberOfMines();  // keeping track of number of places flags by the user
        while (!gameIsOver && !gameIsWon) {

            board.printBoard(false, remainingFlags);
            System.out.println();

            int numRows = board.getNumRows();
            int numCols = board.getNumCols();

            UserInput userInput = inputHandler.getUserInput(numRows, numCols); // this method validates the input

            // input is validated
            String movetype = userInput.moveType;
            int row = userInput.row;
            int col = userInput.col;

            // if the input is to reveal a tile
            if(movetype.equals("r")) {

                Tile selectedTile = board.getSelectedTile(row,col); // this retrieves the selected tile

                if (selectedTile.isMine()) {
                    gameIsOver = true;
                    selectedTile.setRevealed(true);
                    System.out.println("************** Game Over ************");
                    board.printBoard(true, remainingFlags);
                    // ask if the player want to play again.
                } else {
                    board.revealTile(row, col); // reveal the tile and its neighbors if it is an empty tile
                }
                gameIsWon = board.checkIfPlayerHasWon(); // Check for a win after a player has revealed a tile
                if (gameIsWon) {
                    System.out.println("********  Congratulations, You Have Won The Game!  ********");
                    board.printBoard(true, remainingFlags); // reveal the mines since the game is won
                }

                // if the input is to flag a tile
            } else if (movetype.equals("f")) {

                if (board.getSelectedTile(row, col).isFlagged())
                {
                    remainingFlags++;
                }
                else{
                    remainingFlags--;
                }

                // flag the selected tile
                board.flagTile(row, col);

            } else {
                System.out.println("Invalid input"); // this gets printed when the user inputs the wrong action
            }

        }

    }

}