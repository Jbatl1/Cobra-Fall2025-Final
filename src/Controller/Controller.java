package Controller;

import Model.Entities.Monster;
import Model.Items.Item;
import Model.Model;
import Model.Puzzles.BoundaryPuzzle;
import Model.Rooms.CrashSite;
import Model.Rooms.LandingSite;
import Model.Rooms.Room;
import View.View;
import Model.Puzzles.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller { //Caleb Butler
    private Model model;
    private View view;


    private boolean shop; // just declare, no initialization

    private boolean fight = false;
    private boolean puzzle = false;
    private boolean solvePuzzle = false;
    private Monster currentMonster = null;
    private Puzzle currentPuzzle = null;
    private String currentPlanet = "Survivors World";


    public Controller(Model model, View view) { //Caleb Butler
        this.model = model;
        this.view = view;
    }


    public void processInput () { //Caleb Butler

        int x;
        boolean rest = model.getPlayer().getCurrRoom().isRestRoom();

        while (!shop && !fight && !puzzle && !solvePuzzle) {
            view.pointerForInput();
            String input = this.view.getInput().toUpperCase();

            String trim = "";
            switch (input) {

                // MOVEMENT----------------------
                case "N": // move north
                    x = model.getPlayer().move("NORTH");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                         currentPuzzle = model.getPlayer().getCurrRoom().getBoundaryPuzzleInDirection("N", model.getRooms());
                    }
                    break;
                case "E": // move east
                    x = model.getPlayer().move("EAST");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getBoundaryPuzzleInDirection("E", model.getRooms());
                    }
                    break;
                case "S": // move south
                    x = model.getPlayer().move("SOUTH");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                         currentPuzzle = model.getPlayer().getCurrRoom().getBoundaryPuzzleInDirection("S", model.getRooms());
                    }
                    break;
                case "W": // move west
                    x = model.getPlayer().move("WEST");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getBoundaryPuzzleInDirection("W", model.getRooms());
                    }
                    break;
                case String s when input.matches("^TRAVEL\\s.*$"): // travel to landing site
                    x = this.model.getPlayer().moveToLandingSite(s.substring(7).trim());
                    if (x == -1) {
                        view.displayLandingSiteNotFound(s.substring(7).trim());
                        break;
                    }
                    if (x == -2) {
                        view.displayNotAtLandingSite();
                        break;
                    }
                    if (x == 1) currentPlanet = "Volcanic Inferno";
                    if (x == 2) currentPlanet = "Frozen Waste";
                    if (x == 3) currentPlanet = "Survivors World";
                    if (x == 4) currentPlanet = "Jungle Ruins";
                    if (x == 5) currentPlanet = "Echo Deserts";
                    if (x == 6) currentPlanet = "Crystal Canyons";
                    if (x == 7) currentPlanet = "Sky Isles";
                    if (x == 8) currentPlanet = "Celestial Citadel";
                    view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    break;


                // ITEMS---------------------
                case String s when input.matches("^DESTROY\\s.*$"):
                    trim = s.substring(8);
                    x = model.getPlayer().destroyItem(trim);
                    model.displayDestroyItem(x, trim);
                    break;
                case String s when input.matches("^PICKUP\\s.*$"): //pickup item //Anita Philip lines 104 - 146
                    x = this.model.getPlayer().pickupItem(s.substring(7).trim());
                    view.displayItemPickup(x, s);
                    break;
                case String s when input.matches("^DROP\\s.*$"): // drop item
                    x = this.model.getPlayer().dropItem(s.substring(5).trim());
                    this.view.displayItemDropped(x, s);
                    break;
                case String s when input.matches("^EQUIP\\s.*$"): //Equip Item
                    trim = s.substring(6).trim();
                    x = this.model.getPlayer().equipItemToHands(trim);
                    this.view.displayEquipItem(x, trim);
                    break;
                case String s when input.matches("^UNEQUIP\\s.*$"): //UnEquip Item
                    trim = s.substring(8).trim();
                    x = this.model.getPlayer().unEquipItemFromHands(trim);
                    this.view.displayUnEquipItem(x, trim);
                    break;
                case String s when input.matches("^EXAMINE\\s.*$"): //Examine Item
                    trim = s.substring(8).trim();   // the thing the player wants to examine

                    // 1. Try to see if it's a puzzle in the current room
                    Puzzle roomPuzzle = this.model.getPlayer().getCurrRoom().getThePuzzle();
                    if (roomPuzzle != null) {
                        // Call your puzzle examine method
                        handleLootNormalPuzzle(model.getPlayer().getCurrRoom());
                        break;
                    }else{
                        view.displayNoPuzzleHereToExamine();
                    }

                    // 2. Not a puzzle → check if it's an inventory item
                    int index = this.model.getPlayer().isInInventory(trim);
                    if (index != -1) {
                        this.view.displayExamineItem(
                                this.model.getPlayer().getInventory().get(index)
                        );
                        break;
                    }

                    // 3. Not found at all
                    this.view.displayMessage1();

                    break;
                case "TOOL BELT": // opens tool belt
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt());
                    break;
                case String s when input.matches("ADD.*?TOOL BELT"): // add item to tool belt
                    trim = s.substring(3, s.length() - 10).trim();
                    x = this.model.getPlayer().equipItemToToolBelt(trim);
                    this.view.displayAddToToolBelt(x, trim);
                    break;
                case String s when input.matches("REMOVE.*?TOOL BELT"): // add item to tool belt
                    trim = s.substring(7, s.length() - 10).trim();
                    x = this.model.getPlayer().removeItemFromToolBelt(trim);
                    this.view.displayRemoveFromToolBelt(x, trim);
                    break;
                case "1", "2", "3", "4", "5": // use tool belt item
                    x = model.getPlayer().useToolBeltItem(Integer.parseInt(input) - 1);
                    view.displayToolBeltUse(x);
                    break;
                case "B": // Shows Inventory
                    this.view.displayInv(this.model.getPlayer().getInventory());
                    break;
                case "G": // drop held item
                    String itemName = this.model.getPlayer().getEquippedItem().getItemName();
                    x = this.model.getPlayer().dropEquippedItem();
                    this.view.displayEquippedItemDropped(x, itemName);  // need to update player dropItem method to return -1 when item cant be dropped
                    break;
                case "SHIP": // Opens Ship Inventory
                    if (this.model.getPlayer().getCurrRoom() instanceof LandingSite) {
                        this.view.displayShipInv(this.model.getPlayer().getShipStorage());
                    }
                    break;
                case "CRASHED SHIP": // Opens Crash Site Ship Inventory
                    if (this.model.getPlayer().getCurrRoom() instanceof CrashSite) {
                        this.view.displayShipInv(((CrashSite) this.model.getPlayer().getCurrRoom()).getShipStorage());
                    }
                    break;
                case String s when input.matches("GET.*?CRASHED_SHIP"): ;
                    trim = s.substring(4, s.length() - 12).trim();
                    x = this.model.getPlayer().getFromCrashedShip(trim);
                    this.view.displayGetFromCrashedShip(x, trim);
                    break;
                case String s when input.matches("GET.*?SHIP"):
                    trim = s.substring(4, s.length() - 4).trim();
                    x = this.model.getPlayer().getFromShip(trim);
                    this.view.displayGetFromShip(x, trim);
                    break;
                case String s when input.matches("STORE.*?CRASHED SHIP"): ;
                    trim = s.substring(6, s.length() - 12).trim();
                    x = this.model.getPlayer().storeItemInCrashedShip(trim);
                    this.view.displayStoreInCrashedShip(x, trim);
                    break;
                case String s when input.matches("STORE.*?SHIP"):
                    trim = s.substring(6, s.length() - 4).trim();
                    x = this.model.getPlayer().storeItemInShip(trim);
                    this.view.displayStoreInShip(x, trim);
                    break;

                // MONSTER -----------------------

                case String s when input.matches("^FIGHT\\s.*$"): // Start fight
                    x = this.model.getPlayer().getCurrRoom().getMonsterByName(s.substring(6).trim());
                    if (x >= 0) {
                        this.view.displayFightStart(s.substring(6).trim());
                        currentMonster = this.model.getPlayer().getCurrRoom().getMonsters().get(x);
                        fight = true;
                    } else {
                        this.view.displayMonsterNotFound(s.substring(6).trim());
                    }
                    break;
                case String s when input.matches("^INSPECT\\s.*$"): // shows monster name / desc / health / atk
                    trim = s.substring(8).trim();
                    x = this.model.getPlayer().getCurrRoom().getMonsterByName(trim);
                    if (x >= 0) {
                        this.view.displayInspectMonster(this.model.getPlayer().getCurrRoom().getMonsters().get(x));
                    }
                    else {
                        this.view.displayMonsterNotFound(s.substring(8).trim());
                    }
                    break;

                // ROOMS ----------------------

                case "EXPLORE": // explore room

                    this.view.displayExploreRoom(this.model.getPlayer().getCurrRoom());
                    break;

                // OTHER --------------------
                case "M": // open map
                    if (currentPlanet.equalsIgnoreCase("Volcanic Inferno")) view.displayVolcanicInfernoMap();
                    if (currentPlanet.equalsIgnoreCase("Frozen Waste")) view.displayFrozenWorldMap();
                    if (currentPlanet.equalsIgnoreCase("Survivors World")) view.displaySurvivorsWorldMap();
                    if (currentPlanet.equalsIgnoreCase("Jungle Ruins")) view.displayJungleRuinMap();
                    if (currentPlanet.equalsIgnoreCase("Echo Deserts")) view.displayEchoDesertMap();
                    if (currentPlanet.equalsIgnoreCase("Crystal Canyons")) view.displayCrystalCanyonMap();
                    if (currentPlanet.equalsIgnoreCase("Sky Isles")) view.displaySkyIslesMap();
                    if (currentPlanet.equalsIgnoreCase("Celestial Citadel")) view.displayCelestialCitadelMap();

                    break;
                case "SHOP": // opens shop and displays items for sale
                    if (this.model.getPlayer().getCurrRoom().isShop()) {
                        this.view.displayOpenShop();
                        shop = true;
                    }
                    else {
                        this.view.displayNotInShop();
                    }
                    break;
                case "HELP":
                    view.displayHelp();
                    break;
                case "STATS":
                    view.printStats(this.model.getPlayer());
                    break;
                default:
                    this.view.displayInvalidCommand();
                    break;
            }
        }

        // ================================
        //          FIGHT LOOP
        // ================================
        while (fight) { //Caleb Butler

            if (this.model.getPlayer().getHealth() <= 0) {
                this.view.displayDefeat();
                model.getPlayer().loseItemOnDefeat();
                fight = false;
                break;
            }
            String input = this.view.getInput().toUpperCase();
            String trim = "";
            switch (input) {
                case "ATTACK":
                    x = this.model.getPlayer().inflictDamage(currentMonster);
                    this.view.displayPlayerAttack(currentMonster.getName(), x);
                    break;
                case "TOOL BELT": // opens tool belt
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt());
                    break;
                case "B": // Shows Inventory
                    this.view.displayInv(this.model.getPlayer().getInventory());
                    break;
                case String s when input.matches("^EQUIP\\s.*$"): //Equip Item
                    trim = s.substring(6).trim();
                    x = this.model.getPlayer().equipItemToHands(trim);
                    this.view.displayEquipItem(x, trim);
                    break;
                case String s when input.matches("^UNEQUIP\\s.*$"): //UnEquip Item
                    trim = s.substring(6).trim();
                    x = this.model.getPlayer().unEquipItemFromHands(trim);
                    this.view.displayUnEquipItem(x, s);
                    break;
                case "FLEE":
                    this.view.displayFlee(currentMonster.getName());
                    currentMonster = null;
                    fight = false;
                    break;
                case String s when input.matches("ADD.*?TOOL BELT"): // add item to tool belt
                    trim = s.substring(3, s.length() - 10).trim();
                    x = this.model.getPlayer().equipItemToToolBelt(trim);
                    this.view.displayAddToToolBelt(x, trim);
                    break;
                case String s when input.matches("REMOVE.*?TOOL BELT"): // add item to tool belt
                    trim = s.substring(7, s.length() - 10).trim();
                    x = this.model.getPlayer().removeItemFromToolBelt(trim);
                    this.view.displayRemoveFromToolBelt(x, trim);
                    break;
                case "1", "2", "3", "4", "5": // use tool belt item
                    x = model.getPlayer().useToolBeltItem(Integer.parseInt(input) - 1);
                    view.displayToolBeltUse(x);
                    break;
                case "STATS":
                    view.printStats(this.model.getPlayer());
                    break;
                default:
                    this.view.displayInvalidCommand();
                    break;
            }

            if (!fight) break; // leave fight loop if we fled


            // check if we killed the monster before it damages us
            if (currentMonster.getHealth() <= 0) {
                this.view.displayVictory(currentMonster.getName(), currentMonster.getDropItem());
                this.model.getPlayer().getCurrRoom().removeMonster(currentMonster);
                fight = false;
                break;
            }

            x = this.model.getPlayer().receiveDamage(currentMonster.getAttackPower());
            this.view.displayTakenDamageFromMonster(model.getPlayer(), x);
            this.view.displayMonsterAttack(currentMonster.getName(), currentMonster.getAttackPower());
        }


        while (shop) {
            String input = this.view.getInput().toUpperCase();
            String trim = "";
            switch (input) {
                case "VIEW ITEMS": // displays items for sale
                    Map<Item, Integer> stock = (this.model.getPlayer().getCurrRoom()).getStock();
                    this.view.displayStock(stock);
                    break;
                case String s when input.matches("^BUY\\s.*$"): // buy item
                    trim = s.substring(4).trim();
                    x = this.model.getPlayer().buyItem(trim); // -1 = not enough money, -2 = item not found, else return price of item
                    this.view.displayPurchaseItem(x, trim);
                    break;
                case String s when input.matches("^SELL\\s.*$"): // sell item
                    trim = s.substring(5).trim();
                    x = this.model.getPlayer().sellItem(trim); // -1 = item not found, else return sell price
                    this.view.displaySellItem(x, trim);
                    break;
                case "EXIT": // exit shop
                    this.view.displayExitShop();
                    shop = false;
                    break;
                default:
                    this.view.displayInvalidCommand();
                    break;
            }
        }



        if (rest) {
            int beforeHealth = this.model.getPlayer().getHealth();
            this.model.getPlayer().rest();
            int afterHealth = this.model.getPlayer().getHealth();
            this.view.displayRest(beforeHealth, afterHealth);
        }
    }


    private boolean runPuzzleLoop(Puzzle puzzle, String answer) {
        while (!puzzle.isPuzzleIsSolved()) {
            // If puzzle is locked, exit immediately
            if (puzzle.isPuzzleLocked()) {
                view.displayPuzzleFailed(puzzle);  // Show failure
                return false;
            }

            String input = answer != null ? answer : view.getInput();
            answer = null; // only use provided answer once

            int result = puzzle.solvePuzzle(model.getPlayer().getCurrRoom(), model.getPlayer(), input);

            switch (result) {
                case 1: // Solved
                    view.displayPuzzleSolved(puzzle);
                    model.getPlayer().getCurrRoom().getPuzzlePresent().remove(puzzle);
                    return true;

                case 0: // Incorrect attempt
                    view.displayPuzzleIncorrect(puzzle);
                    break;

                case -1: // Locked
                    view.displayPuzzleFailed(puzzle);
                    return false;
            }
        }
        return puzzle.isPuzzleIsSolved();
    }

    private boolean runBoundaryPuzzleLoop(Puzzle puzzle) { //Caleb Butler
        view.displayPuzzleQuestion(puzzle);
        while (!puzzle.isPuzzleIsSolved() && !puzzle.isPuzzleLocked()) {
            String input = view.getInput();
            int result =  puzzle.solvePuzzle(model.getPlayer().getCurrRoom(), model.getPlayer(), input);

            switch (result) {
                case 1: // Solved
                    view.displayPuzzleSolved(puzzle);
                    model.getPlayer().getCurrRoom().getPuzzlePresent().remove(puzzle); // ✅ Clean removal
                    return true;

                case 0: // Incorrect attempt
                    view.displayPuzzleIncorrect(puzzle);
                    break;

                case -1: // Locked, invalid, or no attempts left
                default:
                    view.displayPuzzleLocked(puzzle);
                    return false;
            }
        }
        return puzzle.isPuzzleIsSolved();
    }



    public void handleBoundaryPuzzle(Room room) { //Caleb Butler
        Puzzle boundaryPuzzle = room.getRoomPuzzle(); // One boundary puzzle per room

        if (boundaryPuzzle != null
                && !boundaryPuzzle.isPuzzleIsSolved()
                && boundaryPuzzle.getType().equalsIgnoreCase("boundary")) {

            view.displayAttemptPuzzle(); // e.g., "This room has a puzzle (Examine / Ignore)"
            String choice = view.getInput();

            if (choice.equalsIgnoreCase("EXAMINE")) {
                boolean solved = runBoundaryPuzzleLoop(boundaryPuzzle);
                if (!solved) {
                    // Player failed the puzzle; move back
                    view.displayPuzzleFailed(boundaryPuzzle);
                    movePlayerToPreviousRoom(); // return player to previous room
                }
            } else {
                System.out.println(model.getPlayer().getCurrRoom().getRoomName());
                view.displayPuzzleIgnored(boundaryPuzzle);
                movePlayerToPreviousRoom(); // return player to previous room
            }
        }
    }

    public void handleLootNormalPuzzle(Room room) { //Anita Philip
        Puzzle lootNormalPuzzle = room.getThePuzzle();

        // Only proceed if there is a loot/normal puzzle not yet solved
        if (lootNormalPuzzle != null
                && !lootNormalPuzzle.isPuzzleIsSolved()
                && (lootNormalPuzzle.getType().equalsIgnoreCase("normal")
                || lootNormalPuzzle.getType().equalsIgnoreCase("loot"))) {

            lootNormalPuzzle.resetAttempts();

            view.displayNormalLootPuzzlePrompt(room);
            view.displayPick();
            view.pointerForInput();

            String choice = view.getInput();


            // If player ignores the puzzle
            if (choice.equalsIgnoreCase("IGNORE")) {
                int damage = 5; // Or any amount you want
                model.getPlayer().receiveDamage(damage);
                view.displayTakenDamage();
                return; // exits method, returning player to normal room state
            }

            boolean solved;
            if (choice.equalsIgnoreCase("EXAMINE")) {
                solved = runPuzzleLoop(lootNormalPuzzle, null); // handles question and input internally
            } else {
                solved = runPuzzleLoop(lootNormalPuzzle, choice); // treat any other word as answer
            }
        }
    }

        private void movePlayerToPreviousRoom() { //Anita Philip
        Room previousRoom = model.getPlayer().getPrevRoom();
        if (previousRoom != null) {
            model.getPlayer().setCurrRoom(previousRoom);
            view.displayReturnToPreviousRoom(previousRoom); // optional message
        } else {
            view.displayMessage();
        }
    }


}
