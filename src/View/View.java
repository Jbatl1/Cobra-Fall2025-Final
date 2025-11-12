package View;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;
import Model.Rooms.RestRoom;
import Model.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class View {
    private Scanner scanner;

    public View() {
        scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.nextLine().trim().toUpperCase();
    }

    // ==============================
    // General / Utility Output
    // ==============================

    public void displayInvalidCommand() {
        System.out.println("❗ Invalid command. Please try again.");
    }

    public void printSeparator() {
        System.out.println("===============================");
    }


    // ==============================
    // Puzzle
    // ==============================

    public void displayPuzzleStart() {
        System.out.println("Strange runes glow on the wall.\nA voice whispers: 'Answer correctly, or face the consequences.'");
    }

    public void displayPuzzle(Puzzle puzzle) {
        System.out.println(puzzle.toString());
    }

    public void displayBoundaryPuzzle(Puzzle puzzle) {
        System.out.println(puzzle.toString());
    }

    public void displayPuzzleSolved() {
        System.out.println("The runes fade away. You feel a surge of power as the puzzle unlocks something within you.");
    }

    public void displayPuzzleIncorrect(int attemptsLeft) {
        System.out.println("The runes flash red. An ominous feeling washes over you. You have " + attemptsLeft + " attempts left.");
    }

    public void displayPuzzleFailed() {

    }

    public void displayPuzzleAttempt(int x, int attemptsLeft) {
        switch (x) {
            case 1:
                System.out.println("✅ Correct! The path is now clear.");
                break;
            case -1:
                System.out.println("❌ Incorrect. you have, " + attemptsLeft + " attempts left.");
                break;
        }
    }



    // ==============================
    // Room / Exploration Output
    // ==============================
    private void displayRoomEntry(Room room) {
        printSeparator();
        System.out.println("🏠 You enter: " + room.getRoomName());
        System.out.println(room.getRoomDescription());
        System.out.println("Items here: " + (room.getRoomItems().isEmpty() ? "None" : room.getRoomItems().size()));
        System.out.println("Monsters here: " + (room.getMonsters().isEmpty() ? "None" : room.getMonsters().size()));
        printSeparator();
    }
    public void enterRoom(int x, String direction, Room room){
        switch (x) {
            case 1:
                System.out.println("You traveled " + direction);
                displayRoomEntry(room);
                break;
            case -1:
                System.out.println("You can't go " + direction + " from here.");
                break;
            case -2:
                System.out.println("There is a boundry puzzle blocking your way " + direction + ". Solve it to proceed.");
                break;
        }
    }

    public void displayExploreRoom(Room room) {
        System.out.println("🔍 Exploring " + room.getRoomName() + ":");
        System.out.println(room.getRoomDescription());
    }

    public void displayOpenShop() {
        System.out.println("🛒 Welcome to the Shop! you may: ");
        System.out.println("""
                - VIEW ITEMS
                - BUY <item name>
                - SELL <item name>
                - EXIT
                """);

    }

    public void displayNotInShop() {
        System.out.println("❗ You are not in a shop.");
    }

    public void displayExitShop() {
        System.out.println("👋 You exit the shop.");
    }

    // ==============================
    // Rest / Healing Output
    // ==============================
    public void displayRestStart(RestRoom room) {
        System.out.println("💤 You take a moment to rest...");
        System.out.println("(Resting will take " + room.getRestDelay() + " seconds)");
    }

    public void displayRestInterrupted() {
        System.out.println("⚠️ You couldn’t get comfortable. Rest interrupted!");
    }

    public void displayRestComplete(int amountHealed, int currentHealth) {
        System.out.println("✨ You feel refreshed! +" + amountHealed + " HP restored.");
        System.out.println("Your current health is now: " + currentHealth);
    }

    // ==============================
    // Inventory / Items
    // ==============================
    public void displayItemPickup(int x, String itemName) {
        switch (x) {
            case 1:
                System.out.println("📦 You picked up: " + itemName);
                break;
            case -1:
                System.out.println("❗ You can't pick up " + itemName + ". It's not here.");
                break;
        }
    }

    public void displayItemDropped(int x, String itemName) {
        switch (x) {
            case 1:
                System.out.println("🗑️ You dropped: " + itemName);
                break;
            case -1:
                System.out.println("❗ You can't drop " + itemName + ". You don't have it.");
                break;
        }
    }

    public void displayItemDestroyed(Item item) {
        System.out.println("🔥 You destroyed: " + item.getItemName());
    }

    public void displayEquipItem(int x, String itemName) {
        switch (x) {
            case 1:
                System.out.println("🛠️ You equipped: " + itemName);
                break;
            case -1:
                System.out.println("❗ You can't equip " + itemName + ". You don't have it.");
                break;
            case -2:
                System.out.println("❗ " + itemName + " cannot be equipped.");
                break;
        }
    }

    public void displayExamineItem(Item i) {
        if (i != null) {
            System.out.println(i.getItemEffect());
        } else {
            System.out.println("❗ Item not found in inventory.");
        }
    }

    public void displayToolbelt(java.util.List<Item> toolBelt) {
        System.out.println("🧰 Tool Belt Items:");
        if (toolBelt.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (Item item : toolBelt) {
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    public void displayInv(ArrayList<Item> inv) {
        if (inv.isEmpty()) {
            System.out.println("❗ You have no items in your inventory.");
        } else {
            System.out.println("🎒 Inventory Items:");
            for (Item item : inv) {
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    public void displayShipInv(ArrayList<Item> shipInv) {
        if (shipInv.isEmpty()) {
            System.out.println("❗ Your ship's inventory is empty.");
        } else {
            System.out.println("🚀 Ship Inventory Items:");
            for (Item item : shipInv) {
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    // ==============================
    // Monsters / Fights
    // ==============================

    public void displayMonsterNotFound(String s) {
        System.out.println("❗ No monster named " + s + " found here.");
    }

    public void displayInspectMonster(Monster monster) {
        System.out.println("👹 Monster Info:");
        System.out.println("  Name: " + monster.getName());
        System.out.println("  Description: " + monster.getAbilityEffect());
        System.out.println("  Health: " + monster.getHealth());
        System.out.println("  Attack Power: " + monster.getAttackPower());
    }

    public void displayFightStart(String monsterName) {
        System.out.println("⚔️ A wild " + monsterName + " appears! Prepare for battle!");
    }

    public void displayFightIgnored(String monsterName) {
        System.out.println("❕ You chose to ignore the " + monsterName + ". It watches you warily...");
    }

    public void displayPlayerAttack(String monsterName, int damage) {
        System.out.println("You attack " + monsterName + " for " + damage + " damage!");
    }

    public void displayMonsterAttack(String monsterName, int damage) {
        System.out.println(monsterName + " strikes back for " + damage + " damage!");
    }

    public void displayVictory(String monsterName, Item reward) {
        System.out.println("🏆 You defeated " + monsterName + "!");
        if (reward != null) {
            System.out.println("You received " + reward.getItemName() + " as a reward!");
        }
    }

    public void displayDefeat() {
        System.out.println("💀 You were defeated! You lost your equipped item...");
        System.out.println("Find a safe place to recover and try again.");
    }

    public void displayFightSummary(Player player, Monster monster) {
        System.out.println("⚔️ Combat Summary:");
        System.out.println("  " + player.getClass().getSimpleName() + " HP: " + player.getHealth());
        System.out.println("  " + monster.getName() + " HP: " + monster.getHealth());
        printSeparator();
    }




    // Store all dialogues here
    private final HashMap<String, String[]> dialogues = new HashMap<>();

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
    }

    // ========================
    // METHODS
    // ========================

    // Plays a dialogue sequence
    public void play(String key) {
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