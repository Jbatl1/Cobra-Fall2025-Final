package Model.Rooms;

import java.sql.*;
import java.util.*;
import Model.Items.Item;
import Model.Entities.Monster;
import Model.DatabaseConnection; // using your existing connection class

public class Room {

    // ==============================
    // Fields
    // ==============================
    private String id;
    private String name;
    private String description;
    private Set<String> connections; // IDs of connected rooms
    private ArrayList<Item> items;
    private ArrayList<Monster> monsters;

    // ==============================
    // Constructor
    // ==============================
    public Room(String id) {
        this.id = id;
        this.connections = new HashSet<>();
        this.items = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    // ==============================
    // Database Loading
    // ==============================
    public void loadFromDatabase() {
        try (Connection conn = DatabaseConnection.connect()) {

            if (conn == null) {
                System.err.println("❌ Could not connect to database.");
                return;
            }

            // --- Load room info ---
            String sql = "SELECT name, description FROM rooms WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.name = rs.getString("name");
                this.description = rs.getString("description");
            } else {
                System.err.println("⚠️ No room found with ID: " + id);
            }

            rs.close();
            stmt.close();

            // --- Load connections ---
            sql = "SELECT connected_room_id FROM connections WHERE room_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                connections.add(rs.getString("connected_room_id"));
            }
            rs.close();
            stmt.close();

            // --- Load items in the room ---
            sql = "SELECT id, name, type FROM items WHERE room_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name")); // Adjust constructor as needed
                items.add(item);
            }
            rs.close();
            stmt.close();

            // --- Load monsters in the room ---
            sql = "SELECT * FROM monsters WHERE room_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Monster m = new Monster(
                        rs.getString("id"),
                        id,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("health"),
                        rs.getInt("attackPower"),
                        rs.getInt("defense"),
                        rs.getBoolean("isBoss"),
                        null, // optional drop item for now
                        this
                );
                monsters.add(m);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error loading room from DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==============================
    // Getters
    // ==============================
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Set<String> getConnections() { return connections; }
    public ArrayList<Item> getItems() { return items; }
    public ArrayList<Monster> getMonsters() { return monsters; }

    // ==============================
    // Room Logic
    // ==============================
    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    public Room getExit(String direction) {
        // TODO: Map directions to connected rooms (if using a direction table)
        return null;
    }

    public void displayRoomInfo() {
        System.out.println("=== ROOM INFO ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Connections: " + connections);
        System.out.println("Items: " + (items.isEmpty() ? "None" : items.size()));
        System.out.println("Monsters: " + (monsters.isEmpty() ? "None" : monsters.size()));
        System.out.println("=================");
    }
}