package Model.Rooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Database.DatabaseConnection;
import Model.Entities.Monster;
import Model.Items.Item;
import Model.Puzzle;
import Model.Entities.Player;

public class Room {

    // ==============================
    // Fields
    // ==============================
    protected String id;
    protected String name;
    protected String description;
    protected List<Monster> monsters;
    protected List<Item> items;
    protected Puzzle puzzle;
    protected boolean visited;
    private boolean isRestRoom;

    // ==============================
    // Constructor
    // ==============================
    public Room(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.monsters = new ArrayList<>();
        this.items = new ArrayList<>();
        this.visited = false;
    }

    // ==============================
    // Load Room Data from Database
    // ==============================
    public static Room loadRoomById(String roomId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Room room = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM Rooms WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                room = new Room(roomId, name, description);

                // Load related data
                room.loadMonsters(conn);
                room.loadItems(conn);
                room.loadPuzzle(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResources(conn, stmt, rs);
        }

        return room;
    }

    // ==============================
    // Load Monsters, Items, Puzzle
    // ==============================
    private void loadMonsters(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Monsters WHERE roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Monster monster = new Monster(
                            rs.getString("monsterID"),
                            rs.getString("roomID"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("health"),
                            rs.getInt("attackPower"),
                            rs.getInt("defense"),
                            rs.getBoolean("isBoss"),
                            null, // dropItem will be assigned later if needed
                            this
                    );
                    monsters.add(monster);
                }
            }
        }
    }

    private void loadItems(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Items WHERE roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                            rs.getString("itemID"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getBoolean("isConsumable")
                    );
                    items.add(item);
                }
            }
        }
    }

    private void loadPuzzle(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Puzzles WHERE roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    puzzle = new Puzzle(
                            rs.getString("puzzleID"),
                            rs.getString("question"),
                            rs.getString("solution"),
                            null,
                            rs.getInt("attempts")
                    );
                }
            }
        }
    }

    // ==============================
    // Player Interaction Logic
    // ==============================
    public String enter(Player player) {
        visited = true;
        player.setCurrRoom(this);

        StringBuilder sb = new StringBuilder();
        sb.append("You have entered ").append(name).append(".\n")
                .append(description).append("\n");

        if (!monsters.isEmpty()) {
            sb.append("There are ").append(monsters.size()).append(" monster(s) here.\n");
        }
        if (puzzle != null) {
            sb.append("There is a puzzle waiting for you.\n");
        }
        if (!items.isEmpty()) {
            sb.append("You see some items lying around.\n");
        }

        return sb.toString();
    }

    // ==============================
    // Getters
    // ==============================
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Monster> getMonsters() { return monsters; }
    public List<Item> getItems() { return items; }
    public Puzzle getPuzzle() { return puzzle; }
    public boolean isVisited() { return visited; }
    public boolean isRestRoom() { return isRestRoom; }

    // ==============================
    // String Representation
    // ==============================
    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", monsters=" + monsters.size() +
                ", items=" + items.size() +
                ", puzzle=" + (puzzle != null) +
                '}';
    }


    public int getMonsterByName(String s) { // Caleb
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getName().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }
}