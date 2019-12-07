import java.util.ArrayList;

/**
 * Class that handles the operations required for a tic-tac-toe game
 */
public class Game {
    /**
     *
     */
    private ArrayList<ArrayList<Character>> board;
    private GamePlayer player1, player2;
    private GamePlayer winner;

    /**
     * Constructs a game with a 3x3 board and 2 players
     * @param b determines whether the second player is human or not
     */
    public Game(boolean b){
        board = new ArrayList<>(3);
        this.emptyBoard();
        player1 = new GamePlayer("Player 1", 1, 'X', true);
        player2 = new GamePlayer("Player 2", 2, 'O', false);
        player2.setHuman(b);
    }

    /**
     * Clears the board
     */
    public void emptyBoard(){
        for(int i = 0; i < 3; i++){
            this.board.add(new ArrayList<>());
            for(int j = 0; j < 3; j++){
                this.board.get(i).add('.');
            }
        }
    }

    /**
     * Sets the value of a cell in the board. Treats the board as a coordinate grid with coordinates 0,0
     * representing the top-left cell
     * @param x is the y coordinate
     * @param y is the y coordinate
     * @param val is the value the cell is set to
     */
    public void setCell(int x, int y, char val){
        try {
            this.board.get(x).set(y, val);
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    /**
     * Clears the board
     */
    public void startNewGame(){
        this.board.clear();
        this.emptyBoard();
    }

    @Override
    public String toString() {
        String s = "";
        for(ArrayList<Character> row : board){
            for(Character c : row){
                s = s + c.toString() + " ";
            }
            s = s + "\n";
        }
        return s;
    }

    /**
     * Returns the turn player
     * @return is the Player object associated with the player that is currently choosing a move
     */
    public GamePlayer turnPlayer(){
        if(player1.isTurn()){
            return player1;
        }
        else{
            return player2;
        }
    }

    /**
     * Changes the turn player from player 1 to player 2 or vice-versa
     */
    public void changeTurn(){
        if(player1.isTurn()){
            player1.setTurn(false);
            player2.setTurn(true);
        }
        else{
            player1.setTurn(true);
            player2.setTurn(false);
        }
    }

    /**
     * Checks if the game has been won
     * @return true if the game has been won, else false
     */
    public boolean isWon(){
        //check rows
        for(ArrayList<Character> row: this.board){
            if(row.get(0).equals(row.get(1)) && row.get(1).equals(row.get(2))){
                if(row.get(0).equals('X')){
                    this.setWinner(player1);
                    return true;
                }
                else if(row.get(0).equals('O')){
                    this.setWinner(player2);
                    return true;
                }
            }
        }
        //check columns
        for(int i = 0; i < 3; i++){
            if(board.get(0).get(i).equals(board.get(1).get(i)) && board.get(1).get(i).equals(board.get(2).get(i))){
                if(board.get(0).get(i).equals('X')){
                    this.setWinner(player1);
                    return true;
                }
                else if(board.get(0).get(i).equals('O')){
                    this.setWinner(player2);
                    return true;
                }
            }
        }

        //check diagonals
        if(board.get(0).get(0).equals(board.get(1).get(1)) && board.get(1).get(1).equals(board.get(2).get(2))){
            if(board.get(0).get(0).equals('X')){
                this.setWinner(player1);
                return true;
            }
            else if(board.get(0).get(0).equals('O')){
                this.setWinner(player2);
                return true;
            }
        }
        else if(board.get(0).get(2).equals(board.get(1).get(1)) && board.get(1).get(1).equals(board.get(2).get(0))){
            if(board.get(0).get(2).equals('X')){
                this.setWinner(player1);
                return true;
            }
            else if(board.get(0).get(2).equals('O')){
                this.setWinner(player2);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if moves are still able to be played
     * @return true if no possible moves are available, else false
     */
    public boolean isOver(){
        for(ArrayList<Character> row: this.board){
            for(Character c: row){
                if(c.equals('.')){
                    return false;
                }
            }
        }
        return true;
    }

    public GamePlayer getPlayer1() {
        return player1;
    }

    public GamePlayer getPlayer2() {
        return player2;
    }

    public GamePlayer getWinner() {
        return winner;
    }

    public void setWinner(GamePlayer winner) {
        this.winner = winner;
        winner.setScore(winner.getScore() + 1);
    }

    /**
     * Sets the board for the game
     * @param b represents the new value the board will have
     */
    public void setBoard(ArrayList<ArrayList<Character>> b){
        this.board = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            this.board.add(new ArrayList<>());
            for(int j = 0; j < 3; j++){
                this.board.get(i).add(b.get(i).get(j));
            }
        }
    }

    /**
     * Evaluation function for a board state
     * @return  a higher value for a position that favors player 2, a lower value for a position that does not
     */
    public int evalBoard(){
        if(this.isWon()){
            if(this.getWinner().getName().equals("Player 1")){
                this.getWinner().setScore(this.getWinner().getScore() - 1);
                //this.setWinner(null);
                return -500;
            }
            else{
                this.getWinner().setScore(this.getWinner().getScore() - 1);
                //this.setWinner(null);
                return 1000;
            }
        }
        else{
            for(int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (this.board.get(i).get(j).equals('O')) {
                        //up down left right
                        if(i != 0){
                            if(this.board.get(i-1).get(j).equals('O')){
                                return 50;
                            }
                        }
                        if(i != 2){
                            if(this.board.get(i+1).get(j).equals('O')){
                                return 50;
                            }
                        }
                        if(j != 0){
                            if(this.board.get(i).get(j-1).equals('O')){
                                return 50;
                            }
                        }
                        if(j != 2){
                            if(this.board.get(i).get(j+1).equals('O')){
                                return 50;
                            }
                        }
                        //diagonals
                        if(i != 0 && j != 0){
                            if(this.board.get(i-1).get(j-1).equals('O')){
                                return 50;
                            }
                        }
                        if(i != 2 && j != 0){
                            if(this.board.get(i+1).get(j-1).equals('O')){
                                return 50;
                            }
                        }
                        if(i != 0 && j != 2){
                            if(this.board.get(i-1).get(j+1).equals('O')){
                                return 50;
                            }
                        }
                        if(i != 2 && j != 2){
                            if(this.board.get(i+1).get(j+1).equals('O')){
                                return 50;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Minimaxing function for move selection
     * @param depth is the depth of recursion
     * @param player player whose move will be minimaxxed
     * @return the score for a move, after recursive calls this will return the score for the best possible move
     */
    public int miniMax(int depth, GamePlayer player){
        int score = evalBoard();
        if(score == -500) {
            return score;
        }
        if(score == 1000){
            return score;
        }
        if(this.isOver()){
            return 0;
        }
        if(player.getName().equals("Player 2")){
            int best = -1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(this.board.get(i).get(j).equals('.')){
                        this.board.get(i).set(j, player.getSymbol());
                        best = Math.max(best, miniMax(depth+1, player1));
                        this.board.get(i).set(j, '.');
                    }
                }
            }
            return best;
        }
        else{
            int best = 1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(this.board.get(i).get(j).equals('.')){
                        this.board.get(i).set(j, player.getSymbol());
                        best = Math.min(best, miniMax(depth+1, player2));
                        this.board.get(i).set(j, '.');
                    }
                }
            }
            return best - depth;
        }
    }

    /**
     * Makes a play using minimaxxing function to select a move
     * @param player is the player for whom the move is made
     * @return a two value ArrayList containing the coordinates for the move to be made
     */
    public ArrayList<Integer> makePlay(GamePlayer player){
        int best = -1000;
        int temp = best;
        ArrayList<Integer> move = new ArrayList<>(2);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(this.board.get(i).get(j).equals('.')){
                    this.board.get(i).set(j, player.getSymbol());
                    best = Math.max(best, miniMax(0, player1));
                    if(best != temp){
                        move.add(i);
                        move.add(j);
                    }
                    temp = best;
                    this.board.get(i).set(j, '.');
                }
            }
        }
        return move;
    }
}
