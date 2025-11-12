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
            System.out.println("You received " + reward.getItemName() + " as a reward!");
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
        System.out.println("🏠 You enter: " + room.getRoomName());
        System.out.println(room.getRoomDescription());

        System.out.println("Items here: " + (room.getRoomItems().isEmpty() ? "None" : room.getRoomItems().size()));
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
        System.out.println("📦 You picked up: " + item.getItemName());
    }

    public static void displayItemDropped(Item item) {
        System.out.println("🗑️ You dropped: " + item.getItemName());
    }

    public static void displayItemDestroyed(Item item) {System.out.println("🔥 You destroyed: " + item.getItemName());}

    // ==============================
    // Puzzle
    // ==============================

    public static void displayPuzzleBeforePickup(Puzzle puzzle) {
        System.out.println("You must solve a puzzle before you can pickup this Item! (Examine or Ignore)");
    }

    public static void displayPuzzleChoice() {
        System.out.println("Type 'EXAMINE' to try solving the puzzle or 'IGNORE' to leave it.");
    }

    public static void displayPuzzleQuestion(Puzzle puzzle) {
        System.out.println("🧩 Puzzle: " + puzzle.getPuzzleQuestion());
    }



    public static void displayBoundaryPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }

    public static void displayLootPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }
    public static void displayItemPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }

    public static void displayPuzzleIgnored(Puzzle puzzle) {
      //  puzzle.view.displayRoomEntry()
    }

    public static void displayPuzzleSolved(Puzzle puzzle) {
        System.out.println("🧩 You have correctly solved the puzzle!");
        System.out.println("Item ♦️ " + puzzle.getReward().getItemName() + " has been added to your inventory! ");

    }

    public static void displayBoundaryPuzzleSolved(Puzzle puzzle) {
        System.out.println("You have correctly solved the puzzle!, You can now explore room");
        System.out.println("Item ♦️ " + puzzle.getReward().getItemName() + " has been added to your inventory! ");
    }

    public static void displayPuzzleIncorrect(Puzzle puzzle) {
        System.out.println("That answer is not correct! ");
    }

    public static void displayBoundaryPuzzleIncorrect(Puzzle puzzle) {
        System.out.println("That answer is not correct!");
    }

    public static void displayPuzzleFailed(Puzzle puzzle) {
        System.out.println("That answer is not correct! ");
    }

    public static void displayPuzzleLocked(Puzzle puzzle) {
        System.out.println("You have have failed to solve the puzzle! Leave and comeback to try again");
    }


}