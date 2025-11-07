package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;
import java.util.ArrayList;
import java.util.Random;

public class Monster extends Entity {


    // Fields

    private String monsterID;     // Unique ID for the monster
    private String roomID;        // The room this monster belongs to
    private String description;
    private boolean isBoss;
    private Item dropItem;
    private int maxHealth;
    private Room currentRoom;


    // Constructor

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


    // Getters / Setters

    public String getMonsterID() {
        return monsterID;
    }

    public String getRoomID() {
        return roomID;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public Item getDropItem() {
        return dropItem;
    }

    public void setDropItem(Item dropItem) {
        this.dropItem = dropItem;
    }


    // Display Info

    public void inspect() {
        System.out.println("=== Monster Information ===");
        System.out.println("Monster ID: " + monsterID);
        System.out.println("Room ID: " + roomID);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Attack Power: " + attackPower);
        System.out.println("Defense: " + defense);
        System.out.println("Boss: " + (isBoss ? "Yes" : "No"));
        System.out.println("============================");
    }


    // Combat

    public void attackPlayer(Player player) {
        int damage = Math.max(0, attackPower - player.getDefense());
        player.receiveDamage(damage);
        System.out.println(name + " attacks " + player.getName() + " for " + damage + " damage!");
    }

    public void receiveDamage(int amount) {
        int damageTaken = Math.max(0, amount - defense);
        health -= damageTaken;
        if (health < 0) health = 0;
        System.out.println(name + " takes " + damageTaken + " damage! (" + health + " HP left)");
        if (health == 0) {
            System.out.println(name + " has been defeated!");
        }
    }


    // Rewards / Flee

    public Item getReward() {
        if (health <= 0 && dropItem != null) {
            System.out.println(name + " dropped: " + dropItem.getName());
            return dropItem;
        }
        return null;
    }

    public boolean canFlee() {
        return !isBoss;
    }

    public void fleeAttempt(Player player) {
        if (!canFlee()) {
            System.out.println("You cannot flee from this boss monster!");
        } else {
            Random rand = new Random();
            if (rand.nextBoolean()) {
                System.out.println("You successfully fled from " + name + "!");
            } else {
                int penalty = 10;
                player.receiveDamage(penalty);
                System.out.println("You failed to flee and lost " + penalty + " HP!");
            }
        }
    }
}