package Model.Entities;

import java.io.Serializable;

/**
 * Base class for all living entities (Players, Monsters, NPCs).
 * Handles shared logic for health, attack, and defense.
 * No print statements — View layer handles all output.
 */

public abstract class Entity implements Serializable {

    // ==============================
    // Fields
    // ==============================
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;

    // ==============================
    // Constructor
    // ==============================
    public Entity(String name, int health, int attackPower) { //Kai Wiggins
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = 0; // default unless overridden
    }

    // ==============================
    // Getters and Setters - //Kai Wiggins
    // ==============================
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    // ==============================
    // Core Combat Logic
    // ==============================


        public int receiveDamage (int amount){
            int damageTaken = Math.max(0, amount - defense);
            health -= damageTaken;
            if (health < 0) {
                health = 0;
            }
            return damageTaken;
        }

}