package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.util.ArrayList;

public class Shop extends Room{
    private ArrayList<Item> stock;

    //Anita Modified getName() --> getItemName

    private String item_ID;
    private String name;
    private String type;
    private String rarity;
    private int cost;

  /*  public Shop(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop, ArrayList<Item> stock) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.stock = stock;
    }
*/
    public Shop(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop, ArrayList<Item> stock, String item_ID, String name, String type, String rarity, int cost) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.item_ID = item_ID;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.cost = cost;
        this.stock = stock;
    }



    public int getCost() { return cost; }

    public void addItemToStock(Item item){
        stock.add(item);
    }


    public ArrayList<Item> getStock() {
        return stock;
    }



}
