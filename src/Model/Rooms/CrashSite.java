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
        System.out.println(item.getItemName() + "stored");
    }

    public ArrayList<Item> getItems(){
        return storage;
    }
    public boolean retrieveItems(String itemName, Player player){
        for(int i =0;i<storage.size();i++){
            if(storage.get(i).getItemName().equalsIgnoreCase(itemName)){
                Item item = storage.remove(i);
                player.getInventory().add(item);
                System.out.println("You added " + item.getItemName() + " to the inventory");
                return true;
            }
        }
        System.out.println("Item not found");
        return false;
    }
    public void listItems(){
        for(int i =0;i<storage.size();i++){
            System.out.println(storage.get(i).getItemName());
        }
    }
    public Room getCrashSite(){
        return crashSite;
    }
}
