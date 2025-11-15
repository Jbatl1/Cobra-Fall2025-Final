package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.util.ArrayList;
import java.util.HashMap;

public class Shop extends Room{
    private HashMap<Item, Integer> stockWithCost;

    public Shop(String roomID, String roomName, String roomDescription, String roomType,
                String northNavigation, String eastNavigation, String southNavigation, String westNavigation,
                boolean roomVisited, boolean isRaider, boolean isShop,
                HashMap<Item, Integer> stockWithCost) {
        super(roomID, roomName, roomDescription, roomType,
                northNavigation, eastNavigation, southNavigation, westNavigation,
                roomVisited, isRaider, isShop);
        this.stockWithCost = stockWithCost;
    }

    public HashMap<Item, Integer> getStockWithCost() {
        return stockWithCost;
    }

    public void setStockWithCost(HashMap<Item, Integer> stockWithCost) {
        this.stockWithCost = stockWithCost;
    }

    // Optional helper: get cost of a specific item
    public int getCost(Item item) {
        return stockWithCost.getOrDefault(item, 0);
    }

    // ✅ NEW METHOD: return just the items as a list
    public ArrayList<Item> getStock() {
        return new ArrayList<>(stockWithCost.keySet());
    }

}
