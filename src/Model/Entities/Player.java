package Model.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import Model.Items.Item;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;
import Model.Entities.Monster;


public class Player extends Entity {

    //ANITA MODIFIED
    //getName()  --> getItemName()
    //getDescription() --> getPuzzleQuestion()
    //getDamageValue() --> getItemDamage()
    //getName() --> getRoomName()
    //deleted hint method


    // ==============================
    // Fields
    // ==============================
    private ArrayList<Item> inventory;
    private ArrayList<Item> toolBelt;
    private Item equippedItem;
    private Room currRoom;
    private Room prevRoom;
    private ArrayList<String> narrativeMemory; // FR-005.3
    private int health;


    // Ship storage (capacity 20)
    private ArrayList<Item> shipStorage;
    private final int SHIP_CAPACITY = 20;

    // ==============================
    // Constructor
    // ==============================
    public Player(String name, int health, int attackPower, int defense, ArrayList<Item> inventory, ArrayList<Item> toolBelt, Item equippedItem, Room currRoom, ArrayList<String> narrativeMemory, int health1) {
        super(name, health, attackPower, defense);
        this.inventory = inventory;
        this.toolBelt = toolBelt;
        this.equippedItem = equippedItem;
        this.currRoom = currRoom;
        this.narrativeMemory = narrativeMemory;
        this.health = 100; // Default HP
    }

    // ==============================
    // Ship Storage Management
    // ==============================
    public int addCargoItem(Item item) {
        if (shipStorage.size() >= SHIP_CAPACITY) {
            return -1; // storage full
        }
        shipStorage.add(item);
        return 1; // success
    }

    public int removeCargoItem(Item item) {
        if (shipStorage.remove(item)) {
            return 1; // successfully removed
        }
        return -1; // item not found
    }

    public ArrayList<Item> getShipStorage() {return shipStorage;}

    // ==============================
    // Move to a Different Landing Site
    // ==============================
    public int moveToLandingSite(String landingSite) {
        // Not currently at a landing site
        if (!currRoom.getRoomName().equalsIgnoreCase("Landing site")) {
            return -2;
        }

        // Check if the given landing site exists
        Room target = Room.getRoomByName(landingSite); // assumed static lookup
        if (target == null || !target.getRoomName().equalsIgnoreCase("Landing site")) {
            return -1;
        }

        // Move to the new landing site
        currRoom = target;
        return 1;
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    private int isInInventory(String s) {
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
        if (idx >= 0) {
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
            currRoom.addItem(item); // assume Room has addItem()
            return 1;
        }
        return 0;
    }

    public int pickupItem(String s) {
        Item item = currRoom.removeItem(s); // assume Room has removeItem()
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
    // Combat and Damage (FR-004.3–004.6)
    // ==============================
    public void receiveDamage(int amount) { // FR-004.3
        health -= amount;
        if (health < 0) health = 0;
    }

    public int inflictDamage(Monster enemy) { // FR-004.4
        if (equippedItem == null) return 0; // no weapon
        int damage = equippedItem.getItemDamage(); // assume Item has damage value
        enemy.receiveDamage(damage);
        return damage;
    }

    public void loseItemOnDefeat() { // FR-004.5
        if (equippedItem != null) {
            equippedItem = null;
        }
    }


 public void receiveRewardItem(Item item) { // FR-004.6
        inventory.add(item);
        System.out.println("You received " + item.getItemName() + " after victory!");
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================


    // ==============================
    // Player Memory and Bartering (FR-005.2, FR-005.3, FR-005.5)
    // ==============================
    public void rememberEvent(String event) {
        narrativeMemory.add(event);
    }
    public ArrayList<String> getMemories() {
        return narrativeMemory;
    }



    public boolean barterItem(String offerItem, String receiveItem) { // FR-005.5
        int idx = isInInventory(offerItem);
        if (idx >= 0) {
            inventory.remove(idx);
            inventory.add(new Item(receiveItem)); // placeholder
            System.out.println("You bartered " + offerItem + " for " + receiveItem + "!");
            return true;
        }
        return false;
    }

    // ==============================
    // Puzzle Interaction (FR-006.2, FR-006.4, FR-006.5)
    // ==============================

    // Take out puzzle p check if currRoom has puzzle Return 1 if not null, then give puzzle description, if no puzzle then return 0.
    public void examinePuzzle(Puzzle p) {
        // handled by View
    }

    public void skipPuzzle(Puzzle p) {
        receiveDamage(10); // penalty
    }

    // ==============================
    // Movement and General Actions
    // ==============================
    public int move(String direction) {
        Room next = currRoom.getExit(direction);
        if (next != null) {
            setCurrRoom(next); //updates both currRoom and prevRoom     //currRoom = next;
            return 1;
        }
        System.out.println("You can't move that way.");
        return 0;
    }

    public int getHealth() {
        return health;
    }

    public Room getCurrRoom() {
        return currRoom;
    }

    public Room getPrevRoom() {return prevRoom;}

    public void setCurrRoom(Room newRoom) {
        this.prevRoom = this.currRoom;  // store current as previous
        this.currRoom = newRoom;        // move to new room
    }



}


