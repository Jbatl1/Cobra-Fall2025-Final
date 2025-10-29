package Model.Items;

public class Material extends Item {
    private int damageIncrease;

    public Material(String id, String name, String description, String rarity, int damageIncrease) {
        super(id, name, description, rarity);
        this.damageIncrease = damageIncrease;
    }

    public int getDamageIncrease() {
        return damageIncrease;
    }

    public void setDamageIncrease(int damageIncrease) {
        this.damageIncrease = damageIncrease;
    }
}
