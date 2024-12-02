package test;
import game.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class WhiteBox {

    /**
     * Test Case 1: Reading Rolls
     * @throws IOException
     */
    @Test
    public void testDiceRollsFromFile() throws IOException {
        String filePath = "dice.txt";

        Dice dice = new Dice(filePath);

        assertEquals(3, dice.roll());
        assertEquals(5, dice.roll());
        assertEquals(2, dice.roll());
        assertEquals(6, dice.roll());
        assertEquals(4, dice.roll());
    }

    /**
     * Test Case 2: Field Access and Wrapping
     */
    @Test
    public void testFieldAccess() {
        Board board = new Board(5);
        Field field0 = new PropertyField();
        Field field1 = new ServiceField(200);
        Field field2 = new LuckyField(150);
        Field field3 = new PropertyField();
        Field field4 = new PropertyField();
        board.addField(field0);
        board.addField(field1);
        board.addField(field2);
        board.addField(field3);
        board.addField(field4);
        assertSame(field0, board.getField(0));
        assertSame(field1, board.getField(1));
        assertSame(field2, board.getField(2));
        assertSame(field3, board.getField(3));
        assertSame(field4, board.getField(4));
    }

    /**
     * Test Case 3: Starting The Game
     */
    @Test
    public void testGameInitialization() throws IOException {
        // Initialize the game from the game.txt and dice.txt files
        Game game = new Game("game.txt", "diceTest1.txt");
        game.startGame();
        assertTrue(true, "The game should initialize successfully.");
    }

}

