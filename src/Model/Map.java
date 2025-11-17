package Model;

import Model.Entities.Player;
import Model.Rooms.Room;

import java.io.Serializable;
import java.util.HashMap;

public class Map implements Serializable {

    // === UML Fields ===
    private Room playerLocation;
    private Player player;

    // === Getter Methods (from UML) ===
    public Room getPlayerLocation() {
        return playerLocation;
    }

    public Player getPlayer() {
        return player;
    }

    // === Optional: Database Test ===





}