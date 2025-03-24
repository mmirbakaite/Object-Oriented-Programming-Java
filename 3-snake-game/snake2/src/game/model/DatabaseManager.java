package game.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/snake2?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "progtech"; // Replace with your MySQL password

    /**
     * Save a player's score to the database.
     *
     * @param playerName The name of the player.
     * @param score      The score of the player.
     */
    public void saveScore(String playerName, int score) {
        String query = "INSERT INTO Highscores (player_name, score) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
            System.out.println("Score saved successfully!");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save score to database", e);
        }
    }

    /**
     * Fetch the top 10 scores from the database.
     *
     * @return A list of formatted top scores as strings.
     */
    public List<String> getTopScores() {
        String query = "SELECT player_name, score, timestamp FROM Highscores ORDER BY score DESC LIMIT 10";
        List<String> topScores = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String scoreEntry = String.format("%s - %d points (%s)",
                        resultSet.getString("player_name"),
                        resultSet.getInt("score"),
                        resultSet.getString("timestamp"));
                topScores.add(scoreEntry);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch scores from database", e);
        }

        return topScores;
    }

    /**
     * Test the database connection.
     */
    public void testConnection() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }
}
