package Model;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;

import java.util.HashMap;

public class Model {
    private Player player;
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Puzzle> puzzles = new HashMap<>();
    private HashMap<String, Room> rooms = new HashMap<>();

    public Model(Player player) {
        this.player = player;
    }


    // ---------------- Player ----------------
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // ---------------- Items ----------------
    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    // ---------------- Rooms ----------------
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public void displayDestroyItem(int x, String itemName) {
        if (x == 1) {
            System.out.println("You destroyed your " + itemName + "!");
        }
        else if (x == -1) {
            System.out.println("You don't have a " + itemName + " in your inventory");
        }
    }
}
