package Model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Map {
    public static void main(String[] args) {

        try(Connection conn = DatabaseConnection.connect()){

            if (conn != null) {
                System.out.println(" Game can now use the database!");
            } else {
                System.out.println(" Could not connect to database.");
            }

            //Creates a SQL statement object used to send SQL queries to the database.
            Statement stmt = conn.createStatement();

          /*  String RoomInfoQuery = "SELECT * FROM Volcanic_Inferno;";
            ResultSet VIP = stmt.executeQuery(RoomInfoQuery);*/

        }catch(SQLException e){
            System.out.println(e);
        }

        // Close the connection when done
        DatabaseConnection.close();
    }

}
