package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {

    //  CREATE TABLES
    public static void initialize() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS player_progress (
                    save_id TEXT PRIMARY KEY,
                    health INTEGER,
                    attack INTEGER,
                    defense INTEGER,
                    location TEXT,
                    equipment TEXT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS solved_puzzles (
                    save_id TEXT,
                    PuzzleID TEXT,
                    PRIMARY KEY (save_id, PuzzleID)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS visited_rooms (
                    save_id TEXT,
                    RoomID TEXT,
                    PRIMARY KEY (save_id, RoomID)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS defeated_monsters (
                    save_id TEXT,
                    MonsterID TEXT,
                    PRIMARY KEY (save_id, MonsterID)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS upgraded_weapons (
                    save_id TEXT,
                    ItemID TEXT,
                    PRIMARY KEY (save_id, ItemID)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS inventory (
                    save_id TEXT,
                    ItemID TEXT,
                    location TEXT,
                    PRIMARY KEY (save_id, ItemID)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS purchased_items (
                    save_id TEXT,
                    ItemID TEXT,
                    PRIMARY KEY (save_id, ItemID)
                );
            """);

            System.out.println("Game progress tables initialized.");

        } catch (SQLException e) {
            System.err.println("Failed to initialize progress tables.");
            e.printStackTrace();
        }
    }


    // CHECK IF SAVE EXISTS
    public static boolean saveExists(String saveId) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM player_progress WHERE save_id = ?")) {

            stmt.setString(1, saveId);
            ResultSet rs = stmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // SAVE PLAYER DATA
    public static void savePlayer(String saveId, int health, int attack, int defense, String location, String equipment) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     """
                     INSERT INTO player_progress (save_id, health, attack, defense, location, equipment)
                     VALUES (?, ?, ?, ?, ?, ?)
                     ON CONFLICT(save_id) DO UPDATE SET
                         health = excluded.health,
                         attack = excluded.attack,
                         defense = excluded.defense,
                         location = excluded.location,
                         equipment = excluded.equipment;
                     """
             )) {

            stmt.setString(1, saveId);
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


    // LOAD PLAYER
    public static PlayerData loadPlayer(String saveId) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM player_progress WHERE save_id = ?")) {

            stmt.setString(1, saveId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int health = rs.getInt("health");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                String location = rs.getString("location");
                String equipment = rs.getString("equipment");

                return new PlayerData(health, attack, defense, location, equipment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // GENERIC INSERT FOR STRING IDs
    private static void insertForSave(String sql, String saveId, String id) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, saveId);
            stmt.setString(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // SAVE PUZZLES, ROOMS, MONSTERS, WEAPONS, ITEMS
    public static void markPuzzleSolved(String saveId, String puzzleId) {
        insertForSave("INSERT OR IGNORE INTO solved_puzzles (save_id, PuzzleID) VALUES (?, ?);",
                saveId, puzzleId);
    }

    public static void visitRoom(String saveId, String roomId) {
        insertForSave("INSERT OR IGNORE INTO visited_rooms (save_id, RoomID) VALUES (?, ?);",
                saveId, roomId);
    }

    public static void defeatMonster(String saveId, String monsterId) {
        insertForSave("INSERT OR IGNORE INTO defeated_monsters (save_id, MonsterID) VALUES (?, ?);",
                saveId, monsterId);
    }

    public static void upgradeWeapon(String saveId, String itemId) {
        insertForSave("INSERT OR IGNORE INTO upgraded_weapons (save_id, ItemID) VALUES (?, ?);",
                saveId, itemId);
    }

    public static void purchaseItem(String saveId, String itemId) {
        insertForSave("INSERT OR IGNORE INTO purchased_items (save_id, ItemID) VALUES (?, ?);",
                saveId, itemId);
    }


    // INVENTORY SAVING
    public static void addItemToInventory(String saveId, String itemId, String location) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT OR REPLACE INTO inventory (save_id, ItemID, location) VALUES (?, ?, ?);")) {

            stmt.setString(1, saveId);
            stmt.setString(2, itemId);
            stmt.setString(3, location);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
