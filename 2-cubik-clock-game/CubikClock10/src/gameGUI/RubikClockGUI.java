package gameGUI;

import gameLogic.Board;

import javax.swing.*;
import java.awt.*;

/**
 * The RubikClockGUI class represents the graphical user interface for displaying
 * a Rubik's Clock board.
 */
public class RubikClockGUI {

    /**
     * Constructs a RubikClockGUI instance and initializes the main application window.
     * Sets up a 3x3 board and displays it in a JFrame.
     */
    public RubikClockGUI() {
        // Create a new 3x3 game board instance
        Board board = new Board(3);

        // Create a BoardGUI to represent the board visually
        BoardGUI boardGUI = new BoardGUI(board);

        // Set up the JFrame containing the game board
        JFrame frame = new JFrame("Rubik's Clock Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add the board panel to the center of the frame
        frame.add(boardGUI.getBoardPanel(), BorderLayout.CENTER);

        // Adjust frame settings and make it visible
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }
}
