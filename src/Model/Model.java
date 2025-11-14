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

    // ---------------- Puzzles ----------------
    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(HashMap<String, Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    // ---------------- Rooms ----------------
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    // ---------------- Utility ----------------
    public Item findItemInCurrentRoom(String name) {
        for (Item i : getPlayer().getCurrRoom().getRoomItems()) {
            if (i.getItemName().equalsIgnoreCase(name)) return i;
        }
        return null;
    }
}
