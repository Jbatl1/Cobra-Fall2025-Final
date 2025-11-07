package View;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Rooms.Room;
import Model.Rooms.RestRoom;
import Model.Items.Item;

public class View {

    // ==============================
    // General / Utility Output
    // ==============================
    public static void printMessage(String msg) {
        System.out.println(msg);
    }

    public static void printSeparator() {
        System.out.println("===============================");
    }

    // ==============================
    // Combat Output
    // ==============================
    public static void displayFightStart(String monsterName) {
        System.out.println("⚔️ A wild " + monsterName + " appears! Prepare for battle!");
    }

    public static void displayFightIgnored(String monsterName) {
        System.out.println("❕ You chose to ignore the " + monsterName + ". It watches you warily...");
    }

    public static void displayPlayerAttack(String monsterName, int damage) {
        System.out.println("You attack " + monsterName + " for " + damage + " damage!");
    }

    public static void displayMonsterAttack(String monsterName, int damage) {
        System.out.println(monsterName + " strikes back for " + damage + " damage!");
    }

    public static void displayVictory(String monsterName, Item reward) {
        System.out.println("🏆 You defeated " + monsterName + "!");
        if (reward != null) {
            System.out.println("You received " + reward.getName() + " as a reward!");
        }
    }

    public static void displayDefeat(Player player) {
        System.out.println("💀 You were defeated! You lost your equipped item...");
        System.out.println("Current HP: " + player.getHealth());
    }

    public static void displayFightSummary(Player player, Monster monster) {
        System.out.println("⚔️ Combat Summary:");
        System.out.println("  " + player.getClass().getSimpleName() + " HP: " + player.getHealth());
        System.out.println("  " + monster.getName() + " HP: " + monster.getHealth());
        printSeparator();
    }

    // ==============================
    // Room / Exploration Output
    // ==============================
    public static void displayRoomEntry(Room room) {
        printSeparator();
        System.out.println("🏠 You enter: " + room.getName());
        System.out.println(room.getDescription());
        System.out.println("Items here: " + (room.getItems().isEmpty() ? "None" : room.getItems().size()));
        System.out.println("Monsters here: " + (room.getMonsters().isEmpty() ? "None" : room.getMonsters().size()));
        printSeparator();
    }

    // ==============================
    // Rest / Healing Output
    // ==============================
    public static void displayRestStart(RestRoom room) {
        System.out.println("💤 You take a moment to rest...");
        System.out.println("(Resting will take " + room.getRestDelay() + " seconds)");
    }

    public static void displayRestInterrupted() {
        System.out.println("⚠️ You couldn’t get comfortable. Rest interrupted!");
    }

    public static void displayRestComplete(int amountHealed, int currentHealth) {
        System.out.println("✨ You feel refreshed! +" + amountHealed + " HP restored.");
        System.out.println("Your current health is now: " + currentHealth);
    }

    // ==============================
    // Inventory / Items
    // ==============================
    public static void displayItemPickup(Item item) {
        System.out.println("📦 You picked up: " + item.getName());
    }

    public static void displayItemDropped(Item item) {
        System.out.println("🗑️ You dropped: " + item.getName());
    }

    public static void displayItemDestroyed(Item item) {
        System.out.println("🔥 You destroyed: " + item.getName());
    }
}