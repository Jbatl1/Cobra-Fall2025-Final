package Model.Entities;

import java.util.ArrayList;
import Model.Items.Item;
import Model.Rooms.Room;

public class Player extends Entity {

    // ==============================
    // Fields
    // ==============================
    private ArrayList<Item> inventory;
    private ArrayList<Item> toolBelt;
    private Item equippedItem;
    private Room currRoom;

    // ==============================
    // Constructor
    // ==============================
    public Player(Room startingRoom) {
        this.inventory = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.currRoom = startingRoom;
        this.equippedItem = null;
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    private int isInInventory(String s) {
        // TODO: Check if item with name s is in inventory
        return -1;
    }
