package Model.Items;

public class Artifact extends Item{
    private String memory;

    public Artifact(String id, String name, String description, String rarity, String memory) {
        super(id, name, description, rarity);
        this.memory = memory;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
