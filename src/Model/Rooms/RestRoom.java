package Model.Rooms;

import Model.Entities.Player;
import View.View;

public class RestRoom extends Room {
    private int restAmount;
    private int restDelay;

    public RestRoom(String id, String name, String description, int restAmount, int restDelay) {
        super(id, name, description);
        this.restAmount = restAmount;
        this.restDelay = restDelay;
    }

    public boolean rest(Player player, View view) {
        view.displayRestStart();

        try {
            Thread.sleep(getRestDelay() * 1000L); // use getter
        } catch (InterruptedException e) {
            view.displayRestInterrupted();
            return false;
        }

        player.heal(getRestAmount()); // use getter

        view.displayRestComplete(getRestAmount()); // use getter again
        return true;
    }

    public int getRestAmount() {
        return restAmount;
    }

    public int getRestDelay() {
        return restDelay;
    }
}