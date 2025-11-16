package Model;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Items.*;
import Model.Puzzles.Puzzle;
import Model.Rooms.CrashSite;
import Model.Rooms.LandingSite;
import Model.Rooms.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class LoadRooms { //Anita Philip

    HashMap<String, Room> roomsInfo = new HashMap<>();
    HashMap<String, Puzzle> puzzles = new HashMap<>();
    HashMap<String, Item> items = new HashMap<>();
    HashMap<String, Player> player1 = new HashMap<>();
    HashMap<String, Integer> inventory = new HashMap<>();
    HashMap<String, Monster> monsters = new HashMap<>();

    HashMap<String, Puzzle> allPuzzles = new HashMap<>();
    HashMap<String, Puzzle> boundaryPuzzles = new HashMap<>();
    HashMap<String, Puzzle> normalPuzzles = new HashMap<>();
    HashMap<String, Puzzle> lootPuzzles = new HashMap<>();

    private static final List<String> ROOM_TABLES = Arrays.asList( //Anita Philip
            "Volcanic_Inferno",
            "Survivors_World",
            "Sky_Isles",
            "Jungle_Ruins",
            "Frozen_Waste",
            "Echo_Deserts",
            "Crystal_Canyons",
            "Celestial_Citadel"
    );
    public LoadRooms(HashMap<String, Room> roomsInfo, HashMap<String, Puzzle> puzzles, HashMap<String, Item> items,
                     HashMap<String, Player> player1, HashMap<String, Integer> inventory, HashMap<String, Monster> monsters) { //Anita Philip
        this.roomsInfo = roomsInfo;
        this.puzzles = puzzles;
        this.items = items;
        this.player1 = player1;
        this.inventory = inventory;
        this.monsters = monsters;
    }


    public void loadRooms() { //Anita Philip
        try (Connection conn = DatabaseConnection.connect()) {

            loadAllRooms(conn);
            loadAllPuzzles(conn);  // load puzzles first
            loadAllItems(conn);
            assignPuzzlesToRooms(); // Assign after items are loaded
            loadAllMonsters(conn);
            assignMonstersToRooms(); // <-- Add this call here
            setupRoomExits();
            loadAllShops(conn);


        } catch (Exception e) {
            System.out.println("Room loading failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -----------------------------------ROOM LOADING ------------------------------------------------------------------------


    private void loadAllRooms(Connection conn) throws Exception { //Anita Philip
        for (String table : ROOM_TABLES) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + ";");
            while (rs.next()) {

                if (rs.getString("Type") != null && (rs.getString("Type").equalsIgnoreCase("Landing Site"))) {
                    LandingSite ls = new LandingSite(
                            rs.getString("RoomID"),
                            rs.getString("RoomName"),
                            rs.getString("Description"),
                            rs.getString("Type"),
                            rs.getString("NorthNavigation"),
                            rs.getString("EastNavigation"),
                            rs.getString("SouthNavigation"),
                            rs.getString("WestNavigation"),
                            rs.getBoolean("isVisited"),
                            rs.getBoolean("isRaider"),
                            rs.getBoolean("isShop")
                    );
                    roomsInfo.put(ls.getRoomID(), ls);
                }
                else if (rs.getString("Type") != null && rs.getString("Type").equalsIgnoreCase("Crash Site")) {
                    CrashSite cs = new CrashSite(
                            rs.getString("RoomID"),
                            rs.getString("RoomName"),
                            rs.getString("Description"),
                            rs.getString("Type"),
                            rs.getString("NorthNavigation"),
                            rs.getString("EastNavigation"),
                            rs.getString("SouthNavigation"),
                            rs.getString("WestNavigation"),
                            rs.getBoolean("isVisited"),
                            rs.getBoolean("isRaider"),
                            rs.getBoolean("isShop")
                    );
                    roomsInfo.put(cs.getRoomID(), cs);
                }
                else {
                    Room room = new Room(
                            rs.getString("RoomID"),
                            rs.getString("RoomName"),
                            rs.getString("Description"),
                            rs.getString("Type"),
                            rs.getString("NorthNavigation"),
                            rs.getString("EastNavigation"),
                            rs.getString("SouthNavigation"),
                            rs.getString("WestNavigation"),
                            rs.getBoolean("isVisited"),
                            rs.getBoolean("isRaider"),
                            rs.getBoolean("isShop")
                    );
                    roomsInfo.put(room.getRoomID(), room);
                }
            }
            for (Room room : roomsInfo.values()) {
                if (room instanceof LandingSite) {
                    ((LandingSite) room).loadLandingSiteConnections(roomsInfo);
                }
            }
        }
    }

    // ------------------ ITEM LOADING ------------------
    private void loadAllItems(Connection conn) throws Exception { //Anita Philip
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Artifacts;");
        while (rs.next()) {
            Item item = null;
            switch (rs.getString("ItemType")) {
                case "Weapon":
                    item = new Weapon(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getInt("ItemDamage"),
                            rs.getInt("ItemDurability"),
                            rs.getString("ItemDescription"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("Cost")
                    );
                    break;
                case "Consumable":
                    item = new Consumable(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getString("ItemDescription"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("ItemRestoreHP"),
                            rs.getInt("Cost")
                    );
                    break;
                case "Artifact":
                    item = new Artifact(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getString("ItemDescription"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("Cost")
                    );
                    break;
                case "Key Item":
                    item = new Key(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getString("ItemDescription"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("Cost")
                    );
                    break;
                case "Material":
                    item = new Material(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getString("ItemDescription"),
                            rs.getInt("ItemUpgrade"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("Cost")
                    );
                    break;
                default:
                    item = new Item(
                            rs.getString("ItemID"),
                            rs.getString("RoomID"),
                            rs.getString("ItemName"),
                            rs.getString("ItemType"),
                            rs.getString("ItemRarity"),
                            rs.getString("ItemDescription"),
                            rs.getString("PuzzleID"),
                            rs.getInt("Quantity"),
                            rs.getInt("Cost")
                    );
            }


            String puzzleIDs = rs.getString("PuzzleID");
            if (puzzleIDs != null && !puzzleIDs.isEmpty()) {
                item.setPuzzleIDs(Arrays.asList(puzzleIDs.split(",")));
            }

            item.setQuantity(rs.getInt("Quantity") == 0 ? 1 : rs.getInt("Quantity"));
            items.put(item.getItemID(), item);

            // Assign item to its room
            Room room = roomsInfo.get(item.getRoomID());
            if (room != null) {
                room.getRoomItems().add(item);
            }
        }
    }

    // ------------------ PUZZLE LOADING ------------------
    private void loadAllPuzzles(Connection conn) throws Exception { //Anita Philip
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Puzzles;");
        while (rs.next()) {
            String rewardItemID = rs.getString("Reward");
            Item rewardItem = items.get(rewardItemID);

            Puzzle puzzle = new Puzzle(
                    rs.getString("PuzzleID"),
                    rs.getString("Question"),
                    rs.getInt("Attempts"),
                    rs.getString("Solution"),
                    rewardItem,
                    rs.getString("RoomID"),
                    rs.getString("Type")
            );

            allPuzzles.put(puzzle.getPuzzleID(), puzzle);

            // Sort by type
            switch (puzzle.getType().toLowerCase()) {
                case "boundary": boundaryPuzzles.put(puzzle.getPuzzleID(), puzzle); break;
                case "normal": normalPuzzles.put(puzzle.getPuzzleID(), puzzle); break;
                case "loot": lootPuzzles.put(puzzle.getPuzzleID(), puzzle); break;
                default: System.out.println("WARNING: Unknown puzzle type: " + puzzle.getType());
            }
        }
    }
    // ------------------ ASSIGN PUZZLES TO ROOMS ------------------
    private void assignPuzzlesToRooms() { //Anita Philip
        for (Puzzle p : allPuzzles.values()) {
            Room room = roomsInfo.get(p.getRoomID());
            if (room == null) {
                System.out.println("WARNING: Puzzle '" + p.getPuzzleID() + "' has invalid RoomID: " + p.getRoomID());
                continue;
            }

            switch (p.getType().toLowerCase()) {
                case "boundary":
                    room.setRoomPuzzle(p);
                    break;
                case "normal":
                    room.getPuzzlePresent().add(p);
                    break;
                case "loot":
                    for (Item item : room.getRoomItems()) {
                        if (item.getPuzzleIDs() != null && item.getPuzzleIDs().contains(p.getPuzzleID())) {
                            item.addLootPuzzle(p); // Optional helper method in Item
                        }
                    }
                    break;
                default:
                    System.out.println("Unknown puzzle type for puzzle " + p.getPuzzleID());
            }
        }
    }
    // ------------------ MONSTER LOADING ------------------
    private void loadAllMonsters(Connection conn) throws Exception { //Anita Philip
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Monsters;");
        while (rs.next()) {
            String rewardItemID = rs.getString("Drops");
            Item dropItem = items.get(rewardItemID);
            Room room = roomsInfo.get(rs.getString("RoomID"));

            Monster monster = new Monster(
                    rs.getString("MonsterName"),
                    rs.getInt("HP"),
                    rs.getInt("ATK"),
                    rs.getInt("DEF"),
                    rs.getString("MonsterID"),
                    rs.getString("Ability_Effect"),
                    rs.getString("RoomID"),
                    rs.getBoolean("isBoss"),
                    dropItem,
                    rs.getBoolean("isRaider"),
                    room
            );

            monsters.put(monster.getMonsterID(), monster);
        }
    }

    private void assignMonstersToRooms() {
        for (Monster m : monsters.values()) {
            Room r = roomsInfo.get(m.getRoomID());
            if (r != null) {
                r.addMonster(m);  // <-- Use your new method
            }
        }
    }

    // ------------------ SHOPS ------------------

    private void loadAllShops(Connection conn) throws Exception {
        // Map of item -> cost for all shops
        HashMap<Item, Integer> universalShopStock = new HashMap<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Shop;");

        while (rs.next()) {
            String itemID = rs.getString("Item_ID");
            int cost = rs.getInt("Cost");
            Item stockItem = items.get(itemID);

            if (stockItem != null) {
                universalShopStock.put(stockItem, cost);
            } else {
                System.out.println("WARNING: Shop table references invalid item: " + itemID);
            }
        }

        if (universalShopStock.isEmpty()) {
            System.out.println("WARNING: Shop table is empty — no shop items loaded!");
            return;
        }


        for (Room room : roomsInfo.values()) {
            if (room.isShop()) {
                room.setStock(new HashMap<>(universalShopStock));
            }
        }
    }

    // ------------------ ROOM EXITS ------------------
    private void setupRoomExits() {
        for (Room room : roomsInfo.values()) {

            String northID = room.getNorthNavigation();
            String eastID = room.getEastNavigation();
            String southID = room.getSouthNavigation();
            String westID = room.getWestNavigation();

            if (northID != null && roomsInfo.containsKey(northID))
                room.addRoomExit("NORTH", roomsInfo.get(northID));

            if (eastID != null && roomsInfo.containsKey(eastID))
                room.addRoomExit("EAST", roomsInfo.get(eastID));

            if (southID != null && roomsInfo.containsKey(southID))
                room.addRoomExit("SOUTH", roomsInfo.get(southID));

            if (westID != null && roomsInfo.containsKey(westID))
                room.addRoomExit("WEST", roomsInfo.get(westID));
        }
    }

    public HashMap<String, Room> getRoomsInfo() {
        return roomsInfo;
    }
    public HashMap<String, Puzzle> getPuzzles() {return puzzles;}
    public HashMap<String, Monster> getMonsters() {return monsters;}
    public HashMap<String, Puzzle> getAllPuzzles() {return allPuzzles;}
    public HashMap<String, Puzzle> getBoundaryPuzzles() {return boundaryPuzzles;}
    public HashMap<String, Puzzle> getNormalPuzzles() {return normalPuzzles;}
    public HashMap<String, Puzzle> getLootPuzzles() {return lootPuzzles;}
    public HashMap<String, Item> getItems() {
        return items;
    }
    public HashMap<String, Player> getPlayer1() {
        return player1;
    }
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
    public Room getStartRoom() {
        return roomsInfo.get("SW1");
    }
    //public HashMap<String, Monster> getMonstersInfo() {return monsters;}
}
