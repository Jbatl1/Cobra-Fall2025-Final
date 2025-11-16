package Model.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Items.Consumable;
import Model.Items.EnergyWeapon;
import Model.Items.Item;
import Model.Items.Weapon;
import Model.Rooms.CrashSite;
import Model.Rooms.LandingSite;
import Model.Rooms.Room;
import Model.Puzzles.Puzzle;

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
    public Player(Room startingRoom, String name, int health, int attackPower, Weapon starterItem) {
        super(name, health, attackPower);
        this.inventory = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.currRoom = startingRoom;
        this.equippedItem = null;
        this.narrativeMemory = new ArrayList<>();
        this.shipStorage = new ArrayList<>();
        this.gold = 100; // starting gold
        this.equippedItem = starterItem;
    }

    // ==============================
    // Ship Storage Management
    // ==============================

    public ArrayList<Item> getShipStorage() {
        return shipStorage;
    }

    // ==============================
    // Currency Methods
    // ==============================
    public int getGold() {
        return gold;
    }


    // ==============================
    // Move to a Different Landing Site
    // ==============================
    public int moveToLandingSite(String landingSite) {
        if (!(currRoom instanceof LandingSite)) {
            return -2; // not currently at a landing site
        }
        ArrayList<LandingSite> connections = new ArrayList<>(((LandingSite) currRoom).getLandingSiteConnections().values());
        for (LandingSite l : connections) {
            if (l.getRoomName().equalsIgnoreCase(landingSite)) {
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
        if (idx < 0) {
            return -1; // item not in inventory
        }
        if (equippedItem == null && this.inventory.get(idx) instanceof Weapon) {
            equippedItem = inventory.get(idx);
            this.inventory.remove(idx);
            return 1; // success
        }
        return -2; // item is not a weapon and hands are occupied
    }

    public int equipItemToToolBelt(String s) {
        int idx = isInInventory(s);
        if (idx < 0) return -1; // item not in inventory
        if (toolBelt.size() >= 5) return -2; // toolbelt full
        if (toolBelt.contains(inventory.get(idx))) return -3; // item already in toolbelt

        this.toolBelt.add(inventory.get(idx));
        this.inventory.remove(idx);
        return 1;

    }

    public int unEquipItemFromHands(String s) {
        if (equippedItem != null && equippedItem.getItemName().equalsIgnoreCase(s)) {
            addItem(equippedItem);
            equippedItem = null;
            return 1;
        }
        return -1;
    }

    public int removeItemFromToolBelt(String s) {
        if (toolBelt.isEmpty()) return -1; // toolbelt empty
        for (int i = 0; i < toolBelt.size(); i++) {
            if (toolBelt.get(i).getItemName().equalsIgnoreCase(s)) {
                this.inventory.add(toolBelt.get(i));
                toolBelt.remove(i);
                return 1;
            }
        }
        return -2; // item not in toolbelt
    }
    
    public int useToolBeltItem(int x) {
        if (toolBelt.isEmpty()) return -1; // toolbelt empty
        Item item = toolBelt.get(x);
        if (item.getItemType().equalsIgnoreCase("Consumable")) {
            consumeItem(item);
            return 1; //consumable used
        }
        else if (item.getItemType().equalsIgnoreCase("Weapon")) {
            if (equippedItem == null) {
                equippedItem = item;
                toolBelt.remove(x);
                return 2; // success
            }
            else {
                Item temp = equippedItem;
                equippedItem = item;
                toolBelt.set(x, temp);
                return 3; // swapped
            }
        }
        else if (item.getItemType().equalsIgnoreCase("Key")) {
            System.out.println("NEED TO IMPLEMENT KEY USAGE");
        }
        return -1; // cant use item
    }

    private int consumeItem(Item item) {
        if (item.getItemType().equalsIgnoreCase("Consumable")) {
            int restoreHP = ((Consumable) item).getHealth();
            this.health += restoreHP;
            if (this.health > 100) {
                this.health = 100;
            }
            int idx = isInInventory(item.getItemName());
            if (idx >= 0) {
                inventory.remove(idx);
            } else {
                if (isInToolBelt(item.getItemName()) >= 0){
                    toolBelt.remove(item);
                }
            }
            return restoreHP;
        }
        return 0;
    }

    private int isInToolBelt(String name) {
        for (int i = 0; i < toolBelt.size(); i++) {
            if (toolBelt.get(i).getItemName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1; // not found
    }

    public int dropEquippedItem() { // Caleb
        if (equippedItem != null) {
            currRoom.addItem(equippedItem);
            equippedItem = null;
            return 1;
        }
        return -1;
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
    //          Combat System
    // ==============================
    public int inflictDamage(Monster enemy) {
        if (equippedItem == null) return 0; // no weapon equipped
        int damage = ((Weapon)equippedItem).getDamage();
        int weaponStatus = ((Weapon) equippedItem).useDurability();
        if (weaponStatus == -1 && !(equippedItem instanceof EnergyWeapon)) {
            return -1; // weapon broke
        }
        if (weaponStatus == -1 && equippedItem instanceof EnergyWeapon) {
            return -2; // out of energy
        }
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

    public int buyItem( String itemName) { // Kai
        // Find item in shop stock
        Item item = null;

        for (Map.Entry<Item, Integer> entry : currRoom.getStock().entrySet()) {
            if (entry.getKey().getItemName().equalsIgnoreCase(itemName)) {
                item = entry.getKey();
                break;
            }
        }

        if (item == null) return -1; // not sold here

        if (this.gold < item.getCost()) {
            return -2; // not enough gold
        }

        this.inventory.add(item);
        return item.getCost();
    }

    public int sellItem(String itemName) { // Caleb
        Item item = null;
        for (Item i : inventory) {
            if (i.getItemName().equalsIgnoreCase(itemName)) {
                item = i;
                break;
            }
        }
        if (item == null) return -1; // item not in inventory

        for (Map.Entry<Item, Integer> entry : currRoom.getStock().entrySet()) {
            if (entry.getKey().getItemName().equalsIgnoreCase(itemName)) {
                int sellPrice = entry.getValue();
                this.gold += sellPrice;
                inventory.remove(item);
                return sellPrice;
            }
        }
        return -2; // shop does not buy this item
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

    public int storeItemInShip(String item) {
        if (!(this.currRoom instanceof LandingSite)) return -3; // not at landing site
        int idx = isInInventory(item);
        if (idx >= 0) {
            if (shipStorage.size() >= SHIP_CAPACITY) {
                return -2; // ship storage full
            }
            Item itemToStore = inventory.remove(idx);
            shipStorage.add(itemToStore);
            return 1; // success
        }
        return -1; // item not in inventory
    }

    public int storeItemInCrashedShip(String item) {
        if (!(this.currRoom instanceof CrashSite)) return -3; // not at crash site
        int idx = isInInventory(item);
        if (idx >= 0) {
            Item itemToStore = inventory.remove(idx);
            ((CrashSite) this.currRoom).addShipStorageItem(itemToStore);
            return 1; // success
        }
        return -1; // item not in inventory

    }

    public int getFromCrashedShip(String item) { // Caleb
        if (!(this.currRoom instanceof CrashSite)) return -2; // not at crash site
        ArrayList<Item> crashStorage = ((CrashSite) this.currRoom).getShipStorage();
        if (crashStorage.isEmpty()) return -3; // crash ship storage empty
        for (int i = 0; i < crashStorage.size(); i++) {
            if (crashStorage.get(i).getItemName().equalsIgnoreCase(item)) {
                Item itemToRetrieve = crashStorage.remove(i);
                inventory.add(itemToRetrieve);
                return 1; // success
            }
        }
        return -1; // item not in crash ship storage
    }

    public int getFromShip(String item) { // Caleb
        if (!(this.currRoom instanceof LandingSite)) return -2; // not at landing site
        if (shipStorage.isEmpty()) return -3; // ship storage empty
        for (int i = 0; i < shipStorage.size(); i++) {
            if (shipStorage.get(i).getItemName().equalsIgnoreCase(item)) {
                Item itemToRetrieve = shipStorage.remove(i);
                inventory.add(itemToRetrieve);
                return 1; // success
            }
        }
        return -1; // item not in ship storage
    }
}
///need a arraylist with a ship capacity of 20 items, we going to need a method in the player class to move to a different landing site which will take in a string called landingsite, its going to check if they current room is a landingsite, we also need to check if its an existing landingsite, if it exist update player room to the new landingsite, if its false return -1, if true return 1, if not in landingsite from the start return -2.
