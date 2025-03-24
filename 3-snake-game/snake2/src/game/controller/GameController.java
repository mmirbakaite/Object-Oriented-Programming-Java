package game.controller;

import game.model.GameLogic;
import game.view.GamePanel;

import java.awt.event.KeyEvent;

/**
 * The GameController class manages interactions between game logic and UI.
 */
public class GameController {
    private final GameLogic gameLogic;
    private final GamePanel gamePanel;

    public GameController(GameLogic gameLogic, GamePanel gamePanel) {
        this.gameLogic = gameLogic;
        this.gamePanel = gamePanel;
    }


    public void startGame() {
        gameLogic.startGame();
        gamePanel.repaint();
    }

    public void restartGame() {
        gameLogic.startGame();
        gamePanel.repaint();
    }

    public void handleKeyPress(KeyEvent e) {
        char keyChar = e.getKeyChar();
        char direction = gameLogic.getDirection();

        switch (keyChar) {
            case 'a' -> {
                if (direction != 'R') gameLogic.setDirection('L');
            }
            case 'd' -> {
                if (direction != 'L') gameLogic.setDirection('R');
            }
            case 'w' -> {
                if (direction != 'D') gameLogic.setDirection('U');
            }
            case 's' -> {
                if (direction != 'U') gameLogic.setDirection('D');
            }
            default -> {
                gamePanel.gameOver();
                return;
            }
        }
        updateGame();
    }

    public void updateGame() {
        gameLogic.move();
        if (gameLogic.checkApple()) {
            gamePanel.repaint();
        }
        if (gameLogic.checkCollisions()) {
            gamePanel.gameOver();
        }
        gamePanel.repaint();
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}
