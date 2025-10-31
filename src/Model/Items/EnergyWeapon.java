package Model.Items;

public class EnergyWeapon extends Weapon{
    private int charge;
    private int maxCharge;
    public EnergyWeapon(String id, String name, String description, String rarity, int damage, int durability, int maxCharge) {
        super(id, name, description, rarity, damage, durability);
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxcCharge) {
        this.maxCharge = maxcCharge;
    }
}
