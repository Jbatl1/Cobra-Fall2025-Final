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
    public static void printMessage(String msg) { //caleb
        System.out.println(msg);
    }

    public void displayInvalidCommand() { //caleb
        System.out.println("❗ Invalid command. Please try again.");
    }

    public void printSeparator() {
        System.out.println("===============================");
    }

    // ==============================
    // Combat Output
    // ==============================

    public void displayMonsterNotFound(String s) { //caleb
        System.out.println("❗ No monster named " + s + " found here.");
    }

    public void displayInspectMonster(Monster monster) { //caleb
        System.out.println("👹 Monster Info:");
        System.out.println("  Name: " + monster.getName());
        System.out.println("  Description: " + monster.getDescription());
        System.out.println("  Health: " + monster.getHealth());
        System.out.println("  Attack Power: " + monster.getAttackPower());
    }

    public void displayFightStart(String monsterName) { //caleb
        System.out.println("⚔️ A wild " + monsterName + " appears! Prepare for battle!");
    }

    public void displayFightIgnored(String monsterName) { //caleb
        System.out.println("❕ You chose to ignore the " + monsterName + ". It watches you warily...");
    }

    public void displayPlayerAttack(String monsterName, int damage) { //caleb
        System.out.println("You attack " + monsterName + " for " + damage + " damage!");
    }

    public void displayMonsterAttack(String monsterName, int damage) { //caleb
        System.out.println(monsterName + " strikes back for " + damage + " damage!");
    }

    public void displayVictory(String monsterName, Item reward) { //caleb
        System.out.println("🏆 You defeated " + monsterName + "!");
        if (reward != null) {
            System.out.println("You received " + reward.getItemName() + " as a reward!");
        }
    }

    public void displayFlee(String monsterName) { //caleb
        System.out.println("🏃 You fled from " + monsterName + "!");
    }

    public void displayDefeat(Player player) {
        System.out.println("💀 You were defeated! You lost your equipped item...");
        System.out.println("Current HP: " + player.getHealth());
    }

    public void displayFightSummary(Player player, Monster monster) {
        System.out.println("⚔️ Combat Summary:");
        System.out.println("  " + player.getClass().getSimpleName() + " HP: " + player.getHealth());
        System.out.println("  " + monster.getName() + " HP: " + monster.getHealth());
        printSeparator();
    }

    public void displayDefeat() {
        System.out.println("💀 You were defeated! You lost your equipped item...");
        System.out.println("Find a safe place to recover and try again.");
    }

    // ==============================
    // Room / Exploration Output
    // ==============================
    public void displayRoomEntry(Room room) { //caleb
        printSeparator();
        System.out.println("🏠 You enter: " + room.getRoomName());
        System.out.println(room.getRoomDescription());

        System.out.println("Items here: " + (room.getRoomItems().isEmpty() ? "None" : room.getRoomItems().size()));
        System.out.println("Monsters here: " + (room.getMonsters().isEmpty() ? "None" : room.getMonsters().size()));
        printSeparator();
    }
    public void displayNoExit() { //caleb
        System.out.println("🚫 There is no exit in that direction!");
    }

    public void displayExploreRoom(Room room) {
        System.out.println("🔍 Exploring " + room.getName() + ":");
        System.out.println(room.getDescription());
    }

    public void displayOpenShop() { //caleb
        System.out.println("🛒 Welcome to the Shop! you may: ");
        System.out.println("""
                - VIEW ITEMS
                - BUY <item name>
                - SELL <item name>
                - EXIT
                """);

    }

    public void displayNotInShop() { //caleb
        System.out.println("❗ You are not in a shop.");
    }

    public void displayExitShop() { //caleb
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
    public void displayItemPickup(int x, String itemName) { //caleb
        switch (x) {
            case 1:
                System.out.println("📦 You picked up: " + itemName);
                break;
            case -1:
                System.out.println("❗ You can't pick up " + itemName + ". It's not here.");
                break;
        }
    }

    public void displayItemDropped(int x, String itemName) { //caleb
        switch (x) {
            case 1:
                System.out.println("🗑️ You dropped: " + itemName);
                break;
            case -1:
                System.out.println("❗ You can't drop " + itemName + ". You don't have it.");
                break;
        }
    }

    public void displayEquipItem(int x, String itemName) { //caleb
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

    public void displayExamineItem(Item i) { //caleb
        if (i != null) {
            System.out.println(i.getDescription());
        } else {
            System.out.println("❗ Item not found in inventory.");
        }
    }

    public void displayToolbelt(java.util.List<Item> toolBelt) { //caleb
        System.out.println("🧰 Tool Belt Items:");
        if (toolBelt.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (Item item : toolBelt) {
                System.out.println("  - " + item.getName());
            }
        }
    }

    public void displayInv(ArrayList<Item> inv) { //caleb
        if (inv.isEmpty()) {
            System.out.println("❗ You have no items in your inventory.");
        } else {
            System.out.println("🎒 Inventory Items:");
            for (Item item : inv) {
                System.out.println("  - " + item.getName());
            }
        }
    }

    public void displayShipInv(ArrayList<Item> shipInv) { //caleb
        if (shipInv.isEmpty()) {
            System.out.println("❗ Your ship's inventory is empty.");
        } else {
            System.out.println("🚀 Ship Inventory Items:");
            for (Item item : shipInv) {
                System.out.println("  - " + item.getName());
            }
        }
    }

    public void displayItemDestroyed(Item item) {System.out.println("🔥 You destroyed: " + item.getItemName());}

    // ==============================
    // Puzzle
    // ==============================

    public void displayPuzzleBeforePickup(Puzzle puzzle) {
        System.out.println("You must solve a puzzle before you can pickup this Item! (Examine or Ignore)");
    }

    public void displayPuzzleChoice() {
        System.out.println("Type 'EXAMINE' to try solving the puzzle or 'IGNORE' to leave it.");
    }

    public void displayPuzzleQuestion(Puzzle puzzle) {
        System.out.println("🧩 Puzzle: " + puzzle.getPuzzleQuestion());
    }



    public void displayBoundaryPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }

    public void displayLootPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }
    public void displayItemPuzzlePrompt(Puzzle puzzle) {
        System.out.println("❓: " + puzzle.getPuzzleQuestion() );
    }

    public void displayPuzzleIgnored(Puzzle puzzle) {
      //  puzzle.view.displayRoomEntry()
    }

    public void displayPuzzleSolved(Puzzle puzzle) {
        System.out.println("🧩 You have correctly solved the puzzle!");
        System.out.println("Item ♦️ " + puzzle.getReward().getItemName() + " has been added to your inventory! ");

    }

    public void displayBoundaryPuzzleSolved(Puzzle puzzle) {
        System.out.println("You have correctly solved the puzzle!, You can now explore room");
        System.out.println("Item ♦️ " + puzzle.getReward().getItemName() + " has been added to your inventory! ");
    }

    public void displayPuzzleIncorrect(Puzzle puzzle) {
        System.out.println("That answer is not correct! ");
    }

    public void displayBoundaryPuzzleIncorrect(Puzzle puzzle) {
        System.out.println("That answer is not correct!");
    }

    public void displayPuzzleFailed(Puzzle puzzle) {
        System.out.println("That answer is not correct! ");
    }

    public void displayPuzzleLocked(Puzzle puzzle) {
        System.out.println("You have have failed to solve the puzzle! Leave and comeback to try again");
    }


}