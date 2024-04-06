import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    public void revealTileReturnsFalseForInvalidRevealingInput(){
        Board board = new Board(2,2);
        final var actual = board.revealTile(12, 12);
        Assertions.assertFalse(actual, "revealTile should return false for an invalid revealing input");
    }
    @Test
    public void revealTileReturnsFalseIfRevealedInputIsAMine(){
        Board board = new Board(2,2);
        final var actual = board.revealTile(1, 1);
        Assertions.assertTrue(actual, "revealTile should return true if the revealing input is a mine");
    }


}
