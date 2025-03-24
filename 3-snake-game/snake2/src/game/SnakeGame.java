package game;

import game.controller.GameController;
import game.model.GameLogic;
import game.view.GameConfig;
import game.view.GamePanel;

import javax.swing.*;

/**
 * The main class for the Snake Game application.
 */
public class SnakeGame extends JFrame {

    public SnakeGame() {
        GameConfig config = new GameConfig(600, 600, 25);

        GameLogic gameLogic = new GameLogic(config);

        GamePanel gamePanel = new GamePanel(null);

        GameController controller = new GameController(gameLogic, gamePanel);
        gamePanel.setController(controller);

        add(gamePanel);

        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}
