import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameUI {
    /**
     * Sets up the starting screen of the game
     * @param frame the Jpanel container this screen will be initialized to
     */
    public static void startScreen(JPanel frame){
        frame.setPreferredSize(new Dimension(500,500));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextArea textArea = new JTextArea();
        textArea.setBackground(frame.getBackground());
        textArea.setText("Tic-Tac-Toe");
        textArea.setFont(new Font("Arial", Font.PLAIN, 40));
        textArea.setEditable(false);
        topPanel.add(textArea);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startGame = new JButton("Player vs. Player");
        JButton startGame2 = new JButton("Player vs. CPU");
        centerPanel.add(startGame);
        centerPanel.add(startGame2);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchScreens(frame,false,true);

            }
        });
        startGame2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchScreens(frame,false,false);
            }
        });
        JPanel content = new JPanel(new BorderLayout(8,8));
        content.add(topPanel, BorderLayout.NORTH);
        content.add(centerPanel, BorderLayout.CENTER);
        //set layout and add all components
        frame.add(content);
//        frame.add(textArea);
//        frame.add(startGame);
        //set window to exit on close, pack components together and make the window visible
        frame.validate();
        frame.setVisible(true);
    }

    /**
     * Sets up the game window for a game
     * @param frame the Jpanel container this screen will be initialized to
     * @param game the game object associated with the game running in this window
     */
    public static void gameScreen(JPanel frame, Game game){
        frame.setPreferredSize(new Dimension(500,500));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextArea winnerText = new JTextArea();
        winnerText.setBackground(frame.getBackground());
        winnerText.setFont(new Font("Arial", Font.PLAIN, 20));
        winnerText.setText("Player 1: " + String.valueOf(game.getPlayer1().getScore()) + "\n\n" +
                "Player 2: " + String.valueOf(game.getPlayer2().getScore()) + "\n\n");
        winnerText.setEditable(false);


        JTextArea textArea = new JTextArea();
        textArea.setBackground(frame.getBackground());
        textArea.setText("Waiting for Player 1");
        textArea.setFont(new Font("Arial", Font.PLAIN, 40));
        textArea.setEditable(false);
        topPanel.add(textArea);
        JPanel boardPanel = new JPanel(new GridLayout(3,1,0,0));
//        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JPanel midRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JPanel botRow = new JPanel(new FlowLayout(FlowLayout.CENTER));;
        ArrayList<GridButton> buttons = new ArrayList<>();

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JButton button : buttons){
                    button.setEnabled(true);
                    button.setText("");
                    game.startNewGame();
                }
                textArea.setText("Waiting for Player 1");
                playAgain.setVisible(false);
            }
        });

        for(int i = 0; i < 9; i++){
            buttons.add(new GridButton(i/3, i%3, " "));
            boardPanel.add(buttons.get(i));
        }
        for(GridButton button : buttons){
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.setCell(button.getRow(),button.getCol(), game.turnPlayer().getSymbol());
                    button.setText(String.valueOf(game.turnPlayer().getSymbol()));
                    if(game.isWon()){
                        winnerText.setText("Player 1: " + String.valueOf(game.getPlayer1().getScore()) + "\n\n" +
                                "Player 2: " + String.valueOf(game.getPlayer2().getScore()));
                        for(GridButton b: buttons){
                            b.setEnabled(false);
                        }
                        playAgain.setVisible(true);
                        textArea.setText(game.getWinner().getName() + " Wins!");
                        if(game.turnPlayer().getName().equals("Player 2")){
                            game.changeTurn();
                        }
                    }
                    else if(game.isOver()){
                        playAgain.setVisible(true);
                        textArea.setText("Tie");
                        if(game.turnPlayer().getName().equals("Player 2")){
                            game.changeTurn();
                        }
                    }
                    else {
                        game.changeTurn();
                        textArea.setText("Waiting for " + game.turnPlayer().getName());
                    }
                    button.setEnabled(false);
                    System.out.println(game);
                    if(!game.turnPlayer().isHuman()){
                        ArrayList<Integer> move = game.makePlay(game.turnPlayer());
                        try {
                            buttons.get((move.get(0) * 3) + move.get(1)).doClick();
                        }
                        catch(IndexOutOfBoundsException ee){
                            ee.printStackTrace();
                        }

                    }
                }
            });
            button.setFont(new Font("Arial", Font.PLAIN, 40));
        }

//        for(int i = 0; i < 3; i++){
//            topRow.add(buttons.get(i));
//        }
//        for(int i = 3; i < 6; i++){
//            midRow.add(buttons.get(i));
//        }
//        for(int i = 6; i < 9; i++){
//            botRow.add(buttons.get(i));
//        }
//        boardPanel.add(topRow);
//        boardPanel.add(midRow);
//        boardPanel.add(botRow);

//        startGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
        JPanel content = new JPanel(new BorderLayout(8,8));
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(winnerText, BorderLayout.NORTH);
        playAgain.setPreferredSize(new Dimension(100, 50));
        playAgain.setVisible(false);
        rightPanel.add(playAgain, BorderLayout.CENTER);
        //winnerText.add(playAgain);
        boardPanel.setPreferredSize(new Dimension(300,300));
        content.add(topPanel, BorderLayout.NORTH);
        content.add(boardPanel, BorderLayout.CENTER);
        content.add(rightPanel, BorderLayout.EAST);
        //set layout and add all components
        frame.add(content);
//        frame.add(textArea);
//        frame.add(startGame);
        frame.validate();
        frame.setVisible(true);

    }

    /**
     * Switches between the starting screen and the game screen
     * @param panel is the Jpanel container the screen will be initialized to
     * @param b true for start screen, false for game screen
     * @param h if b is false, this is used as the argument to construct a Game object
     */
    public static void switchScreens(JPanel panel, boolean b, boolean h){
        //panel.setVisible(false);
        panel.removeAll();
        if(b){
            startScreen(panel);
        }
        else{
            gameScreen(panel, new Game(h));
        }

    }

    /**
     * Sets up the menu for the windoww
     * @param frame the JFrame container this menu will be initialized to
     * @param panel is the Jpanel container this menu will be initialized to
     */
    public static void setupMenu(JFrame frame, JPanel panel){
        //menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu();
        JMenu nGame = new JMenu("New Game");
        JMenuItem rules = new JMenuItem("Rules");
        JMenuItem pvp = new JMenuItem("Player vs. Player");
        pvp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                gameScreen(panel,new Game(true));
            }
        });
        JMenuItem pvc = new JMenuItem("Player vs. CPU");
        pvc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                gameScreen(panel,new Game(false));
            }
        });
        JMenu help = new JMenu("Help");
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Tic-tac-toe"));
                }
                catch(IOException | URISyntaxException ee){
                    ee.printStackTrace();
                }
            }
        });
        //add menu items
        nGame.add(pvp);
        nGame.add(pvc);
        help.add(rules);
        menu.add(nGame);
        menu.add(help);
        menuBar.add(nGame);
        menuBar.add(help);
        //frame.add(menuBar);
        frame.setJMenuBar(menuBar);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Tic-tac-toe");
        frame.setPreferredSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        setupMenu(frame, mainPanel);
        startScreen(mainPanel);
        //gameScreen(mainPanel, new Game());
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        //Game g = new Game();
        //System.out.println(g);


    }
}
