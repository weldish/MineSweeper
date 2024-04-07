import java.util.Scanner;

public class MinesweeperGame {
    private Board board;
    private boolean gameIsOver;
    private boolean gameIsWon= false;
    private boolean isTheFirstMove = true;
    private InputHandler inputHandler;
    private GameMode gameMode;



    public MinesweeperGame() {

        inputHandler = new InputHandler();

        // get the level of difficulty from the user
        String difficultyLevel = inputHandler.getDifficultyLevel();

        // set the level of diffculty
        gameMode = GameMode.DifficultyLevel(difficultyLevel);

        // create an instance of the board with this level of difficulty
        int numRows = gameMode.getNumRows();
        int numCols = gameMode.getNumCols();
        int numberOfMines = gameMode.getNumberOfMines();
        board = new Board(numRows, numCols, numberOfMines);
    }


    // this method starts the minesweeper game
    public void startGame() {

        int remainingFlags = board.getNumberOfMines();  // keeping track of number of places flags by the user
        while (!gameIsOver && !gameIsWon) {

            board.printBoard( remainingFlags);
            System.out.println();

            int numRows = board.getNumRows();
            int numCols = board.getNumCols();

            UserInput userInput = inputHandler.getUserInput(numRows, numCols); // this method validates the user input

            // once input is validated
            String movetype = userInput.moveType;
            int row = userInput.row;
            int col = userInput.col;

            // Place mines considering the first move
            placeMinesIfFirstMove(row, col);

            // if the input is to reveal a tile
            if(movetype.equals("r")) {

                Tile selectedTile = board.getSelectedTile(row,col); // this retrieves the selected tile

                if (selectedTile.isMine()) {
                    gameIsOver = true;
                    selectedTile.setRevealed(true);
                    System.out.println("************** Game Over ************");
                    // a method here for reveling all mines is the game is over
                    board.revealAllMines(); // Reveal all mines that are not flagged already by the player
                    board.printBoard(remainingFlags);
                    // ask if the player want to play again.
                } else {
                    board.revealTile(row, col); // reveal the tile and its neighbors if it is an empty tile
                }
                gameIsWon = board.checkIfPlayerHasWon(); // Check for a win after a player has revealed a tile
                if (gameIsWon) {
                    System.out.println("********  Congratulations, You Have Won The Game!  ********");
                    board.printBoard(remainingFlags); // reveal the mines since the game is won
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

   // checking if it is the first move when a player makes a move
    private void placeMinesIfFirstMove(int row, int col) {
        if (isTheFirstMove){ // if this is the first move, then placer the mines on board
            board.placeMinesOnBoard(row, col); // Adjust placeMines to avoid the first move
            isTheFirstMove = false;
        }
    }

    private void resetGame() {
        //board.setFirstMoveMade(false);
        // Other reset logic...
    }

}

