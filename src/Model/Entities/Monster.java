package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;

public class Monster extends Entity { //Kai Wiggins


    // Fields
    private String monsterID;// Unique ID for the monster
    private String abilityEffect;
    private String roomID;
    private String description;
    private boolean isBoss;
    private Item dropItem;
    private boolean isRaider;
    private Room currentRoom; // The room this monster belongs to



    public Monster(String name, int health, int attackPower, int defense,
                   String monsterID, String abilityEffect, String roomID,
                   boolean isBoss, Item dropItem, boolean isRaider, Room currentRoom) { //Kai Wiggins

        super(name, health, attackPower);
        this.monsterID = monsterID;
        this.abilityEffect = abilityEffect;
        this.defense = defense;
        this.roomID = roomID;         // from DB
        this.isBoss = isBoss;
        this.dropItem = dropItem;
        this.isRaider = isRaider;
        this.currentRoom = currentRoom;  // actual Room object
    }


    // Getters / Setters - //Kai Wiggins
    public String getMonsterID() {
        return monsterID;
    }

    public Item getDropItem() {
        return dropItem;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getDescription() {
        return description;
    }
    // Combat


    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }


    public int receiveDamage(int amount) {
        int damageTaken = Math.max(0, amount - defense);
        health -= damageTaken;
        if (health < 0) health = 0;
        System.out.println(name + " takes " + damageTaken + " damage! (" + health + " HP left)");
        if (health == 0) {
            System.out.println(name + " has been defeated!");
        }
        return damageTaken;
    }

}

