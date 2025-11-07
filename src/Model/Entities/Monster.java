package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;

public class Monster extends Entity {

    // ==============================
    // Fields
    // ==============================
    private String monsterID;
    private String roomID;
    private String description;
    private boolean isBoss;
    private Item dropItem;
    private int maxHealth;
    private Room currentRoom;

    // ==============================
    // Constructor (used when loaded from Room)
    // ==============================
    public Monster(String monsterID, String roomID, String name, String description,
                   int health, int attackPower, int defense, boolean isBoss, Item dropItem, Room currentRoom) {

        super(name, health, attackPower);
        this.monsterID = monsterID;
        this.roomID = roomID;
        this.description = description;
        this.isBoss = isBoss;
        this.dropItem = dropItem;
        this.defense = defense;
        this.maxHealth = health;
        this.currentRoom = currentRoom;
    }

    // ==============================
    // Basic Getters and Setters
    // ==============================
    public String getMonsterID() { return monsterID; }
    public String getRoomID() { return roomID; }
    public Room getCurrentRoom() { return currentRoom; }
    public String getDescription() { return description; }
    public boolean isBoss() { return isBoss; }
    public Item getDropItem() { return dropItem; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }
    public void setDropItem(Item dropItem) { this.dropItem = dropItem; }

    // ==============================
    // Inspect Monster (logic only — View will print)
    // ==============================
    public String inspectDetails() {
        return "Monster ID: " + monsterID + "\n"
                + "Room ID: " + roomID + "\n"
                + "Name: " + name + "\n"
                + "Description: " + description + "\n"
                + "Health: " + health + "/" + maxHealth + "\n"
                + "Attack Power: " + attackPower + "\n"
                + "Defense: " + defense + "\n"
                + "Boss: " + (isBoss ? "Yes" : "No");
    }
}