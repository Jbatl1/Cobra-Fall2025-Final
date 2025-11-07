package Model.Rooms;

import Model.Entities.Player;

public class RestRoom extends Room {
    private int restAmount;
    private int restDelay;

    public RestRoom(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, int restAmount, int restDelay) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited);
        this.restAmount = restAmount;
        this.restDelay = restDelay;
    }

    /*public void rest(Player player) {
        System.out.println("You take a moment to rest...");
        try {
            Thread.sleep(restDelay * 1000);
        } catch (InterruptedException e) {
            System.out.println("You couldn't get comfortable.");
        }

        player.heal(restAmount);
        System.out.println("You feel refreshed. +" + restAmount + " HP restored.");
    }
*/
    public int getRestAmount() { return restAmount; }
    public int getRestDelay() { return restDelay; }
}