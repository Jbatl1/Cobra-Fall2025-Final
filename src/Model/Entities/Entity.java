package Model.Entities;

public abstract class Entity {


    // Fields

    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;


    // Constructor

    public Entity(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = 0; // Default unless overridden
    }


    // Getters and Setters

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


    // Core Methods

    public void receiveDamage(int amount) {
        int damageTaken = Math.max(0, amount - defense);
        health -= damageTaken;
        if (health < 0) health = 0;
        System.out.println(name + " took " + damageTaken + " damage (" + health + " HP left).");
    }

    public boolean isAlive() {
        return health > 0;
    }
}