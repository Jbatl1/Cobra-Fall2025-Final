package Model.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import Model.Items.Item;
import Model.Rooms.Room;
import Model.Entities.Monster;
import Model.Puzzle;

public class Player extends Entity {

    // ==============================
    // Fields
    // ==============================
    private ArrayList<Item> inventory;
    private ArrayList<Item> toolBelt;
    private Item equippedItem;
    private Room currRoom;
    private ArrayList<String> narrativeMemory; // FR-005.3
    private int health;

    // ==============================
    // Constructor
    // ==============================
    public Player(Room startingRoom, String name, int health, int attackPower) {
        super(name,health,attackPower);
        this.inventory = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.currRoom = startingRoom;
        this.equippedItem = null;
        this.narrativeMemory = new ArrayList<>();
        this.health = 100; // Default HP
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    private int isInInventory(String s) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(s)) {
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
        if (equippedItem != null && equippedItem.getName().equalsIgnoreCase(s)) {
            equippedItem = null;
            return 1;
        }
        return 0;
    }

    public int removeItemFromToolBelt(String s) {
        return toolBelt.removeIf(i -> i.getName().equalsIgnoreCase(s)) ? 1 : 0;
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
        int damage = equippedItem.getDamageValue(); // assume Item has damage value
        enemy.receiveDamage(damage);
        return damage;
    }

    public void loseItemOnDefeat() { // FR-004.5
        if (equippedItem != null) {
            System.out.println("You lost your " + equippedItem.getName() + " in battle!");
            equippedItem = null;
        }
    }

    public void receiveRewardItem(Item item) { // FR-004.6
        inventory.add(item);
        System.out.println("You received " + item.getName() + " after victory!");
    }

    // ==============================
    // Player Memory and Bartering (FR-005.2, FR-005.3, FR-005.5)
    // ==============================
    public void rememberEvent(String event) {
        narrativeMemory.add(event);
    }

    public void showMemories() {
        System.out.println("Your memories:");
        for (String e : narrativeMemory) {
            System.out.println("- " + e);
        }
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
    public void examinePuzzle(Puzzle p) {
        System.out.println("Puzzle: " + p.getDescription());
    }

    public void requestPuzzleHint(Puzzle p) {
        System.out.println("Hint: " + p.getHint());
        receiveDamage(5); // penalty
    }

    public void skipPuzzle(Puzzle p) {
        System.out.println("You skipped the puzzle, but lost health!");
        receiveDamage(10);
    }

    // ==============================
    // Movement and General Actions
    // ==============================
    public int move(String direction) {
        Room next = currRoom.getExit(direction);
        if (next != null) {
            currRoom = next;
            System.out.println("You moved " + direction + " to " + currRoom.getName());
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
}
//need a arraylist with a ship capacity of 20 items, we going to need a method in the player class to move to a different landing site which will take in a string called landingsite, its going to check if they current room is a landingsite, we also need to check if its an existing landingsite, if it exist update player room to the new landingsite, if its false return -1, if true return 1, if not in landingsite from the start return -2.