package Model.Rooms;

import Model.Entities.Player;
import Model.Items.Item;
import java.sql.*;
import Model.DatabaseConnection;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CrashedShip {
    private Room crashSite;
    private ArrayList<Item> storage;

    public CrashedShip() {
        this.storage=new ArrayList<>();
    }

    public void loadFromDatabase() {
        try (Connection conn = DatabaseConnection.connect()) {

            if (conn == null) {
                System.err.println("❌ Could not connect to database.");
                return;
            }

            String sql = "SELECT id, name, description FROM rooms WHERE LOWER(type) = 'crash site'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                crashSite = new Room(id);
                crashSite.loadFromDatabase();

                System.out.println("🛸 Crashed ship located at crash site room: " + crashSite.getName());
            } else {
                System.err.println("⚠️ No crash site room found in database.");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error loading crash site from DB: " + e.getMessage());
            e.printStackTrace();
        }
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
                System.out.println("You added " + item.getName() + " to the inventory");
                return true;
            }
        }
        System.out.println("Item not found");
        return false;
    }
    public void listItems(){
        for(int i =0;i<storage.size();i++){
            System.out.println(storage.get(i).getName());
        }
    }
    public Room getCrashSite(){
        return crashSite;
    }
}
