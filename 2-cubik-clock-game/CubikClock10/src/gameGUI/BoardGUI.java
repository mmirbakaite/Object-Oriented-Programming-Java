package gameGUI;

import gameLogic.Board;
import gameLogic.Field;

import javax.swing.*;
import java.awt.*;

/**
 * The BoardGUI class represents the graphical user interface for a game board.
 */
public class BoardGUI {
    private Board board;
    private final JPanel boardPanel;
    private JButton[][] buttons;

    /**
     * Constructs a new BoardGUI instance for a game board.
     *
     * @param board
     */
    public BoardGUI(Board board) {
        this.board = board;
        int boardSize = board.getBoardSize();
        buttons = new JButton[boardSize][boardSize];

        boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        initializeBoardButtons(gbc);
        addExtraButtons(gbc);
    }

    /**
     * Initializes the buttons representing the fields on the board and adds them to the panel.
     *
     * @param gbc The GridBagConstraints used for layout management.
     */
    private void initializeBoardButtons(GridBagConstraints gbc) {
        int boardSize = board.getBoardSize();
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                Field field = board.get(i, j);
                JButton button = createBoardButton(field);
                buttons[i][j] = button;
                gbc.gridx = j * 2;
                gbc.gridy = i * 2;
                boardPanel.add(button, gbc);
            }
        }
    }

    /**
     * Creates a JButton representing a field on the game board.
     *
     * @param field The Field object containing the data for this button.
     * @return A JButton configured with the field's properties.
     */
    private JButton createBoardButton(Field field) {
        JButton button = new JButton();
        button.setText(String.valueOf(field.getNumber()));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setEnabled(false);
        button.setPreferredSize(new Dimension(90, 90));
        button.setBackground(new Color(230, 230, 250));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // No MouseListener added here, so no color change on hover.
        return button;
    }

    /**
     * Adds extra control buttons to the board panel at specific positions.
     *
     * @param gbc The GridBagConstraints used for layout management.
     */
    private void addExtraButtons(GridBagConstraints gbc) {
        String[] extraButtonNames = {"Button 1", "Button 2", "Button 3", "Button 4"};
        int[][] positions = {
                {1, 1}, {1, 3}, {3, 1}, {3, 3}
        };

        for (int i = 0; i < 4; i++) {
            JButton extraButton = createExtraButton(extraButtonNames[i], i);
            int x = positions[i][0];
            int y = positions[i][1];
            gbc.gridx = y;
            gbc.gridy = x;
            boardPanel.add(extraButton, gbc);
        }
    }

    /**
     * Creates an extra control button with a specified label and action index.
     *
     * @param name The label to display on the button.
     * @param index The index used to determine which action to perform on button click.
     * @return A JButton configured with the specified properties.
     */
    private JButton createExtraButton(String name, int index) {
        JButton extraButton = new JButton(name);
        extraButton.setFont(new Font("Arial", Font.BOLD, 14));
        extraButton.setEnabled(true);
        extraButton.setPreferredSize(new Dimension(100, 50));
        extraButton.setBackground(new Color(255, 223, 186));
        extraButton.setForeground(Color.BLACK);
        extraButton.setFocusPainted(false);
        extraButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        extraButton.addActionListener(e -> handleExtraButtonClick(index));
        return extraButton;
    }

    /**
     * Handles the click action for the extra control buttons.
     * Increments the surrounding fields' clocks and updates the board display.
     * Checks if all clocks are set to 12 and shows a completion message if so.
     *
     * @param index The index of the clicked extra button.
     */
    private void handleExtraButtonClick(int index) {
        board.incrementSurroundingClocks(index);
        board.incrementClickCount();
        updateButtonTexts();
        if (board.allClocksAreMaxed()) {
            showCompletionMessage();
        }
    }

    /**
     * Updates the text displayed on each button to reflect the current state of the board.
     */
    private void updateButtonTexts() {
        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                buttons[i][j].setText(String.valueOf(board.get(i, j).getNumber()));
            }
        }
    }

    /**
     * Displays a completion message when all clocks are set to 12,
     * and provides options to restart the game or exit.
     */
    private void showCompletionMessage() {
        Object[] options = {"OK", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "All clocks are set to 12! It took " + board.getClickCount() + " clicks.",
                "Completion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            board.refreshGame();
            updateButtonTexts();
        } else {
            System.exit(0);
        }
    }

    /**
     * Returns the main panel containing the board layout.
     *
     * @return The JPanel representing the board's graphical layout.
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }
}
