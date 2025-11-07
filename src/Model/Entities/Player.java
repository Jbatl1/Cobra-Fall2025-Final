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
    private ArrayList<String> narrativeMemory;
    private int health;

    // ==============================
    // Constructor
    // ==============================
    public Player(Room startingRoom) {
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

    public int EquipItemToHands(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            equippedItem = inventory.get(idx);
            return 1; // success
        }
        return 0; // item not found
    }

    public int EquipItemToToolBelt(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            toolBelt.add(inventory.get(idx));
            return 1;
        }
        return 0;
    }

    public int RemoveItemFromHands(String s) {
        if (equippedItem != null && equippedItem.getName().equalsIgnoreCase(s)) {
            equippedItem = null;
            return 1;
        }
        return 0;
    }

    public int RemoveItemFromToolBelt(String s) {
        return toolBelt.removeIf(i -> i.getName().equalsIgnoreCase(s)) ? 1 : 0;
    }

    public int dropItem(String s) {
        int idx = isInInventory(s);
        if (idx >= 0) {
            Item item = inventory.remove(idx);
            currRoom.addItem(item);
            return 1; // success
        }
        return 0; // item not found
    }

    public int pickupItem(String s) {
        Item item = currRoom.removeItem(s);
        if (item != null) {
            inventory.add(item);
            return 1; // success
        }
        return 0; // item not found
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
    // Combat and Damage (FR-004.2–004.6)
    // ==============================
    public void receiveDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public int inflictDamage(Monster enemy) {
        if (equippedItem == null) return 0;
        int damage = equippedItem.getDamageValue();
        enemy.receiveDamage(damage);
        return damage;
    }

    public int loseItemOnDefeat() {
        if (equippedItem != null) {
            equippedItem = null;
            return 1; // item lost
        }
        return 0; // nothing to lose
    }

    public int receiveRewardItem(Item item) {
        if (item == null) return 0;
        inventory.add(item);
        return 1; // reward received
    }
    // ==============================
// Combat - Ignore Fight
// ==============================
    public int ignoreFight(String monsterName) {
        if (currRoom == null || currRoom.getMonsters().isEmpty()) {
            return -1; // No monsters in room
        }

        for (Monster m : currRoom.getMonsters()) {
            if (m.getName().equalsIgnoreCase(monsterName)) {
                return 1; // Player chose to ignore fight
            }
        }
        return 0; // No matching monster
    }

    // ==============================
    // Start Fight (no print statements)
    // ==============================
    public int startFight(String monsterName) {
        ArrayList<Monster> monsters = currRoom.getMonsters();

        if (monsters == null || monsters.isEmpty()) {
            return -1; // no monsters in room
        }

        Monster target = null;
        for (Monster m : monsters) {
            if (m.getName().equalsIgnoreCase(monsterName)) {
                target = m;
                break;
            }
        }

        if (target == null) {
            return 0; // monster not found
        }

        // Fight loop (logic only)
        while (this.health > 0 && target.getHealth() > 0) {
            inflictDamage(target);

            if (target.getHealth() <= 0) {
                currRoom.getMonsters().remove(target);
                if (target.getRewardItem() != null) {
                    receiveRewardItem(target.getRewardItem());
                }
                return 1; // fight won
            }

            target.attack(this); // Monster attacks back
        }

        if (this.health <= 0) {
            loseItemOnDefeat();
            return -2; // player defeated
        }

        return 0; // fallback
    }

    // ==============================
    // Player Memory and Bartering (FR-005.2, FR-005.3, FR-005.5)
    // ==============================
    public void rememberEvent(String event) {
        narrativeMemory.add(event);
    }

    public ArrayList<String> getMemories() {
        return narrativeMemory;
    }

    public boolean barterItem(String offerItem, String receiveItem) {
        int idx = isInInventory(offerItem);
        if (idx >= 0) {
            inventory.remove(idx);
            inventory.add(new Item(receiveItem)); // placeholder
            return true;
        }
        return false;
    }

    // ==============================
    // Puzzle Interaction (FR-006.2, FR-006.4, FR-006.5)
    // ==============================
    public int skipPuzzle(Puzzle p) {
        if (currRoom.getPuzzle() != null && !currRoom.getPuzzle().isSkipped()) {
            receiveDamage(10);
            return 1; // puzzle skipped with penalty
        }
        return -1; // no puzzle or already skipped
    }

    // ==============================
    // Movement and General Actions
    // ==============================
    public int move(String direction) {
        Room next = currRoom.getExit(direction);
        if (next != null) {
            currRoom = next;
            return 1; // moved successfully
        }
        return 0; // invalid move
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
}