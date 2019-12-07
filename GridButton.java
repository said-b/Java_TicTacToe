import javax.swing.*;

public class GridButton extends JButton {
    private int row;
    private int col;

    /**
     * Constructs a button for use in a tic-tac-toe game
     * @param x x coordinate corresponding to this button's representation in the game board
     * @param y y coordinate corresponding to this button's representation in the game board
     * @param s
     */
    public GridButton(int x, int y, String s){
        super(s);
        this.row =  x;
        this.col = y;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
