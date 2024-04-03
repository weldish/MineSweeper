import java.util.Scanner;

public class MinesweeperGame {
    private Board board;
    private boolean gameOver;

    public MinesweeperGame(int numRows, int numCols) {
        this.board = new Board(numRows, numCols);

    }

    public void startGame() {
        board.printBoard(false);
    }

    public static void main(String[] args) {
        MinesweeperGame game = new MinesweeperGame(10, 10);
        game.startGame();
    }
}