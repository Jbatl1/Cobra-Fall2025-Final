package Model;

import java.sql.Connection;
import java.util.Map;
import Model.Entities.Player;  // assuming Player is in Model.Entities
import Model.Entities.Room;
import Model.Entities.Item;
import Model.Entities.Monster;
import Model.Entities.Puzzle;

public class Map {

    // === UML Fields ===
    private Room playerLocation;
    private Player player;
    private Map<String, Room> rooms;
    private Map<String, Item> items;
    private Map<String, Monster> monsters;
    private Map<String, Puzzle> puzzles;

    // === Constructor ===
    public Map() {
        // You can initialize your maps later when loading data
    }

    // === Getter Methods (from UML) ===
    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Map<String, Monster> getMonsters() {
        return monsters;
    }

    public Map<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    // === Optional: Database Test ===
    public static void main(String[] args) {

        Connection conn = DatabaseConnection.connect();

        if (conn != null) {
            System.out.println("Game can now use the database!");
        } else {
            System.out.println("Could not connect to database.");
        }

        // Close the connection when done
        DatabaseConnection.close();
    }
}