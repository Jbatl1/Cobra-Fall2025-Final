package Model;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Items.Item;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;
import Model.Rooms.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Map;

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

   /* public LoadRooms(HashMap<String, Room> roomsInfo, HashMap<String, Puzzle> puzzles, HashMap<String, Item> items, HashMap<String, Player> player1, HashMap<String, Integer> inventory, HashMap<String, Monster> monsters) {
        this.roomsInfo = roomsInfo;
        this.puzzles = puzzles;
        this.items = items;
        this.player1 = player1;
        this.inventory = inventory;
        this.monsters = monsters;


    }*/


    private static final List<String> ROOM_TABLES = Arrays.asList( //Anita Philip
            "Volcanic_Inferno",
            "Survivors_World",
            "Sky_Isles",
            "Jungle_Ruins",
            "Frozen_Waste",
            "Echo_Deserts",
            "Crystal_Canyons"
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
            loadAllItems(conn);
            loadAllPuzzles(conn);
            assignPuzzlesToRooms(); // Assign after items are loaded
            loadAllMonsters(conn);
            setupRoomExits();

            loadAllShops(conn);   // <-- ADD THIS LINE

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
    }

    // ------------------ ITEM LOADING ------------------
    private void loadAllItems(Connection conn) throws Exception { //Anita Philip
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Artifacts;");
        while (rs.next()) {
            Item item = new Item(
                    rs.getString("ItemID"),
                    rs.getString("RoomID"),
                    rs.getString("ItemName"),
                    rs.getString("ItemType"),
                    rs.getString("ItemRarity"),
                    rs.getInt("ItemDamage"),
                    rs.getInt("ItemDurability"),
                    rs.getInt("ItemRestoreHP"),
                    rs.getString("ItemDescription"),
                    rs.getInt("ItemUpgrade"),
                    rs.getString("PuzzleID"),
                    rs.getInt("Quantity")
            );

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
                    dropItem,
                    rs.getBoolean("isBoss"),
                    rs.getBoolean("isRaider"),
                    room
            );

            monsters.put(monster.getMonsterID(), monster);
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
    // ------------------ SHOP LOADING ------------------
    // ------------------ SHOP LOADING (MATCHES YOUR CONSTRUCTOR) ------------------
    private void loadAllShops(Connection conn) throws Exception {

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Shops;");

        // Store multiple items per shop
        HashMap<String, ArrayList<Item>> shopStocks = new HashMap<>();

        while (rs.next()) {
            String roomID = rs.getString("RoomID");

            // Convert Shop row into actual Item object
            String itemID = rs.getString("Item_ID");
            Item stockItem = items.get(itemID);

            if (stockItem == null) {
                System.out.println("WARNING: Shop row references invalid item: " + itemID);
                continue;
            }

            // Add item to the room's stock list
            shopStocks.computeIfAbsent(roomID, k -> new ArrayList<>()).add(stockItem);
        }

        // ---------- Now create the actual Shop objects ----------
        for (Map.Entry<String, ArrayList<Item>> entry : shopStocks.entrySet()) {

            String roomID = entry.getKey();
            ArrayList<Item> stock = entry.getValue();

            Room baseRoom = roomsInfo.get(roomID);

            if (baseRoom == null) {
                System.out.println("WARNING: Shop references missing RoomID: " + roomID);
                continue;
            }

            // Replace the Room object with a Shop object
            Shop shop = new Shop(
                    baseRoom.getRoomID(),
                    baseRoom.getRoomName(),
                    baseRoom.getRoomDescription(),
                    baseRoom.getRoomType(),
                    baseRoom.getNorthNavigation(),
                    baseRoom.getEastNavigation(),
                    baseRoom.getSouthNavigation(),
                    baseRoom.getWestNavigation(),
                    baseRoom.isRoomVisited(),
                    baseRoom.isRaider(),
                    baseRoom.isShop(),

                    // Stock list
                    stock,

                    // Only the first row's item info stored at shop-level (optional but required by your constructor)
                    stock.get(0).getItemID(),
                    stock.get(0).getItemName(),
                    stock.get(0).getItemType(),
                    stock.get(0).getItemRarity(),
                    stock.get(0).getCost() //


            );

            // Replace room with Shop
            roomsInfo.put(roomID, shop);
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
}
