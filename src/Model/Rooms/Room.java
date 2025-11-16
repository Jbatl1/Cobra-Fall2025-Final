package Model.Rooms;

import java.util.ArrayList;
import java.util.List;

import Main.Main;
import Model.DatabaseConnection;
import Model.Entities.Monster;
import Model.Items.Item;
import Model.LoadRooms;
import Model.Puzzles.Puzzle;
import Model.Entities.Player;
import Model.Puzzles.BoundaryPuzzle;
import Model.Model;
import java.util.HashMap;
import java.util.Map;

//items --> roomItems
//getName() --> getItemName
public class Room {
    private String roomID;
    private String roomName;
    private String roomDescription;
    private String roomType;
    private String northNavigation;
    private String eastNavigation;
    private String southNavigation;
    private String westNavigation;
    private boolean roomVisited;
    private boolean isRaider;
    private boolean isShop;
    private Puzzle roomPuzzle;  // the puzzle in this room, can be null
    private HashMap<String, Room> exits;// stores exits (direction → roomNumber mapping)
    private ArrayList<Item> roomItems = new ArrayList<>(); // items present in this room
    private ArrayList<Puzzle> puzzlePresent = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<Monster>();
    private Map<Item, Integer> stock;


    // ==============================
    // Constructor
    // ==============================

    public Room(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomType = roomType;
        this.northNavigation = northNavigation;
        this.eastNavigation = eastNavigation;
        this.southNavigation = southNavigation;
        this.westNavigation = westNavigation;
        this.roomVisited = roomVisited;
        this.isRaider = isRaider;
        this.isShop = isShop;

        this.exits = new HashMap<>();  // <<< REQUIRED
    }
    public void setStock(Map<Item, Integer> stock) {
        this.stock = stock;
    }
    public Map<Item, Integer> getStock() {
        return stock;
    }
    public void setRoomPuzzle(Puzzle p){
        this.roomPuzzle = p;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getRoomType() {
        return roomType;
    }

    public Puzzle getRoomPuzzle() {return roomPuzzle;}

    public String getNorthNavigation() {
        return northNavigation;
    }

    public String getEastNavigation() {
        return eastNavigation;
    }

    public String getSouthNavigation() {
        return southNavigation;
    }

    public String getWestNavigation() {
        return westNavigation;
    }

    public boolean isRoomVisited() {
        return roomVisited;
    }






    public ArrayList<Item> getRoomItems() {
        return roomItems;
    } //fix

    public boolean isRestRoom(){
        if (this.roomType.equalsIgnoreCase("Rest")){
            return true;
        }
        return false;
    }

    public Puzzle getBoundaryPuzzleInDirection(String direction, HashMap<String, Room> roomsMap) {
        if (direction == null || roomsMap == null) return null;

        Room targetRoomID = exits.get(direction.toUpperCase());
        if (targetRoomID == null) return null;

        Room targetRoom = roomsMap.get(targetRoomID);
        if (targetRoom == null) return null;

        return targetRoom.getRoomPuzzle();  // could be null
    }


    // ==============================
    // Player Interaction Logic
    // ==============================


        public HashMap<String, Room> getExits () {
            return exits;
        }

             public void addRoomExit(String direction, Room room) {
        if (room != null) {
            exits.put(direction.toUpperCase(), room);
        }
        }

        public boolean isRaider () {
            return isRaider;
        }

        public boolean isShop () {
            return isShop;
        }

        public void addItem (Item item){
            roomItems.add(item);
        }

        public Item removeItem (String itemName){
            for (Item i : roomItems) {
                if (i.getItemName().equalsIgnoreCase(itemName)) {
                    roomItems.remove(i);
                    return i;
                }
            }
            return null;
        }

    public String getMonsters1(Room room) {

        for (Monster m : Main.M.getMonsters().values()) { // monsters should return
            if (m.getRoomID().equalsIgnoreCase(room.getRoomID())){
                return m.getName();
            }
        }
        return null;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster m) {
        if (m == null) return;

        // Avoid duplicates
        if (!monsters.contains(m)) {
            monsters.add(m);

            // Set the monster's roomID to this room
           // m.setRoomID(this.roomID);
        }

    }

    public ArrayList<Puzzle> getPuzzlePresent() {
        return puzzlePresent;
    }

    public List<String> getMonsterNames() {
        List<String> names = new ArrayList<>();
        for (Monster m : Main.M.getMonsters().values()) {
            if (this.roomID.equals(m.getRoomID())) {
                names.add(m.getName());
            }
        }
        return names;
    }

    public List<String> getPuzzleNames() {
        List<String> puzzleNames = new ArrayList<>();

        for (Puzzle p : Main.M.getAllPuzzles().values()) { // <--- use getAllPuzzles()
            if (this.roomID.equals(p.getRoomID())) {
                if (p.getType().equalsIgnoreCase("Normal") || p.getType().equalsIgnoreCase("Loot")) {
                    puzzleNames.add(p.getPuzzleQuestion());
                }
            }
        }
        return puzzleNames;
    }

      /*  public List<String> getItemPresent() {
        List<String> itemNames = new ArrayList<>();

        for (Item i : Main.M.getItems().values()) {
            if (this.roomID.equals(i.getRoomID())) {
                itemNames.add(i.getItemName());
            }
            else if (i.)

        }
        return itemNames;
    }*/

    public List<String> getItemPresent() {
        List<String> itemNames = new ArrayList<>();

        // Get player inventory safely
        Player currentPlayer = Main.M.getPlayer1().values().stream().findFirst().orElse(null);
        List<String> inventoryItemNames = new ArrayList<>();
        if (currentPlayer != null) {
            for (Item invItem : currentPlayer.getInventory()) {
                inventoryItemNames.add(invItem.getItemName());
            }
        }

        // Only check items actually **currently in this room**
        for (Item i : this.getRoomItems()) {
            if (!inventoryItemNames.contains(i.getItemName())) {
                itemNames.add(i.getItemName());
            }
        }

        return itemNames;
    }

    public int getMonsterByName(String name) {
        if (name == null || monsters == null) return 0;

        for (Monster m : monsters) { // monsters should return
            if (m.getName().equalsIgnoreCase(name)) {
                return 1;   // yes monster was found
            }
        }

        return -1; // not found
    }



    public String monsterInRoom() {

        for (Monster m : monsters) { // monsters should return
            return m.getName();
        }
        return null;
    }

    public void removeMonster(Monster monster) {
        if (monster == null || monsters == null) return;
        monsters.remove(monster);
    }

}





