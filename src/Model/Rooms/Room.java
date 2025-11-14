package Model.Rooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Entities.Monster;
import Model.Items.Item;
import Model.Puzzles.Puzzle;
import Model.Entities.Player;
import Model.Puzzles.BoundaryPuzzle;

import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, String> exits;// stores exits (direction → roomNumber mapping)
    private ArrayList<Item> roomItems = new ArrayList<>(); // items present in this room
    private ArrayList<Puzzle> puzzlePresent = new ArrayList<>();

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

    public ArrayList<Puzzle> getPuzzlePresent() {
        return puzzlePresent;
    }

    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }

    // ==============================
    // Player Interaction Logic
    // ==============================


        public HashMap<String, String> getExits () {
            return exits;
        }

        public void addRoomExit (String direction, String roomID){
            exits.put(direction, roomID);
        }

        public boolean isRaider () {
            return isRaider;
        }

        public boolean isShop () {
            return isShop;
        }

        public ArrayList<Item> getRoomItems () {
            return roomItems;
        }

        public List<Monster> getMonsters () {
            return monsters;
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

        public Room getExit (String direction){
            // TODO: Map directions to connected rooms (if using a direction table)
            return null;
        }
    }



}

