package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.util.ArrayList;

public class Shop extends Room{
    private ArrayList<Item> stock;

    //Anita Modified getName() --> getItemName

    public Shop(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop, ArrayList<Item> stock) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.stock = stock;
    }

    public void addItemToStock(Item item){
        stock.add(item);
    }

 public void displayStock(){
        System.out.println("Items for sale:");
        for (Item item : stock){
            System.out.println("- " + item.getItemName());
        }
    }
    public boolean buyItem(Player player, Item item) {
        if (stock.contains(item)) {
            player.addToInventory(item);
            stock.remove(item);
            System.out.println("You bought " + item.getItemName() + "!");
            return true;
        }
        System.out.println("Item not found.");
        return false;
    }

    public void sellItem(Player player, Item item) {
        if (player.removeFromInventory(item)) {
            stock.add(item);
            System.out.println("You sold " + item.getItemName() + ".");
        }
    }



}
