package game.view;

import game.controller.*;
import game.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * The GamePanel class is responsible for rendering the game's visual elements and handling user input.
 */
public class GamePanel extends JPanel {

    /**
     * The GameController instance responsible for managing game logic and handling input.
     */
    private GameController controller;

    /**
     * Tracks the elapsed time in seconds since the game started.
     */
    private int elapsedTimeInSeconds;

    /**
     * A Swing Timer to update the elapsed time.
     */
    private Timer timer;

    /**
     * Constructs a GamePanel with the specified GameController.
     *
     * @param controller the GameController to handle game logic and input
     */
    public GamePanel(GameController controller) {
        this.controller = controller;
        this.elapsedTimeInSeconds = 0; // Initialize elapsed time
        initializePanel();
        startTimer();
        if (controller != null) {
            controller.startGame();
        }
    }

    /**
     * Sets up the panel's dimensions, background, and key listener.
     */
    private void initializePanel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        requestFocusInWindow();
    }

    /**
     * Starts the timer to count seconds.
     */
    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTimeInSeconds++;
                repaint();
            }
        });
        timer.start();
    }

    /**
     * Sets the GameController for this panel and starts the game.
     *
     * @param controller the GameController to handle game logic and input
     */
    public void setController(GameController controller) {
        this.controller = controller;
        controller.startGame();
    }

    /**
     * Overrides the paintComponent method to render the game elements.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (controller != null) {
            draw(g);
        }
    }

    /**
     * Draws the game elements, including the player, obstacles, apple, score, and timer.
     *
     * @param g the Graphics object used for rendering
     */
    private void draw(Graphics g) {
        GameLogic gameLogic = controller.getGameLogic();

        if (gameLogic.isRunning()) {
            drawObstacles(g, gameLogic);
            drawApple(g, gameLogic);
            drawPlayer(g, gameLogic);
            drawScoreAndTimer(g, gameLogic);
        } else {
            gameOver();
        }
    }

    /**
     * Draws the obstacles in the game.
     *
     * @param g         the Graphics object used for rendering
     * @param gameLogic the GameLogic instance containing the game state
     */
    private void drawObstacles(Graphics g, GameLogic gameLogic) {
        g.setColor(Color.GRAY);
        for (Point obstacle : gameLogic.getObstacles()) {
            g.fillRect(obstacle.x * 25, obstacle.y * 25, 25, 25);
        }
    }

    /**
     * Draws the apple in the game.
     *
     * @param g         the Graphics object used for rendering
     * @param gameLogic the GameLogic instance containing the game state
     */
    private void drawApple(Graphics g, GameLogic gameLogic) {
        g.setColor(Color.RED);
        g.fillOval(gameLogic.getAppleX(), gameLogic.getAppleY(), 25, 25);
    }

    /**
     * Draws the player's body parts on the screen.
     *
     * @param g         the Graphics object used for rendering
     * @param gameLogic the GameLogic instance containing the game state
     */
    private void drawPlayer(Graphics g, GameLogic gameLogic) {
        for (int i = 0; i < gameLogic.getBodyParts(); i++) {
            if (i == 0) {
                g.setColor(Color.GREEN); // Head
            } else {
                g.setColor(new Color(45, 180, 0)); // Body
            }
            g.fillRect(gameLogic.getX()[i], gameLogic.getY()[i], 25, 25);
        }
    }

    /**
     * Draws the score and timer on the screen.
     *
     * @param g         the Graphics object used for rendering
     * @param gameLogic the GameLogic instance containing the game state
     */
    private void drawScoreAndTimer(Graphics g, GameLogic gameLogic) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Score: " + gameLogic.getApplesEaten(), 10, g.getFont().getSize());

        g.drawString("Time: " + elapsedTimeInSeconds + " s", 400, g.getFont().getSize());
    }

    /**
     * Handles the game over scenario by displaying the leaderboard and restarting options.
     */
    public void gameOver() {
        timer.stop();
        String playerName = JOptionPane.showInputDialog(this, "Enter Your Name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Unknown Player";
        }

        saveAndDisplayLeaderboard(playerName);

        int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart?", "Restart Game", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            elapsedTimeInSeconds = 0; // Reset the timer
            timer.start(); // Restart the timer
            controller.restartGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * Saves the player's score to the leaderboard and displays the top scores.
     *
     * @param playerName the name of the player
     */
    private void saveAndDisplayLeaderboard(String playerName) {
        GameLogic gameLogic = controller.getGameLogic();
        gameLogic.saveScoreToDatabase(playerName);

        List<String> topScores = gameLogic.getTopScoresFromDatabase();
        StringBuilder scoresDisplay = new StringBuilder("Top Scores:\n");
        for (String score : topScores) {
            scoresDisplay.append(score).append("\n");
        }
        JOptionPane.showMessageDialog(this, scoresDisplay.toString(), "Top Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * A private inner class to handle keyboard input for controlling the game.
     */
    private class MyKeyAdapter extends KeyAdapter {
        /**
         * Invoked when a key is pressed.
         *
         * @param e the KeyEvent describing the key press
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (controller != null) {
                controller.handleKeyPress(e);
            }
        }
    }
}
