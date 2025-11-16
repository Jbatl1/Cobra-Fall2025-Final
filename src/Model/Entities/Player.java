package Model.Entities;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Items.Item;
import Model.Rooms.LandingSite;
import Model.Rooms.Room;
import Model.Entities.Monster;
import Model.Puzzles.Puzzle;
import Model.Rooms.Shop;
import Model.Rooms.Shop;

/**
 * Player entity. Uses Entity's health/attack/defense fields and methods.
 * Model contains no printing; View handles output.
 */
public class Player extends Entity {

    // ==============================
    // Fields
    // ==============================
    private ArrayList<Item> inventory;
    private ArrayList<Item> toolBelt;
    private Item equippedItem;
    private Room currRoom;
    private Room prevRoom;
    private ArrayList<String> narrativeMemory; // FR-005.3

    // Ship storage (capacity 20)
    private ArrayList<Item> shipStorage;
    private final int SHIP_CAPACITY = 20;

    // Currency
    private int gold;

    // ==============================
    // Constructor
    // ==============================
    public Player(Room startingRoom, String name, int health, int attackPower) {
        super(name, health, attackPower);
        this.inventory = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.currRoom = startingRoom;
        this.equippedItem = null;
        this.narrativeMemory = new ArrayList<>();
        this.shipStorage = new ArrayList<>();
        this.gold = 100; // starting gold
    }

    // ==============================
    // Ship Storage Management
    // ==============================
    public int addCargoItem(Item item) {
        if (shipStorage.size() >= SHIP_CAPACITY) {
            return -1; // full
        }
        shipStorage.add(item);
        return 1;
    }

    public int removeCargoItem(Item item) {
        return shipStorage.remove(item) ? 1 : -1;
    }

    public ArrayList<Item> getShipStorage() {
        return shipStorage;
    }

    // ==============================
    // Currency Methods
    // ==============================
    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        if (amount > 0) {
            gold += amount;
        }
    }

    public boolean spendGold(int amount) {
        if (amount <= gold) {
            gold -= amount;
            return true;
        }
        return false;
    }

    public int checkCurrency() {
        return gold;
    }

    // ==============================
    // Move to a Different Landing Site
    // ==============================
    public int moveToLandingSite(String landingSite) {
        if (!currRoom.getRoomName().equalsIgnoreCase("Landing site") || !currRoom.getRoomType().equalsIgnoreCase("Crash site")) {
            return -2; // not currently at a landing site
        }
        ArrayList<LandingSite> connections = new ArrayList<>(((LandingSite) currRoom).getLandingSiteConnections().values());
        for (LandingSite l : connections) {
            if (l.getLandingSiteName().equalsIgnoreCase(landingSite)) {
                currRoom = l;
                return 1; // success
            }
        }
        return -1; // landing site does not exist
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getToolBelt() {
        return toolBelt;
    }

    public void addItem(Item newItem) {
        for (Item item : inventory) {
            if (item.getItemID().equals(newItem.getItemID())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        inventory.add(newItem);
    }

    public int isInInventory(String s) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItemName().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }

    public int equipItemToHands(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            equippedItem = inventory.get(idx);
            return 1; // success
        }
        return 0;
    }

    public int equipItemToToolBelt(String s) {
        int idx = isInInventory(s);
        if (idx >= 0 && !toolBelt.contains(inventory.get(idx))) {
            toolBelt.add(inventory.get(idx));
            return 1;
        }
        return 0;
    }

    public int removeItemFromHands(String s) {
        if (equippedItem != null && equippedItem.getItemName().equalsIgnoreCase(s)) {
            equippedItem = null;
            return 1;
        }
        return 0;
    }

    public int removeItemFromToolBelt(String s) {
        return toolBelt.removeIf(i -> i.getItemName().equalsIgnoreCase(s)) ? 1 : 0;
    }

    public int dropItem(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            Item item = inventory.remove(idx);
            currRoom.addItem(item);
            return 1;
        }
        return 0;
    }

    public int pickupItem(String s) {
        Item item = currRoom.removeItem(s);
        if (item != null) {
            inventory.add(item);
            return 1;
        }
        return 0;
    }

    public int destroyItem(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            inventory.remove(idx);
            return 1;
        }
        return 0;
    }

    // ==============================
    // Combat System
    // ==============================
    public int inflictDamage(Monster enemy) {
        if (equippedItem == null) return 0; // no weapon equipped
        int damage = equippedItem.getItemDamage();
        enemy.receiveDamage(damage);
        return damage;
    }

    public void loseItemOnDefeat() {
        if (equippedItem != null) {
            equippedItem = null;
        }
    }

    public void receiveRewardItem(Item item) {
        inventory.add(item);
    }

    // ==============================
    // Player Memory & Bartering
    // ==============================
    public void rememberEvent(String event) {
        narrativeMemory.add(event);
    }

    public ArrayList<String> getMemories() {
        return narrativeMemory;
    }

    public int buyItem( String itemName) {
        // Find item in shop stock
        Item item = null;
        for (Item i : ((Shop) currRoom).getStock()) {
            if (i.getItemName().equalsIgnoreCase(itemName)) {
                item = i;
                break;
            }
        }

        if (item == null) return -1; // not sold here

        return -1;
    }

    public int sellItem(String itemName) {
        int idx = isInInventory(itemName);
        if (((Shop) currRoom).getStock().contains(inventory.get(idx))) {
            inventory.remove(idx);
            return 1;
        }
        return -1;
    }


    // ==============================
    // Puzzle Interaction
    // ==============================
    public void examinePuzzle(Puzzle p) {
        // logic-only; View handles display
    }

    public void skipPuzzle(Puzzle p) {
        receiveDamage(10);
    }


    // ==============================
    // Movement
    // ==============================
    public int move(String direction) {
        HashMap<String, Room> exits = currRoom.getExits();
        if (exits.containsKey(direction) && currRoom.getBoundaryPuzzleInDirection(direction, exits) != null) {
            return -2;
        }
        else if(exits.containsKey(direction) && exits.get(direction).getBoundaryPuzzleInDirection(direction, exits) == null) {
            this.currRoom = currRoom.getExits().get(direction);
            return 1;
        }
        else {
            return -1;
        }
    }

    public Room getCurrRoom() {
        return currRoom;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public Room getPrevRoom() {
        return prevRoom;
    }

    public void setCurrRoom(Room newRoom) {
        this.prevRoom = this.currRoom;  // store current as previous
        this.currRoom = newRoom;        // move to new room
    }

    public void rest() {
        this.health += 5;
        if (this.health > 100) {
            this.health = 100;
        }
    }
}
///need a arraylist with a ship capacity of 20 items, we going to need a method in the player class to move to a different landing site which will take in a string called landingsite, its going to check if they current room is a landingsite, we also need to check if its an existing landingsite, if it exist update player room to the new landingsite, if its false return -1, if true return 1, if not in landingsite from the start return -2.
