package game;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the PostgreSQL database connection and provides methods for interacting with the database.
 * 
 * @author Spencer Goles
*/
public class DatabaseManager {
    private PGSimpleDataSource dataSource;

    /**
     * Constructs a new DatabaseManager and initialize the data source with PostgreSQL connection details.
    */
    public DatabaseManager() {
        this.dataSource = new PGSimpleDataSource();

        //Replace with the correct information
        this.dataSource.setServerName("SERVER_NAME");
        this.dataSource.setDatabaseName("DATABASE_NAME");
        this.dataSource.setUser("USER");
        this.dataSource.setPortNumber(5432);
        this.dataSource.setPassword("PASSWORD");
    }

    /**
     * Connection to the PostgreSQL database.
     *
     * @return A connection to the PostgreSQL database.
     * @throws SQLException if a database access error occurs.
    */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the  database connection.
     *
     * @param connection The connection to be closed.
     * @throws SQLException if a database access error occurs.
    */
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Executes a query on the specified table and prints the results.
     *
     * @param tableName The name of the table to query.
    */
    public void queryAndPrintData(String tableName) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            String sql = "SELECT * FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Get metadata about the result set (columns, types, etc.)
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // Process the results
                    while (resultSet.next()) {
                        // Print each column and its value
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = resultSet.getObject(i);

                            System.out.println(columnName + ": " + columnValue);
                        }
                        System.out.println(); // Add a line break between rows
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    /**
     * Adds a new score to the leaderboard table.
     *
     * @param score The score to be added.
    */
    public void addScore(int score) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            // Get the current maximum rank in the leaderboard
            int maxRank = getMaxRank(connection);

            // Insert a new row with the next rank and the provided score
            String insertSql = "INSERT INTO leaderboard (rank, score) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setInt(1, maxRank + 1); // Increment the rank
                insertStatement.setInt(2, score);

                // Execute the insert statement
                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Score added successfully: " + score);
                } else {
                    System.out.println("Failed to add score.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the maximum rank from the leaderboard table.
     *
     * @param connection The database connection.
     * @return The maximum rank in the leaderboard table.
     * @throws SQLException if a database access error occurs.
    */
    private int getMaxRank(Connection connection) throws SQLException {
        // Get the maximum rank from the leaderboard table
        String maxRankSql = "SELECT MAX(rank) FROM leaderboard";
        try (PreparedStatement maxRankStatement = connection.prepareStatement(maxRankSql)) {
            try (ResultSet resultSet = maxRankStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 1;
    }

    /**
     * Deletes the score for the specified rank from the leaderboard table.
     *
     * @param rank The rank for which the score should be deleted.
    */
    public void deleteScore(int rank) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            // Delete the score for the specified rank
            String deleteSql = "DELETE FROM leaderboard WHERE rank = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, rank);

                // Execute the delete statement
                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Score for rank " + rank + " deleted successfully.");
                } else {
                    System.out.println("No score found for rank " + rank + ". No deletion performed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all scores from the leaderboard table.
    */
    public void deleteAllScores() {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            // Delete all scores from the "leaderboard" table
            String deleteAllSql = "DELETE FROM leaderboard";
            try (PreparedStatement deleteAllStatement = connection.prepareStatement(deleteAllSql)) {
                // Execute the delete statement
                int rowsAffected = deleteAllStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("All scores deleted successfully.");
                } else {
                    System.out.println("No scores found. No deletion performed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top three scores from the leaderboard table.
     *
     * @return A map containing the top three scores, where the key is the rank and the value is the score.
    */
    public Map<Integer, Integer> getTopThreeScores() {
        Map<Integer, Integer> topThreeScores = new HashMap<>();

        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            // Retrieve the first three rows from the "leaderboard" table, ordered by score in descending order and then by rank in ascending order
            String topThreeSql = "SELECT * FROM leaderboard ORDER BY score DESC, rank ASC LIMIT 3";
            try (PreparedStatement topThreeStatement = connection.prepareStatement(topThreeSql)) {
                try (ResultSet resultSet = topThreeStatement.executeQuery()) {
                    // Process the results
                    int count = 0;
                    while (resultSet.next() && count < 3) {
                        // Retrieve data from the result set
                        int rank = count + 1;
                        int score = resultSet.getInt("score");

                        // Put the data in the map
                        topThreeScores.put(rank, score);
                        count++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topThreeScores;
    }
}