package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;

public class Monster extends Entity {


    // Fields
    private String monsterID;// Unique ID for the monster
    private String abilityEffect;
    private String roomID;
    private String description;
    private boolean isBoss;
    private Item dropItem;
    private boolean isRaider;
    private Room currentRoom; // The room this monster belongs to



    // Constructor
    public Monster(String name, int health, int attackPower, int defense, String monsterID, String abilityEffect, Item dropItem, boolean isBoss, boolean isRaider, Room currentRoom) {
        super(name, health, attackPower, defense);


        this.monsterID = monsterID;
        this.abilityEffect = abilityEffect;
        this.dropItem = dropItem;
        this.isBoss = isBoss;
        this.isRaider = isRaider;
        this.currentRoom = currentRoom;
    }

    // Getters / Setters
    public String getMonsterID() {
        return monsterID;
    }

    public String getAbilityEffect() {
        return abilityEffect;
    }

    public Item getDropItem() {
        return dropItem;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public boolean isRaider() {
        return isRaider;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getDescription() {
        return description;
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
            System.out.println(name + " dropped: " + dropItem.getItemName());
            return dropItem;
        }
        return null;
    }

    public boolean canFlee() {
        return !isBoss;
    }

  /*  public void fleeAttempt(Player player) {
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
    }*/
}

