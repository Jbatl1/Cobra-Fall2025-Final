package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;

import java.util.ArrayList;

public class CrashSite extends LandingSite{ //Caleb Butler

    private ArrayList<Item> shipStorage;

    public CrashSite(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop) { // Caleb
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.shipStorage = new ArrayList<>();
    } //Caleb Butler

    //Getters - //Caleb Butler
    public ArrayList<Item> getShipStorage() { // Caleb
        return shipStorage;
    }

    public void addShipStorageItem(Item item) { // Caleb
        shipStorage.add(item);
    }


}
