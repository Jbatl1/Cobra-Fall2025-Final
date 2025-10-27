package Model;
import java.sql.Connection;

public class Map {
    public static void main(String[] args) {

        Connection conn = DatabaseConnection.connect();

        if (conn != null) {
            System.out.println(" Game can now use the database!");
        } else {
            System.out.println(" Could not connect to database.");
        }

        // Close the connection when done
        DatabaseConnection.close();
    }

}
