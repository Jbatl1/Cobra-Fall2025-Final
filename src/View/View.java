package View;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Rooms.Room;
import Model.Rooms.RestRoom;
import Model.Items.Item;

import java.util.HashMap;

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
    public void enterRoom(int x, String direction, String roomName){
        switch (x) {
            case 1:
                System.out.println("You traveled " + direction + " to room " + roomName + ".");
                break;
            case -1:
                System.out.println("You can't go " + direction + " from here.");
                break;
            case -2:
                System.out.println("There is a boundry puzzle blocking your way " + direction + ". Solve it to proceed.");
                break;
        }
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

    // Store all dialogues here
    private static final HashMap<String, String[]> dialogues = new HashMap<>();

    static {
        // Raider Camp
        dialogues.put("raider_intro", new String[]{
                "You approach the Raider Camp.",
                "The smell of smoke and oil hangs in the air.",
                "A Raider steps forward: 'Hand over your supplies, stranger!'"
        });

        dialogues.put("raider_victory", new String[]{
                "The Raider leader falls to the ground.",
                "You’ve cleared the camp and taken their supplies."
        });

        // Shop
        dialogues.put("shop_intro", new String[]{
                "Merchant: 'Welcome, traveler!'",
                "Merchant: 'We have everything from potions to plasma rifles!'"
        });

        // Rest Room
        dialogues.put("rest_start", new String[]{
                "You find a quiet place to rest.",
                "Warmth returns to your limbs.",
                "Your health slowly recovers..."
        });

        // Puzzle
        dialogues.put("puzzle_start", new String[]{
                "Strange runes glow on the wall.",
                "A voice whispers: 'Answer correctly, or face the consequences.'"
        });

        dialogues.put("puzzle_solved", new String[]{
                "The runes fade away.",
                "You feel a surge of power as the puzzle unlocks something within you."
        });
    }

    // ========================
    // METHODS
    // ========================

    // Plays a dialogue sequence
    public static void play(String key) {
        if (!dialogues.containsKey(key)) {
            System.out.println("[No dialogue found for key: " + key + "]");
            return;
        }

        String[] lines = dialogues.get(key);
        for (String line : lines) {
            System.out.println(line);
            try {
                Thread.sleep(1200); // short pause between lines
            } catch (InterruptedException ignored) {
            }
        }
    }

    // Returns the dialogue lines (if needed elsewhere)
    public static String[] get(String key) {
        return dialogues.getOrDefault(key, new String[]{"[Dialogue not found]"});
    }
}