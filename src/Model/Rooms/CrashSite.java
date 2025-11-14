package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;

import java.util.ArrayList;

public class CrashSite {
    private Room crashSite;
    private ArrayList<Item> storage;

    public CrashSite() {
        this.storage=new ArrayList<>();
    }


    public void storeItems(Item item){
        storage.add(item);
        System.out.println(item.getName() + "stored");
    }

    public ArrayList<Item> getItems(){
        return storage;
    }
    public boolean retrieveItems(String itemName, Player player){
        for(int i =0;i<storage.size();i++){
            if(storage.get(i).getName().equalsIgnoreCase(itemName)){
                Item item = storage.remove(i);
                player.getInventory().add(item);
                return true;
            }
        }
        return false;
    }
    public void listItems(){
    }

    public Room getCrashSite(){
        return crashSite;
    }
}
