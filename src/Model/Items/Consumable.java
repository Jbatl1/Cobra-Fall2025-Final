package Model.Items;

public class Consumable extends Item{
    private int health;
    private double riskOfPoison;
    private int amount;

    public Consumable(String id, String name, String description, String rarity, int health, double riskOfPoison, int amount) {
        super(id, name, description, rarity);
        this.health = health;
        this.riskOfPoison = riskOfPoison;
        this.amount = amount;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getRiskOfPoison() {
        return riskOfPoison;
    }

    public void setRiskOfPoison(double riskOfPoison) {
        this.riskOfPoison = riskOfPoison;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
