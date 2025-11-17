package Model.Entities;

/**
 * Base class for all living entities (Players, Monsters, NPCs).
 * Handles shared logic for health, attack, and defense.
 * No print statements — View layer handles all output.
 */

public abstract class Entity {

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
    public Entity(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = 0; // default unless overridden
    }

    // ==============================
    // Getters and Setters
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


    // ==============================
    // Core Combat Logic
    // ==============================

    /**
     * Applies incoming damage after accounting for defense.
     * Ensures health never drops below zero.
     * Logic-only — no direct printing.
     */
    public void receiveDamage(int amount) {
        int damageTaken = Math.max(0, amount - defense);
        health -= damageTaken;
        if (health < 0) {
            health = 0;
        }
    }
}