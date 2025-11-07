package Model;

import java.sql.Connection;
import Model.Entities.Player;
import Model.Rooms.Room;
import Model.Items.Item;
import Model.Entities.Monster;
import Model.Puzzle;

public class Map {

    // === UML Fields ===
    private Room playerLocation;
    private Player player;
    private java.util.Map<String, Room> rooms;
    private java.util.Map<String, Item> items;
    private java.util.Map<String, Monster> monsters;
    private java.util.Map<String, Puzzle> puzzles;

    // === Constructor ===
    public Map() {
        // You can initialize your maps later when loading data
    }

    // === Getter Methods (from UML) ===
    public java.util.Map<String, Room> getRooms() {
        return rooms;
    }

    public java.util.Map<String, Item> getItems() {
        return items;
    }

    public java.util.Map<String, Monster> getMonsters() {
        return monsters;
    }

    public java.util.Map<String, Puzzle> getPuzzles() {
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