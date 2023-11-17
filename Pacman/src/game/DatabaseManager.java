package game;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DatabaseManager {
    private PGSimpleDataSource dataSource;

    public DatabaseManager() {
        this.dataSource = new PGSimpleDataSource();
        this.dataSource.setServerName("ec2-3-232-218-211.compute-1.amazonaws.com");
        this.dataSource.setDatabaseName("d57pblt23lgs71");
        this.dataSource.setUser("luzyzvsjnmrsdo");
        this.dataSource.setPortNumber(5432);
        this.dataSource.setPassword("fcc49cb27a58333f0793daeafc59a6d9cd9cd5f5765e0b12bcf46c401227918e");

    }

    /* 
    public DatabaseManager(String host, String database, String user, int port, String password) {
        this.dataSource = new PGSimpleDataSource();
        this.dataSource.setServerName(host);
        this.dataSource.setDatabaseName(database);
        this.dataSource.setUser(user);
        this.dataSource.setPortNumber(port);
        this.dataSource.setPassword(password);
    }
    */

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void queryAndPrintData(String tableName) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL server.");

            // Example query
            String sql = "SELECT * FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Execute the query
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

                            // Print the data
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

    private int getMaxRank(Connection connection) throws SQLException {
        // Get the maximum rank from the leaderboard table
        String maxRankSql = "SELECT MAX(rank) FROM leaderboard";
        try (PreparedStatement maxRankStatement = connection.prepareStatement(maxRankSql)) {
            try (ResultSet resultSet = maxRankStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1); // Return the maximum rank
                }
            }
        }
        return 1; // Default to 0 if there are no rows in the table
    }


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
