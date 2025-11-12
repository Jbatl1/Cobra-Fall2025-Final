package Model.Entities;

import java.util.ArrayList;
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
    private ArrayList<String> narrativeMemory;
    private int health;

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
        this.health = 100;
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
        if (shipStorage.remove(item)) {
            return 1;
        }
        return -1;
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
        if (!currRoom.getName().equalsIgnoreCase("Landing site")) {
            return -2;
        }
        Room target = Room.getRoomByName(landingSite);
        if (target == null || !target.getName().equalsIgnoreCase("Landing site")) {
            return -1;
        }
        currRoom = target;
        return 1;
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


    public int isInInventory(String s) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }

    // Equip item to hands but keep it in inventory
    public int equipItemToHands(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            equippedItem = inventory.get(idx);
            return 1;
        }
        return 0;
    }

    // Add item to toolbelt but keep it in inventory
    public int equipItemToToolBelt(String s) {
        int idx = isInInventory(s);
        if (idx >= 0 && !toolBelt.contains(inventory.get(idx))) {
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
    public void receiveDamage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public int inflictDamage(Monster enemy) {
        if (equippedItem == null) return 0; // no weapon equipped
        int damage = equippedItem.getDamageValue();
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

    // Main combat logic — called externally when "fight" command is issued
    public int startCombatLoop(Monster enemy) {
        if (enemy == null) return -1;

        while (health > 0 && enemy.getHealth() > 0) {
            int damageDealt = inflictDamage(enemy);

            if (enemy.getHealth() <= 0) {
                receiveRewardItem(new Item("Trophy"));
                addGold(20);
                return 1; // win
            }

            receiveDamage(enemy.getAttackPower());
            if (health <= 0) {
                loseItemOnDefeat();
                return 0; // lose
            }
        }
        return -1; // unexpected
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

    // barterType = "buy", "sell", or "trade"
    // price = gold amount for buy/sell
    public int barterItem(String offerItem, String receiveItem, String barterType, int price) {
        if (barterType.equalsIgnoreCase("sell")) {
            int idx = isInInventory(offerItem);
            if (idx >= 0) {
                inventory.remove(idx);
                addGold(price);
                return 1;
            }
            return 0;
        }

        if (barterType.equalsIgnoreCase("buy")) {
            if (spendGold(price)) {
                inventory.add(new Item(receiveItem));
                return 1;
            }
            return -1;
        }

        if (barterType.equalsIgnoreCase("trade")) {
            int idx = isInInventory(offerItem);
            if (idx >= 0) {
                inventory.remove(idx);
                inventory.add(new Item(receiveItem));
                return 1;
            }
            return 0;
        }

        return 0;
    }

    // ==============================
    // Puzzle Interaction
    // ==============================
    public void examinePuzzle(Puzzle p) { }

    public void skipPuzzle(Puzzle p) {
        receiveDamage(10);
    }

    // ==============================
    // Movement
    // ==============================
    public int move(String direction) {
        Room next = currRoom.getExit(direction);
        if (next != null) {
            currRoom = next;
            return 1;
        }
        return 0;
    }

    public int getHealth() {
        return health;
    }

    public Room getCurrRoom() {
        return currRoom;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }


    public void rest() {
        this.health += 5;
        if (this.health > 100) {
            this.health = 100;
        }
    }
}
///need a arraylist with a ship capacity of 20 items, we going to need a method in the player class to move to a different landing site which will take in a string called landingsite, its going to check if they current room is a landingsite, we also need to check if its an existing landingsite, if it exist update player room to the new landingsite, if its false return -1, if true return 1, if not in landingsite from the start return -2.