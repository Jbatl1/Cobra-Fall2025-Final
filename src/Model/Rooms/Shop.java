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


    public ArrayList<Item> getStock() {
        return stock;
    }



}
