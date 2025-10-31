package Model.Items;

public class Weapon extends Item{
    private int damage;
    private int durability;

    public Weapon(String id, String name, String description, String rarity, int damage, int durability) {
        super(id, name, description, rarity);
        this.damage = damage;
        this.durability = durability;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
