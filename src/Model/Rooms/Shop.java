/*
package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.util.ArrayList;

public class Shop extends Room{
    private ArrayList<Item> stock;

    public Shop(Strimg id, String name, String description) {
        super(id, name, description);
        this.stock = new ArrayList<>();
    }
    public void addItemToStock(Item item){
        stock.add(item);
    }
    public void displayStock(){
        System.out.println("Items for sale:");
        for (Item item : stock){
            System.out.println("- " + item.getName());
        }
    }
    public boolean buyItem(Player player, Item item) {
        if (stock.contains(item)) {
            player.addToInventory(item);
            stock.remove(item);
            System.out.println("You bought " + item.getName() + "!");
            return true;
        }
        System.out.println("Item not found.");
        return false;
    }

    public void sellItem(Player player, Item item) {
        if (player.removeFromInventory(item)) {
            stock.add(item);
            System.out.println("You sold " + item.getName() + ".");
        }
    }
}
*/
