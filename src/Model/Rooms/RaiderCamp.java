/*
package Model.Rooms;

import Model.Entities.Player;
import Model.Entities.Monster;

public class RaiderCamp extends Room {
    private Monster raiderLeader;
    private boolean cleared;

    public RaiderCamp(String roomID, String roomName, String roomDescription, String roomType, String northNavigation, String eastNavigation, String southNavigation, String westNavigation, boolean roomVisited, Monster raiderLeader, boolean cleared) {
        super(roomID, roomName, roomDescription, roomType, northNavigation, eastNavigation, southNavigation, westNavigation, roomVisited);
        this.raiderLeader = raiderLeader;
        this.cleared = cleared;
    }
 public void enterCamp(Player player) {
        System.out.println("You approach the Raider Camp...");
        if (!cleared) {
            raidEncounter(player);
        } else {
            System.out.println("The camp lies in ruins. Nothing left here.");
        }
    }

    private void raidEncounter(Player player) {
        System.out.println("Raiders surround you! Do you FIGHT or NEGOTIATE?");
        // Could take input or use a decision tree system
        fight(player);
    }

    private void fight(Player player) {
        System.out.println("Combat begins!");
        player.attack(raiderLeader);
        if (!raiderLeader.isAlive()) {
            cleared = true;
            System.out.println("You’ve defeated the Raider Camp leader!");
        }
    }


    public boolean isCleared() { return cleared; }
}
*/
