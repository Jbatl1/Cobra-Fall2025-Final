package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class CrashSite extends LandingSite implements Serializable {

    private ArrayList<Item> shipStorage;

    public CrashSite(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop) { // Caleb
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.shipStorage = new ArrayList<>();
    }

    public ArrayList<Item> getShipStorage() { // Caleb
        return shipStorage;
    }

    public void addShipStorageItem(Item item) { // Caleb
        shipStorage.add(item);
    }


}
