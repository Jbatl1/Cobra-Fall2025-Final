package Model.Rooms;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Items.Item;

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
   // private Puzzle roomPuzzle;  // the puzzle in this room, can be null
    private HashMap<String, Integer> exits;// stores exits (direction → roomNumber mapping)
   private ArrayList<Item> roomItems = new ArrayList<>(); // items present in this room


    public Room(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomType = roomType;
        this.northNavigation = northNavigation;
        this.eastNavigation = eastNavigation;
        this.southNavigation = southNavigation;
        this.westNavigation = westNavigation;
        this.roomVisited = roomVisited;
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

  /*  public Puzzle getRoomPuzzle() {
        return roomPuzzle;
    }*/

    public HashMap<String, Integer> getExits() {
        return exits;
    }


  /*  public void addItem(Item item) {
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
*/
    public Room getExit(String direction) {
        // TODO: Map directions to connected rooms (if using a direction table)
        return null;
    }



}
