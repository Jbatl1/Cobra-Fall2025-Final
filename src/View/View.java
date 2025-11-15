package View;

import Model.Entities.Monster;
import Model.Entities.Player;
import Model.Puzzles.Puzzle;
import Model.Rooms.CrashSite;
import Model.Rooms.LandingSite;
import Model.Rooms.Room;
import Model.Items.Item;
import java.util.ArrayList;
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
        if (room instanceof LandingSite) { // assuming you have a subclass that links room to LandingSite
            LandingSite landingSite = (LandingSite) room;
            System.out.println("Planet: " + landingSite.getPlanetName());
            System.out.println("Landing Site: " + landingSite.getLandingSiteName());
            System.out.println("Description: " + landingSite.getDesc());
        } else {
            System.out.println("🔍 Exploring " + room.getRoomName() + ":");
            System.out.println(room.getRoomDescription());
        }
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

    public void displayStock(ArrayList<Item> stock) { // Kai
        System.out.println("Items for sale:");
        for (Item item : stock){
            System.out.println("- " + item.getItemName());
        }
    }
    public void displaySellItem(int x, String s) {
        if (x < 0) {
            System.out.println("❗ You don't have " + s + " to sell.");
        } else {
            System.out.println("💰 You sold " + s + " for " + x + " gold.");
        }
    }


    public void displayPurchaseItem(int x, String s) {
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

    public void displayNotAtLandingSite() {
        System.out.println("❗ You can travel at a landing site!");
    }

    public void displayLandingSiteNotFound(String siteName) {
        System.out.println("❗ You can't travel to " + siteName + " from here!");
    }

    // ==============================
    // Rest / Healing Output
    // ==============================
    public void displayRest(int before, int after) {
        System.out.println("💤 You take a moment to rest...");
        System.out.println("Your health went from " + before + " to " + after + ".");
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



    }
    //game save
    public void displayGameSaved() {
        System.out.println("Game saved successfully.");
    }
    //landing site
    public void displayLandingSiteInfo(LandingSite site) {
        System.out.println("Planet: " + site.getPlanetName());
        System.out.println("Landing Site: " + site.getLandingSiteName());
        System.out.println("Description: " + site.getDesc());

        System.out.println("\nConnected Landing Sites:");
        if (site.getLandingSiteConnections().isEmpty()) {
            System.out.println("  None");
        } else {
            for (String name : site.getLandingSiteConnections().keySet()) {
                System.out.println("  - " + name);
            }
        }

        System.out.println("\nConnected Rooms:");
        if (site.getRoomConnections().isEmpty()) {
            System.out.println("  None");
        } else {
            for (Room room : site.getRoomConnections().values()) {
                System.out.println("  - " + room.getName());
            }
        }
    }

    //crash site display
    public void displayItemRetrieved(Item item) {
        System.out.println("You added " + item.getName() + " to your inventory.");
    }
    public void displayItemNotFound(String itemName) {
        System.out.println("Item '" + itemName + "' not found in the crashed ship.");
    }
    public void displayCrashSiteItems(CrashSite crashSite) {
        if (crashSite.getItems().isEmpty()) {
            System.out.println("The crashed ship has no items.");
        } else {
            System.out.println("Items at the crashed ship:");
            for (Item item : crashSite.getItems()) {
                System.out.println("  - " + item.getName());
            }
        }

}