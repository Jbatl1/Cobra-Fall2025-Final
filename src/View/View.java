package View;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Puzzles.Puzzle;
import Model.Rooms.Room;
import Model.Items.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
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

    public void displayDefeat(Player player) {  // Caleb
        System.out.println("💀 You were defeated! You lost your equipped item...");
        System.out.println("Current HP: " + player.getHealth());
    }

    public void displayFightSummary(Player player, Monster monster) {  // Caleb
        System.out.println("⚔️ Combat Summary:");
        System.out.println("  " + player.getClass().getSimpleName() + " HP: " + player.getHealth());
        System.out.println("  " + monster.getName() + " HP: " + monster.getHealth());
        printSeparator();
    }

    public void displayDefeat() {  // Caleb
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

    public void displayExploreRoom(Room room) {  // Caleb
        System.out.println("🔍 Exploring " + room.getRoomName() + ":");
        System.out.println(room.getRoomDescription());
        //System.out.println(room.getMonsters1(room));

        List<String> monsterNames = room.getMonsterNames();
        if (monsterNames.isEmpty()) {

        } else {
            System.out.println("Monsters here: " + monsterNames);
        }

        List<String> puzzleQuestions = room.getPuzzleNames();
        if (puzzleQuestions.isEmpty()) {

        } else {
            System.out.println("MYSTERY ITEM: (" + puzzleQuestions + ") \n");
        }

        List<String> itemNames = room.getItemPresent();
        if (itemNames.isEmpty()) {

        } else {
            System.out.println("Items here: " + itemNames);
        }



        printSeparator();
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

    public void displayStock(Map<Item, Integer> stock) { // Kai
        System.out.println("Items for sale:");
        for (Map.Entry<Item, Integer> entry : stock.entrySet()) {
            Item item = entry.getKey();
            Integer price = entry.getValue();
            System.out.println("- " + item.getItemName() + ": " + price + " gold");
        }
    }
    public void displaySellItem(int x, String s) {  // Caleb
        if (x < 0) {
            System.out.println("❗ You don't have " + s + " to sell.");
        } else {
            System.out.println("💰 You sold " + s + " for " + x + " gold.");
        }
    }


    public void displayPurchaseItem(int x, String s) {  // Caleb
        if (x < 0) {
            System.out.println("❗ You don't have enough gold to buy" + s + ".");
        } else {
            System.out.println("💰 You purchased " + s + " for " + x + " gold.");
        }
    }
    public void displayNotInShop() { //caleb
        System.out.println("❗ You are not in a shop.");
    }

    public void displayExitShop() { //caleb
        System.out.println("👋 You exit the shop.");
    }

    public void displayNotAtLandingSite() {  // Caleb
        System.out.println("❗ You can only travel at a landing site!");
    }

    public void displayLandingSiteNotFound(String siteName) {  // Caleb
        System.out.println("❗ You can't travel to " + siteName + " from here!");
    }

    // ==============================
    // Rest / Healing Output
    // ==============================
    public void displayRest(int before, int after) {  // Caleb
        System.out.println("💤 You take a moment to rest...");
        System.out.println("Your health went from " + before + " to " + after + ".");
    }


    // ==============================
    // Inventory / Items
    // ==============================

    public void displayItemNotFound(Item item ){
        System.out.println("Sorry could not find item");
    }
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
            System.out.println(i.getItemDescription());
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
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    public void displayInv(ArrayList<Item> inv) { //caleb
        if (inv.isEmpty()) {
            System.out.println("❗ You have no items in your inventory.");
        } else {
            System.out.println("🎒 Inventory Items:");
            for (Item item : inv) {
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    public void displayShipInv(ArrayList<Item> shipInv) { //caleb
        if (shipInv.isEmpty()) {
            System.out.println("❗ Your ship's inventory is empty.");
        } else {
            System.out.println("🚀 Ship Inventory Items:");
            for (Item item : shipInv) {
                System.out.println("  - " + item.getItemName());
            }
        }
    }

    public void displayItemDestroyed(Item item) {System.out.println("🔥 You destroyed: " + item.getItemName());}

    // ==============================
    // Puzzle
    // ==============================


        public void displayPuzzleBeforePickup (Puzzle puzzle){ //Anita Philip
            System.out.println("You must solve a puzzle before you can pickup this Item! (Examine or Ignore)");
        }

        public void displayPuzzleChoice () {
            System.out.println("Type 'EXAMINE' to try solving the puzzle or 'IGNORE' to leave it.");
        }


        public void displayBoundaryPuzzlePrompt (Puzzle puzzle){ //Anita Philip
            System.out.println("❓: " + puzzle.getPuzzleQuestion());
        }

        public void displayLootPuzzlePrompt (Puzzle puzzle){//Anita Philip
            System.out.println("❓: " + puzzle.getPuzzleQuestion());
        }

        public void displayPuzzleSolved (Puzzle puzzle){//Anita Philip
            System.out.println("🧩 You have correctly solved the puzzle!");
            System.out.println("Item ♦️ " + puzzle.getReward().getItemName() + " has been added to your inventory! ");

        }

        public static void displayPuzzleIncorrect (Puzzle puzzle){//Anita Philip
            System.out.println("That answer is not correct! ");
        }

        public static void displayPuzzleLocked (Puzzle puzzle){//Anita Philip
            System.out.println("You have have failed to solve the puzzle! Leave and comeback to try again");
        }

        public void displayPuzzleBlockedPickup (Item i){//Anita Philip
            System.out.println("You cannot pick up " + i.getItemName() + " until the puzzle is solved.");
        }

        public void displayPuzzleQuestion(Puzzle puzzle){//Anita Philip
            System.out.println("Puzzle Question: " + puzzle.getPuzzleQuestion());
        }

        public void displayPuzzleIgnored (Puzzle p){//Anita Philip
            System.out.println("You decided to ignore the puzzle.");
        }

        public void displayPuzzleFailed (Puzzle p){//Anita Philip
            System.out.println("You failed to solve the puzzle. The item remains locked.");
        }

        public void displayReturnToPreviousRoom (Room m){//Anita Philip
            System.out.println("You have been moved back to previous room");
        }

        public void displayMessage () {//Anita Philip
            System.out.println("You cannot move back, no previous room recorded.");
        }


    public void displayStoreInShip(int x, String s) { // Caleb
        if (x == 1) {
            System.out.println("📦 You stored " + s + " in your ship's inventory.");
        } else if (x == -2) {
            System.out.println("❗ Your ship's inventory is full. Cannot store " + s + ".");
        }
        else if (x == -1) {
            System.out.println("❗ You don't have " + s + " in your inventory.");
        } else if (x == -3) {
            System.out.println("❗ You are not at a landing site.");
        }
    }

    public void displayStoreInCrashedShip(int x, String s) { // Caleb
        if (x == 1) {
            System.out.println("📦 You stored " + s + " in the crashed ship's storage.");
        } else if (x == -2) {
            System.out.println("❗ The crashed ship's storage is full. Cannot store " + s + ".");
        }
        else if (x == -1) {
            System.out.println("❗ You don't have " + s + " in your inventory.");
        } else if (x == -3) {
            System.out.println("❗ You are not at the crashed ship location.");
        }
    }

    public void displayGetFromCrashedShip(int x, String itemName) { // Caleb
        if (x == 1) {
            System.out.println("📦 You retrieved " + itemName + " from the crashed ship's storage.");
        }
        else if (x == -1) {
            System.out.println("❗ " + itemName + " is not in the crashed ship's storage.");
        }
        else if (x == -2) {
            System.out.println("❗ You are not at the crashed ship location.");
        }
        else if (x == -3) {
            System.out.println("❗ The crashed ship's storage is empty. Cannot retrieve " + itemName + ".");
        }
    }

    public void displayGetFromShip(int x, String item) {
        if (x == 1) {
            System.out.println("📦 You retrieved " + item + " from your ship's inventory.");
        }
        else if (x == -1) {
            System.out.println("❗ " + item + " is not in your ship's inventory.");
        }
        else if (x == -2) {
            System.out.println("❗ You are not at a landing site.");
        }
        else if (x == -3) {
            System.out.println("❗ Your ship's inventory is empty. Cannot retrieve " + item + ".");
        }
    }

    public void displayAddToToolBelt(int x, String itemName) { // Caleb
        if (x == 1) {
            System.out.println("🧰 You added " + itemName + " to your tool belt.");
        } else if (x == -1) {
            System.out.println("❗ You don't have " + itemName + " in your inventory.");
        } else if (x == -2) {
            System.out.println("❗ Your tool belt is full. Cannot add " + itemName + ".");
        } else if (x == -3) {
            System.out.println("❗ " + itemName + " is already in your tool belt.");
        }
    }

    public void displayRemoveFromToolBelt(int x, String item) {
        if (x == 1) {
            System.out.println("🧰 You removed " + item + " from your tool belt.");
        }
        else if (x == -1) {
            System.out.println("❗ You don't have " + item + " in your inventory.");
        }
        else if (x == -2) {
            System.out.println("❗ " + item + " is not in your tool belt.");
        }
    }
}