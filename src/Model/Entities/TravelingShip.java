package Model.Entities;

import Model.Items.Item;
import Model.Rooms.Planet;
import Model.Rooms.Room;
import java.util.HashMap;
import java.util.ArrayList;

public class TravelingShip extends Player {
    private ArrayList<Item> cargo;
    private int capacity = 20;
    private String currPlanet;
    private HashMap<String, Planet> planets;
    private boolean docked;

    public TravelingShip(Room startingRoom, Planet startingPlanet, int capacity) {
        super(startingRoom);
        this.planets = new HashMap<>();
        this.capacity = capacity;
        this.currPlanet = startingPlanet.getName();
        this.cargo = new ArrayList<>();
        this.docked = true;
    }

    //add planet
    public void addPlanet(Planet planet) {
        this.planets.put(planet.getName(), planet);
    }

    public Planet getPlanet(String planetName) {
        return this.planets.get(planetName);
    }

    //check if the traveling ship is at the landing site
    public boolean isAtLandingSite() {
        return getCurrRoom().getName().equalsIgnoreCase("Landing site");
    }

    public boolean isDocked() {
        return this.docked;
    }

    //move to planet
    public boolean travelToPlanet(String planetName) {
        if (!isAtLandingSite()) {
            System.out.println("You must be at the landing site to do this.");
            return false;
        }
        Planet destination = getPlanet(planetName);
        if (destination == null) {
            System.out.println("Uknown planet");
            return false;
        }
        Room landingSite = destination.getLandingSite();
        if (landingSite == null) {
            System.out.println("No landing site's name on: " + planetName);
            return false;
        }
        setCurrRoom(landingSite);
        this.currPlanet = destination.getName();
        this.docked = true;

        System.out.println("You have arrived at " + currPlanet);
        return true;
    }

    // add items to ship's storage
    public boolean addCargoItem(Item item) {
        if (cargo.size() > capacity) {
            System.out.println("Storage is full!");
            return false;
        }
        cargo.add(item);
        return true;
    }

    public boolean removeCargoItem(String itemName) {
        for (Item i : cargo) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                cargo.remove(i);
                System.out.println("Item " + itemName + " has been removed!");
                return true;
            }
        }
        System.out.println("No item name " + itemName);
        return false;

    }

    public void listCargo() {
        System.out.println("=== Cargo Hold ===");
        if (cargo.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Item i : cargo) {
                System.out.println("- " + i.getName());
            }

        }
    }
    /*
     private void setCurrRoom(Room newRoom) {
        try {
            java.lang.reflect.Field field = Player.class.getDeclaredField("currRoom");
            field.setAccessible(true);
            field.set(this, newRoom);
        } catch (Exception e) {
            System.err.println("Error setting current room: " + e.getMessage());
        }
    }
     */
}

