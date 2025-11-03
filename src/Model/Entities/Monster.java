package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;
import Model.DatabaseConnection;
import java.sql.*;

public class Monster extends Entity {

    // ==============================
    // Fields
    // ==============================
    private String monsterID;
    private String roomID;
    private String description;
    private boolean isBoss;
    private Item dropItem;
    private int maxHealth;
    private Room currentRoom;

    // ==============================
    // Constructor (manual use)
    // ==============================
    public Monster(String monsterID, String roomID, String name, String description,
                   int health, int attackPower, int defense, boolean isBoss, Item dropItem, Room currentRoom) {

        super(name, health, attackPower);
        this.monsterID = monsterID;
        this.roomID = roomID;
        this.description = description;
        this.isBoss = isBoss;
        this.dropItem = dropItem;
        this.defense = defense;
        this.maxHealth = health;
        this.currentRoom = currentRoom;
    }

    // ==============================
    // Constructor (load from database)
    // ==============================
    public Monster(String monsterID) {
        super("Unknown", 0, 0); // placeholders until loaded
        this.monsterID = monsterID;
        loadFromDatabase(monsterID);
    }

    // ==============================
    // Database Loading
    // ==============================
    public void loadFromDatabase(String monsterID) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) {
                System.err.println("❌ Database connection failed when loading monster.");
                return;
            }

            String sql = "SELECT * FROM monsters WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, monsterID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.name = rs.getString("name");
                this.roomID = rs.getString("room_id");
                this.description = rs.getString("description");
                this.health = rs.getInt("health");
                this.attackPower = rs.getInt("attackPower");
                this.defense = rs.getInt("defense");
                this.isBoss = rs.getBoolean("isBoss");
                this.maxHealth = health;
            } else {
                System.err.println("⚠️ No monster found with ID: " + monsterID);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error loading monster from DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==============================
    // (Optional) Save Back to Database
    // ==============================
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn == null) return;

            String sql = """
                UPDATE monsters
                SET name = ?, description = ?, health = ?, attackPower = ?, defense = ?, isBoss = ?
                WHERE id = ?
                """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, health);
            stmt.setInt(4, attackPower);
            stmt.setInt(5, defense);
            stmt.setBoolean(6, isBoss);
            stmt.setString(7, monsterID);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error saving monster to DB: " + e.getMessage());
        }
    }

    // ==============================
    // Getters
    // ==============================
    public String getMonsterID() { return monsterID; }
    public String getRoomID() { return roomID; }
    public Room getCurrentRoom() { return currentRoom; }
    public String getDescription() { return description; }
    public boolean isBoss() { return isBoss; }
    public Item getDropItem() { return dropItem; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }
    public void setDropItem(Item dropItem) { this.dropItem = dropItem; }

    // ==============================
    // Display / Combat (same as before)
    // ==============================
    public void inspect() {
        System.out.println("=== Monster Information ===");
        System.out.println("Monster ID: " + monsterID);
        System.out.println("Room ID: " + roomID);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Attack Power: " + attackPower);
        System.out.println("Defense: " + defense);
        System.out.println("Boss: " + (isBoss ? "Yes" : "No"));
        System.out.println("============================");
    }

    // Combat + Rewards (same as before)
    // ...
}