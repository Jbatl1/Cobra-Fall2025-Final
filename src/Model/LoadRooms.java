package Model;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Items.Item;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoadRooms {

    HashMap<String, Room> roomsInfo = new HashMap<>();
    HashMap<String, Puzzle> puzzles = new HashMap<>();
    HashMap<String, Item> items = new HashMap<>();
    HashMap<String, Player> player1 = new HashMap<>();
    HashMap<String, Integer> inventory = new HashMap<>();
    HashMap<String, Monster> monsters = new HashMap<>();

    public LoadRooms(HashMap<String, Room> roomsInfo, HashMap<String, Puzzle> puzzles, HashMap<String, Item> items, HashMap<String, Player> player1, HashMap<String, Integer> inventory, HashMap<String, Monster> monsters) {
        this.roomsInfo = roomsInfo;
        this.puzzles = puzzles;
        this.items = items;
        this.player1 = player1;
        this.inventory = inventory;
        this.monsters = monsters;
    }

    public void loadRooms() {

        try(Connection conn = DatabaseConnection.connect()){

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Volcanic_Inferno;");

            while(rs.next()) {

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

            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Survivors_World;");

            while(rs1.next()) {

                Room room = new Room(
                        rs1.getString("RoomID"),
                        rs1.getString("RoomName"),
                        rs1.getString("Description"),
                        rs1.getString("Type"),
                        rs1.getString("NorthNavigation"),
                        rs1.getString("EastNavigation"),
                        rs1.getString("SouthNavigation"),
                        rs1.getString("WestNavigation"),
                        rs1.getBoolean("isVisited"),
                        rs1.getBoolean("isRaider"),
                        rs1.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Sky_Isles;");

            while(rs2.next()) {

                Room room = new Room(
                        rs2.getString("RoomID"),
                        rs2.getString("RoomName"),
                        rs2.getString("Description"),
                        rs2.getString("Type"),
                        rs2.getString("NorthNavigation"),
                        rs2.getString("EastNavigation"),
                        rs2.getString("SouthNavigation"),
                        rs2.getString("WestNavigation"),
                        rs2.getBoolean("isVisited"),
                        rs2.getBoolean("isRaider"),
                        rs2.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }
            Statement stmt3 = conn.createStatement();
            ResultSet rs3 = stmt3.executeQuery("SELECT * FROM Jungle_Ruins;");

            while(rs3.next()) {

                Room room = new Room(
                        rs3.getString("RoomID"),
                        rs3.getString("RoomName"),
                        rs3.getString("Description"),
                        rs3.getString("Type"),
                        rs3.getString("NorthNavigation"),
                        rs3.getString("EastNavigation"),
                        rs3.getString("SouthNavigation"),
                        rs3.getString("WestNavigation"),
                        rs3.getBoolean("isVisited"),
                        rs3.getBoolean("isRaider"),
                        rs3.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt4 = conn.createStatement();
            ResultSet rs4 = stmt4.executeQuery("SELECT * FROM Frozen_Waste;");

            while(rs4.next()) {

                Room room = new Room(
                        rs4.getString("RoomID"),
                        rs4.getString("RoomName"),
                        rs4.getString("Description"),
                        rs4.getString("Type"),
                        rs4.getString("NorthNavigation"),
                        rs4.getString("EastNavigation"),
                        rs4.getString("SouthNavigation"),
                        rs4.getString("WestNavigation"),
                        rs4.getBoolean("isVisited"),
                        rs4.getBoolean("isRaider"),
                        rs4.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt5 = conn.createStatement();
            ResultSet rs5 = stmt5.executeQuery("SELECT * FROM Echo_Deserts;");

            while(rs5.next()) {

                Room room = new Room(
                        rs5.getString("RoomID"),
                        rs5.getString("RoomName"),
                        rs5.getString("Description"),
                        rs5.getString("Type"),
                        rs5.getString("NorthNavigation"),
                        rs5.getString("EastNavigation"),
                        rs5.getString("SouthNavigation"),
                        rs5.getString("WestNavigation"),
                        rs5.getBoolean("isVisited"),
                        rs5.getBoolean("isRaider"),
                        rs5.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt6 = conn.createStatement();
            ResultSet rs6 = stmt6.executeQuery("SELECT * FROM Crystal_Canyons;");

            while(rs6.next()) {

                Room room = new Room(
                        rs6.getString("RoomID"),
                        rs6.getString("RoomName"),
                        rs6.getString("Description"),
                        rs6.getString("Type"),
                        rs6.getString("NorthNavigation"),
                        rs6.getString("EastNavigation"),
                        rs6.getString("SouthNavigation"),
                        rs6.getString("WestNavigation"),
                        rs6.getBoolean("isVisited"),
                        rs6.getBoolean("isRaider"),
                        rs6.getBoolean("isShop")
                );

                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt7 = conn.createStatement();
            ResultSet rs7 = stmt7.executeQuery("SELECT * FROM Crystal_Canyons;");

            while(rs7.next()) {

                Room room = new Room(
                        rs7.getString("RoomID"),
                        rs7.getString("RoomName"),
                        rs7.getString("Description"),
                        rs7.getString("Type"),
                        rs7.getString("NorthNavigation"),
                        rs7.getString("EastNavigation"),
                        rs7.getString("SouthNavigation"),
                        rs7.getString("WestNavigation"),
                        rs7.getBoolean("isVisited"),
                        rs7.getBoolean("isRaider"),
                        rs7.getBoolean("isShop")
                );
                roomsInfo.put(room.getRoomID(), room);
            }

            Statement stmt8 = conn.createStatement();
            ResultSet rs8 = stmt8.executeQuery("SELECT * FROM Puzzles;");



            while(rs8.next()) {

                String rewardItemID = rs8.getString("Reward");
                Item rewardItem = items.get(rewardItemID);

                Puzzle puzzle = new Puzzle(

                        rs8.getString("PuzzleID"),
                        rs8.getString("Question"),
                        rs8.getInt("Attempts"),
                        rs8.getString("Solution"),
                        rewardItem,
                        rs8.getString("RoomID"),
                        rs8.getString("Type")
                );

                puzzles.put(puzzle.getPuzzleID(), puzzle);
            }

            Statement stmt9 = conn.createStatement();
            ResultSet rs9 = stmt9.executeQuery("SELECT * FROM Monsters;");

            while(rs9.next()) {

                String rewardItemID = rs9.getString("Drops");
                Item dropItem = items.get(rewardItemID);

                String roomID = rs9.getString("RoomID");
                Room currentRoom = roomsInfo.get(roomID);

                Monster monster = new Monster(
                        rs9.getString("MonsterName"),
                        rs9.getInt("HP"),
                        rs9.getInt("ATK"),
                        rs9.getInt("DEF"),
                        rs9.getString("MonsterID"),
                        rs9.getString("Ability_Effect"),
                        dropItem,
                        rs9.getBoolean("isBoss"),
                        rs9.getBoolean("isRaider"),
                        currentRoom

                );

                monsters.put(monster.getMonsterID(), monster);
            }

            Statement stmt10 = conn.createStatement();
            ResultSet rs10 = stmt10.executeQuery("SELECT * FROM Artifacts;");
            while(rs10.next()) {

                Item item = new Item(
                        rs10.getString("ItemID"),
                        rs10.getString("RoomID"),
                        rs10.getString("ItemName"),
                        rs10.getString("ItemType"),
                        rs10.getString("ItemRarity"),
                        rs10.getInt("ItemDamage"),
                        rs10.getInt("ItemDurability"),
                        rs10.getInt("ItemRestoreHP"),
                        rs10.getString("ItemDescription"),
                        rs10.getInt("ItemUpgrade"),
                        rs10.getString("PuzzleID"),
                        rs10.getInt("Quantity")
                );

                //  handles multi-puzzle link and quantity ---
                String puzzleIDs = rs10.getString("PuzzleID"); // assumes your table has this column
                if (puzzleIDs != null && !puzzleIDs.isEmpty()) {
                    // split comma-separated PuzzleIDs into list
                    item.setPuzzleIDs(Arrays.asList(puzzleIDs.split(",")));
                }

                // optional: initialize quantity field if you add it to Item class
                item.setQuantity(rs10.getInt("Quantity") == 0 ? 1 : rs10.getInt("Quantity"));


                items.put(item.getItemID(), item);
            }

      for (Map.Entry<String, Room> entry : roomsInfo.entrySet()) {

                String key = entry.getKey();
                Room room = entry.getValue();

                room.addRoomExit("NORTH", room.getNorthNavigation());
                room.addRoomExit("EAST", room.getEastNavigation());
                room.addRoomExit("SOUTH", room.getSouthNavigation());
                room.addRoomExit("WEST", room.getWestNavigation());
            }


        } catch(Exception e){
            System.out.println("Room loading failed: " + e.getMessage());
        }
    }

    public HashMap<String, Room> getRoomsInfo() {
        return roomsInfo;
    }

    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public HashMap<String, Player> getPlayer1() {
        return player1;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}
