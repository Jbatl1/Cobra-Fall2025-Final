package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {

    // --- Initialize all progress tables ---
    public static void initialize() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            //ADD TABLE:
            //crash ship, traveling ship, tool belt, (maybe add one for ech planet/room to keep track if items currently htere?),
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS player_progress (
                    id INTEGER PRIMARY KEY,
                    name TEXT,
                    health INTEGER,
                    attack INTEGER,
                    defense INTEGER,
                    location TEXT,
                    equipment TEXT,
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS solved_puzzles (
                    puzzle_id INTEGER PRIMARY KEY
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS visited_rooms (
                    room_id INTEGER PRIMARY KEY
                );
            """);


            stmt.execute("""
                CREATE TABLE IF NOT EXISTS defeated_monsters (
                    monster_id INTEGER PRIMARY KEY
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS upgraded_weapons (
                    weapon_id INTEGER PRIMARY KEY
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS inventory (
                    Item_Id INTEGER PRIMARY KEY,
                    location TEXT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS purchased_items (
                    Item_Id INTEGER PRIMARY KEY
                );
            """);



            System.out.println("✅ Game progress tables initialized.");

        } catch (SQLException e) {
            System.err.println("❌ Failed to initialize progress tables.");
            e.printStackTrace();
        }
    }

    // --- PLAYER PROGRESS ---
    public static void savePlayer(String name, int health, int attack, int defense, int level, String location) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO player_progress (id, name, health, attack, defense, location, equipment)"+
                             "VALUES (1, ?, ?, ?, ?, ?, ?, ) " +
                             "ON CONFLICT(id) DO UPDATE SET name=excluded.name, health=excluded.health, attack=excluded.attack, defense=excluded.defense, location=excluded.location, equipment=excluded.equipment;"
             )) {
            stmt.setString(1, name);
            stmt.setInt(2, health);
            stmt.setInt(3, attack);
            stmt.setInt(4, defense);
            stmt.setString(5, location);
            stmt.setString(6, equipment);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayer() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM player_progress WHERE id=1")) {

            if (rs.next()) {
                String name = rs.getString("name");
                int health = rs.getInt("health");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                String location = rs.getString("location");
                String equipment = rs.getString("equipment");

                return new PlayerData(name, health, attack, defense, location, equipment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- GENERIC INSERT METHODS ---
    public static void markPuzzleSolved(int puzzleId) {
        executeInsert("INSERT OR IGNORE INTO solved_puzzles (puzzle_id) VALUES (?);", puzzleId);
    }

    public static void visitRoom(int roomId) {
        executeInsert("INSERT OR IGNORE INTO visited_rooms (room_id) VALUES (?);", roomId);
    }

    public static void collectArtifact(int artifactId) {
        executeInsert("INSERT OR IGNORE INTO owned_artifacts (artifact_id) VALUES (?);", artifactId);
    }

    public static void defeatMonster(int monsterId) {
        executeInsert("INSERT OR IGNORE INTO defeated_monsters (monster_id) VALUES (?);", monsterId);
    }

    public static void upgradeWeapon(int weaponId) {
        executeInsert("INSERT OR IGNORE INTO upgraded_weapons (weapon_id) VALUES (?);", weaponId);
    }

    public static void addItemToInventory(int itemId, String location) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT OR REPLACE INTO inventory (item_id, location) VALUES (?, ?);"
             )) {
            stmt.setInt(1, itemId);
            stmt.setString(2, location);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void purchaseItem(int itemId) {
        executeInsert("INSERT OR IGNORE INTO purchased_items (item_id) VALUES (?);", itemId);
    }

    // --- GENERIC HELPER ---
    private static void executeInsert(String sql, int id) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- LOAD LISTS ---
    public static List<Integer> getSolvedPuzzles() { return getIdList("solved_puzzles", "puzzle_id"); }
    public static List<Integer> getVisitedRooms() { return getIdList("visited_rooms", "room_id"); }
    public static List<Integer> getOwnedArtifacts() { return getIdList("owned_artifacts", "artifact_id"); }
    public static List<Integer> getDefeatedMonsters() { return getIdList("defeated_monsters", "monster_id"); }
    public static List<Integer> getUpgradedWeapons() { return getIdList("upgraded_weapons", "weapon_id"); }
    public static List<Integer> getInventory(String location) {
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT item_id FROM inventory WHERE location = ?;")) {
            stmt.setString(1, location);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- HELPER TO GET ID LIST ---
    private static List<Integer> getIdList(String table, String column) {
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT " + column + " FROM " + table + ";")) {
            while (rs.next()) {
                list.add(rs.getInt(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
