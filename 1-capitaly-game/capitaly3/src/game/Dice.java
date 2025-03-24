package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Dice {
    private final List<Integer> rolls;
    private int currentRollIndex;

    /**
     * Constructor Dice is responsible for initiation of reading list of rolls from the file.
     * @param filePath
     * @throws IOException
     */
    public Dice(String filePath) throws IOException {
        this.rolls = new ArrayList<>();
        this.currentRollIndex = 0;
        readDiceRollsFromFile(filePath);
    }

    /**
     * Method readDiceRollsFromFile is responsible for reading list of rolls from the file.
     * @param filePath
     * @throws IOException
     */
    private void readDiceRollsFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            rolls.add(Integer.parseInt(line.trim()));
        }
        br.close();
    }

    /**
     * Method roll is responsible for getting values from the list.
     * @return
     */
    public int roll() {
        if (currentRollIndex < rolls.size()) {
            return rolls.get(currentRollIndex++);
        } else {
            currentRollIndex = 0;
            return rolls.get(currentRollIndex++);
        }
    }
}
