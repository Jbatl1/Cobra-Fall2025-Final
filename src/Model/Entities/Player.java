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
public class Player extends Entity { //Kai Wiggins & Caleb Butler

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
    public Player(Room startingRoom, String name, int health, int attackPower, Weapon starterItem) { //Kai Wiggins
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
    public int moveToLandingSite(String landingSite) { //Caleb Butler
        if (!(currRoom instanceof LandingSite)) {
            return -2; // not currently at a landing site
        }
        ArrayList<LandingSite> connections = new ArrayList<>(((LandingSite) currRoom).getLandingSiteConnections().values());
        for (LandingSite l : connections) {
            if (l.getRoomName().equalsIgnoreCase(landingSite)) {
                currRoom = l;
                switch (currRoom.getRoomID()) {
                    case "VI1":
                        return 1;
                    case "FW1":
                        return 2;
                    case "SW1":
                        return 3;
                    case "JR1":
                        return 4;
                    case "ED1":
                        return 5;
                    case "CC1":
                        return 6;
                    case "SI1":
                        return 7;
                    case "CT1":
                        return 8;
                }
            }
        }
        return -1; // landing site does not exist
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================
    public ArrayList<Item> getInventory() {
        return inventory;
    }  //Kai Wiggins

    public ArrayList<Item> getToolBelt() {
        return toolBelt;
    } //Kai Wiggins

    public void addItem(Item newItem) { //Kai Wiggins
        for (Item item : inventory) {
            if (item.getItemID().equals(newItem.getItemID())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        inventory.add(newItem);
    }

    public int isInInventory(String s) { //Kai Wiggins
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItemName().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }

    public int equipItemToHands(String s) { //Kai Wiggins & Caleb Butler
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

    public int equipItemToToolBelt(String s) { //Kai Wiggins & Caleb Butler
        int idx = isInInventory(s);
        if (idx < 0) return -1; // item not in inventory
        if (toolBelt.size() >= 5) return -2; // toolbelt full
        if (toolBelt.contains(inventory.get(idx))) return -3; // item already in toolbelt

        this.toolBelt.add(inventory.get(idx));
        this.inventory.remove(idx);
        return 1;

    }

    public int unEquipItemFromHands(String s) {//Kai Wiggins
        if (equippedItem != null && equippedItem.getItemName().equalsIgnoreCase(s)) {
            addItem(equippedItem);
            equippedItem = null;
            return 1;
        }
        return -1;
    }

    public int removeItemFromToolBelt(String s) {  //Kai Wiggins & Caleb Butler
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
    
    public int useToolBeltItem(int x) { //Kai Wiggins & Caleb Butler
        if (toolBelt.isEmpty()) return -1; // toolbelt empty
        if (x > toolBelt.size()-1) return -2; // no item in slot
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
        return -3; // cant use item
    }

    private int consumeItem(Item item) { //Caleb Butler
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

    private int isInToolBelt(String name) { //Caleb Butler
        for (int i = 0; i < toolBelt.size(); i++) {
            if (toolBelt.get(i).getItemName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1; // not found
    }

    public int dropEquippedItem() { //Caleb Butler
        if (equippedItem != null) {
            currRoom.addItem(equippedItem);
            equippedItem = null;
            return 1;
        }
        return -1;
    }

    public int dropItem(String s) { //Kai Wiggins
        int idx = isInInventory(s);
        if (idx >= 0) {
            Item item = inventory.remove(idx);
            currRoom.addItem(item);
            return 1;
        }
        return 0;
    }

    public int pickupItem(String s) { //Kai Wiggins
        Item item = currRoom.removeItem(s);
        if (item != null) {
            inventory.add(item);
            return 1;
        }
        return 0;
    }


    public int destroyItem(String s) {  //Kai Wiggins
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
    public int inflictDamage(Monster enemy) { //Kai Wiggins & Caleb Butler
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

    public void loseItemOnDefeat() { //Kai Wiggins & Caleb Butler
        if (equippedItem != null) {
            equippedItem = null;
        }
    }


    // ==============================
    // Player Memory & Bartering
    // ==============================

    public int buyItem( String itemName) { //Kai Wiggins & Caleb Butler
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
        this.gold -= item.getCost();
        return item.getCost();
    }

    public int sellItem(String itemName) { //Caleb Butler
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


    // ==============================
    // Movement
    // ==============================
    public int move(String direction) {//Kai Wiggins
        HashMap<String, Room> exits = currRoom.getExits();
        Room nextRoom = exits.get(direction);

        if (nextRoom == null) {
            return -1; // no exit
        }

        // Check for boundary puzzle in that direction
        Puzzle boundaryPuzzle = currRoom.getBoundaryPuzzleInDirection(direction, exits);
        if (boundaryPuzzle != null && !boundaryPuzzle.isPuzzleIsSolved()) {
            return -2; // boundary puzzle exists
        }

        // Normal movement
        setCurrRoom(nextRoom); // updates prevRoom automatically
        return 1;
    }

    public Room getCurrRoom() {
        return currRoom;
    } //Anita Philip

    public Item getEquippedItem() {
        return equippedItem;
    } //Anita Philip

    public Room getPrevRoom() {
        return prevRoom;
    } //Anita Philip

    public void setCurrRoom(Room newRoom) { //Anita Philip
        this.prevRoom = this.currRoom;  // store current as previous
        this.currRoom = newRoom;        // move to new room
    }

    public void rest() { //Caleb Butler
        this.health += 5;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    public int storeItemInShip(String item) {  //Caleb Butler
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

    public int storeItemInCrashedShip(String item) { //Caleb Butler
        if (!(this.currRoom instanceof CrashSite)) return -3; // not at crash site
        int idx = isInInventory(item);
        if (idx >= 0) {
            Item itemToStore = inventory.remove(idx);
            ((CrashSite) this.currRoom).addShipStorageItem(itemToStore);
            return 1; // success
        }
        return -1; // item not in inventory

    }

    public int getFromCrashedShip(String item) { //Caleb Butler
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

    public int getFromShip(String item) {  //Caleb Butler
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
