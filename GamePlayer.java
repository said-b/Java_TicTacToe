/**
 *  An extension of the Player class for players of tic-tac-toe
 */
public class GamePlayer extends Player {
    private int score;
    private int playerNum;
    private boolean turn;
    private char symbol;
    private boolean human = true;

    /**
     * Constructs a player object
     * @param name is the name of the player
     * @param n is the player number (e.g. "player1, player2")
     * @param symbol is the symbol that is placed on the board when this player makes a move
     * @param turn set to true if this player will go first
     */
    GamePlayer(String name, int n, char symbol, boolean turn){
        super(name);
        this.playerNum = n;
        this.symbol = symbol;
        this.turn = turn;
    }

    /**
     *
     * @return this players score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score for this player
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return if it is this player's turn
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * Sets whether it is this player's turn
     * @param turn
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     *
     * @return this symbol used for this character's moves
     */
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     *
     * @return true if this player is human, false is player is CPU
     */
    public boolean isHuman() {
        return human;
    }

    /**
     * Sets whether player is human or not
     * @param human
     */
    public void setHuman(boolean human) {
        this.human = human;
    }
}
