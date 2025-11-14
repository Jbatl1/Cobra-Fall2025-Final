package Model.Entities;

import Model.Rooms.Room;
import Model.Items.Item;

/**
 * Monster entity. No printing inside model.
 */
public class Monster extends Entity {

    // Fields
    private String monsterID;
    private String abilityEffect;
    private boolean isBoss;
    private Item dropItem;
    private boolean isRaider;
    private Room currentRoom;

    // Constructor
    public Monster(String name, int health, int attackPower, int defense,
                   String monsterID, String abilityEffect, Item dropItem,
                   boolean isBoss, boolean isRaider, Room currentRoom) {

        super(name, health, attackPower);
        this.defense = defense;

        this.monsterID = monsterID;
        this.abilityEffect = abilityEffect;
        this.dropItem = dropItem;
        this.isBoss = isBoss;
        this.isRaider = isRaider;
        this.currentRoom = currentRoom;
    }

    // Getters
    public String getMonsterID() { return monsterID; }
    public String getAbilityEffect() { return abilityEffect; }
    public Item getDropItem() { return dropItem; }
    public boolean isBoss() { return isBoss; }
    public boolean isRaider() { return isRaider; }
    public Room getCurrentRoom() { return currentRoom; }

    /**
     * Attack the given player. Returns the damage dealt (after defense).
     * The View can use the returned value to display messages.
     */
    public int attackPlayer(Player player) {
        int damage = Math.max(0, attackPower - player.getDefense());
        player.receiveDamage(damage);
        return damage;
    }

    /**
     * If the monster is dead, return its drop item; otherwise return null.
     * The View or caller handles presentation of this reward.
     */
    public Item getReward() {
        if (health <= 0 && dropItem != null) {
            return dropItem;
        }
        return null;
    }

    public boolean canFlee() {
        return !isBoss;
    }
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


