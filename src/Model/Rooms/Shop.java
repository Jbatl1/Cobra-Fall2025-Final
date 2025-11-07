package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.util.ArrayList;

public class Shop extends Room{
    private ArrayList<Item> stock;

    public Shop(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, ArrayList<Item> stock) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited);
        this.stock = new ArrayList<>();
    }

    public void addItemToStock(Item item){
        stock.add(item);
    }

   /* public void displayStock(){
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
    }*/


}
