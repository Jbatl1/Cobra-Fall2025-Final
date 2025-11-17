package Model.Rooms;

//change planet to landing site, no collection, add desc to landing site (planet desc, boolean isAtlandingsite,
//get other connections from other landing sites, and other rooms in that landing
//hashmap for landsite connections and hashmap for rooms and landsite connections
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LandingSite extends Room{ //Caleb Butler & My Tran
    // Connections to other landing sites and rooms
    private Map<String, LandingSite> landingSiteConnections;

    public LandingSite(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, boolean isRaider, boolean isShop) { // Caleb
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited, isRaider, isShop);
        this.landingSiteConnections = new HashMap<>();
    } //Caleb Butler & My Tran

    // --- Getters and Setters --- //Caleb Butler
    public Map<String, LandingSite> getLandingSiteConnections() { // My
        return landingSiteConnections;
    }

    public void addLandingSiteConnection(String direction, LandingSite landingSite) { //Caleb Butler
        landingSiteConnections.put(direction, landingSite);
    }


    public void loadLandingSiteConnections(Map<String, Room> connections) { // Caleb Butler
        for (Room room : connections.values() ) {
            if (room instanceof LandingSite && !room.getRoomName().equals(this.getRoomName())) {
                landingSiteConnections.put(room.getRoomName(), (LandingSite) room);
            }
        }
    }
}
