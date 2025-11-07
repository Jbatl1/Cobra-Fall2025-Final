package Model.Rooms;

import Model.Rooms.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Planet{
    private String name;
    private String desc;
    private Map<String, Room> rooms; //use room bc the player moves from rooms to rooms
    private Room landingSite;

    /*
    room has to inherit planet
     */
    public Planet(String planetName, String desc){
        this.name = planetName;
        this.desc = desc;
        this.rooms = new HashMap<>();
    }
    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public void addRoom(Room room){
        this.rooms.put(room.getId(), room);
    }
    public Room getRoom(Room roomId){
        return rooms.get(roomId);
    }
    public Collection<Room> getAllRooms(){ //planets is a collection of rooms
        return rooms.values();
    }
    public Room getLandingSite(){
        return landingSite;
    }
    public void setLandingSite(Room landingSite){
        this.landingSite = landingSite;
    }

    public void planetDesc{
        System.out.println("Planet name: " + name);
        System.out.println(desc);
        System.out.println("Landing site: " + landingSite != null ? landingSite.getName() : "None");
    }

}
