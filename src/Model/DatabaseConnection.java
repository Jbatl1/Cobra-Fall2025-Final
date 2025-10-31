package Model;

import java.sql.*;
import java.io.File;


public class DatabaseConnection {

    private static final String DB_FILE_NAME = "NEXUS-ESCAPE-DATABASE.db";
    private static Connection connection = null;

    // --- Public method to get connection ---
    public static Connection connect() {
        if (connection != null) {
            return connection; // reuse the same connection
        }

        try {
            // Get the project directory dynamically
            String projectDir = System.getProperty("user.dir");
            File dbFile = findDatabase(new File(projectDir), DB_FILE_NAME);

            if (dbFile == null || !dbFile.exists()) {
                System.err.println("Database not found anywhere inside: " + projectDir);
                return null;
            }

            // Build the SQLite URL
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

            // Connect to SQLite database
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database at: " + dbFile.getAbsolutePath());
            return connection;

        } catch (SQLException e) {
            System.err.println("SQLite connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    // --- Recursive search for the database file ---
    private static File findDatabase(File dir, String fileName) {
        File[] files = dir.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isDirectory()) {
                File found = findDatabase(file, fileName);
                if (found != null) return found;
            } else if (file.getName().equalsIgnoreCase(fileName)) {
                return file;
            }
        }
        return null;
    }

    // --- Optional: safely close connection when exiting the game ---
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                // System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}