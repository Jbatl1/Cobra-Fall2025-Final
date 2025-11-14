package Model.Rooms;

//change planet to landing site, no collection, add desc to landing site (planet desc, boolean isAtlandingsite,
//get other connections from other landing sites , and other rooms in that landing
//hashmap for landsite connections and hashmap for rooms and landsite connections
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LandingSite {
    private String planetName;  // The planet this landing site belongs to
    private String landingSiteName; // Name of the landing site itself
    private String desc; // Description of this landing site
    private boolean isAtLandingSite; // True if the player is currently at this site

    // Connections to other landing sites and rooms
    private Map<String, LandingSite> landingSiteConnections;
    private Map<String, Room> roomConnections;

    public LandingSite(String planetName, String landingSiteName, String desc) {
        this.planetName = planetName;
        this.landingSiteName = landingSiteName;
        this.desc = desc;
        this.isAtLandingSite = false;

        this.landingSiteConnections = new HashMap<>();
        this.roomConnections = new HashMap<>();
    }

    // --- Getters and Setters ---

    public String getPlanetName() {
        return planetName;
    }

    public String getLandingSiteName() {
        return landingSiteName;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isAtLandingSite() {
        return isAtLandingSite;
    }

    public void setAtLandingSite(boolean atLandingSite) {
        this.isAtLandingSite = atLandingSite;
    }

    // --- Connections ---

    public void addLandingSiteConnection(LandingSite site) {
        landingSiteConnections.put(site.getLandingSiteName(), site);
    }

    public void addRoomConnection(Room room) {
        roomConnections.put(room.getRoomID(), room);
    }

    public Map<String, LandingSite> getLandingSiteConnections() {
        return landingSiteConnections;
    }

    public Map<String, Room> getRoomConnections() {
        return roomConnections;
    }

    // --- Display Information ---
    public void printLandingSiteInfo() {
        System.out.println("Planet: " + planetName);
        System.out.println("Landing Site: " + landingSiteName);
        System.out.println("Description: " + desc);

        System.out.println("\nConnected Landing Sites:");
        if (landingSiteConnections.isEmpty()) {
            System.out.println("  None");
        } else {
            for (String name : landingSiteConnections.keySet()) {
                System.out.println("  - " + name);
            }
        }

        System.out.println("\nConnected Rooms:");
        if (roomConnections.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Room room : roomConnections.values()) {
                System.out.println("  - " + room.getRoomName());
            }
        }
    }
}
