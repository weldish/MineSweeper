
// A class to encapsulate a user input.
public class UserInput {
    public final String moveType;
    public final int row;
    public final int col;

    public UserInput(String moveType, int row, int col) {
        this.moveType = moveType;
        this.row = row;
        this.col = col;
    }
}