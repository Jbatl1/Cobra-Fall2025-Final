package Controller;

import Model.Entities.Monster;
import Model.Items.Item;
import Model.Model;
import Model.Rooms.Room;
import Model.Rooms.Shop;
import View.View;
import Model.Puzzles.Puzzle;
import Model.GameSaveManager;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private View view;


    private boolean shop = model.getPlayer().getCurrRoom() instanceof Shop;
    private boolean fight = false;
    private boolean puzzle = false;
    private boolean solvePuzzle = false;
    private Monster currentMonster = null;
    private Puzzle currentPuzzle = null;

    public void processInput () { // Caleb

        int x;
        boolean rest = model.getPlayer().getCurrRoom().isRestRoom();

        while (!shop && !fight && !puzzle && !solvePuzzle) {
            String input = this.view.getInput();
            switch (input) {

                // MOVEMENT----------------------
                case "N": // move north
                    x = model.getPlayer().move("N");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getExit("N").getBoundryPuzzle();
                    }
                    break;
                case "E": // move east
                    x = model.getPlayer().move("E");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getExit("E").getBoundryPuzzle();
                    }
                    break;
                case "S": // move south
                    x = model.getPlayer().move("S");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getExit("S").getBoundryPuzzle();
                    }
                    break;
                case "W": // move west
                    x = model.getPlayer().move("W");
                    handleBoundaryPuzzle(model.getPlayer().getCurrRoom());

                    if (x == 1) {
                        view.displayRoomEntry(model.getPlayer().getCurrRoom());
                    }
                    if (x == -1) {
                        view.displayNoExit();
                    }
                    if (x == -2) {
                        puzzle = true;
                        currentPuzzle = model.getPlayer().getCurrRoom().getExit("W").getBoundryPuzzle();
                    }
                    break;


                // ITEMS---------------------
                case String s when input.matches("^PICKUP\\s.*$"): //pickup item
                    x = this.model.getPlayer().pickupItem(s.substring(7).trim());
                    view.displayItemPickup(x, s);
                    String itemName = input.substring(7).trim();
                    Item targetItem = model.findItemInCurrentRoom(itemName);

                    if (targetItem == null) {
                        view.displayItemNotFound(targetItem);
                        break;
                    }

                    // Get puzzles for this item in the current room
                    List<Puzzle> puzzlesInThisRoom = getItemPuzzlesForCurrentRoom(targetItem);
                    boolean blocked = false;

                    for (Puzzle p : puzzlesInThisRoom) {
                        if (!p.isPuzzleIsSolved()) {
                            view.displayPuzzlePrompt(p);
                            String choice = view.getInput();

                            if (choice.equalsIgnoreCase("EXAMINE")) {
                                boolean solved = runPuzzleLoop(p);
                                if (!solved) {
                                    view.displayPuzzleFailed(p);
                                    view.displayPuzzleBlockedPickup(targetItem);
                                    blocked = true;
                                    break;
                                }
                            } else { // IGNORE
                                view.displayPuzzleIgnored(p);
                                view.displayPuzzleBlockedPickup(targetItem);
                                blocked = true;
                                break;
                            }
                        }
                    }

                    // If not blocked, allow pickup
                    if (!blocked) {
                        model.getPlayer().pickupItem(targetItem.getItemName());
                        model.getPlayer().getCurrRoom().getRoomItems().remove(targetItem); // ✅ Remove from room inventory
                        view.displayItemPickup(x,targetItem.getItemName());
                    }
                    break;

                case String s when input.matches("^DROP\\s.*$"): // drop item
                    x = this.model.getPlayer().dropItem(s.substring(5).trim());
                    this.view.displayItemDropped(x, s);
                    break;
                case String s when input.matches("^EQUIP\\s.*$"): //Equip Item
                    x = this.model.getPlayer().equipItemToHands(s);
                    this.view.displayEquipItem(x, s);
                    break;
                case String s when input.matches("^EXAMINE\\s.*$"): //Examine Item
                    x = this.model.getPlayer().isInInventory(s);
                    this.view.displayExamineItem(this.model.getPlayer().getInventory().get(x));
                    break;
                case "TOOL BELT": // opens tool belt
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt());
                    break;
                case "B": // Shows Inventory
                    this.view.displayInv(this.model.getPlayer().getInventory());
                    break;
                case "G": // drop held item
                    String itemName = this.model.getPlayer().getEquippedItem().getItemName();
                    x = this.model.getPlayer().dropItem(itemName);
                    this.view.displayItemDropped(x, itemName);  // need to update player dropItem method to return -1 when item cant be dropped
                    break;
                case "SHIP": // Opens Ship Inventory
                    if (this.model.getPlayer().getCurrRoom() instanceof LaunchSite) {
                        this.view.displayShipInv(this.model.getPlayer().getShipStorage());
                    }
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
                    x = this.model.getPlayer().getCurrRoom().getMonsterByName(s.substring(8).trim());
                    if (this.model.getPlayer().getCurrRoom().getMonsterByName(s) > 0) {
                        this.view.displayInspectMonster(this.model.getPlayer().getCurrRoom().getMonsters().get(x));
                    }
                    else {
                        this.view.displayMonsterNotFound(s.substring(8).trim());
                    }
                    break;
                case "IGNORE": // ignore monster in room
                    // idk if this is useful? they can just pick what monster to fight in the room
                    break;

                // ROOMS ----------------------

                case "EXPLORE": // explore room
                    this.view.displayExploreRoom(this.model.getPlayer().getCurrRoom());
                    break;

                // OTHER --------------------
                case "M": // open map

                    break;
                case "SHOP": // opens shop and displays items for sale
                    if (this.model.getPlayer().getCurrRoom() instanceof Shop) {
                        this.view.displayOpenShop();
                        shop = true;
                    }
                    else {
                        this.view.displayNotInShop();
                    }
                    break;
                case "QUIT":
                    String saveId = "1"; // Later you can add multiple save slots

                    // ----- Save player -----
                    GameSaveManager.savePlayer(
                            saveId,
                            model.getPlayer().getHealth(),
                            model.getPlayer().getAttackPower(),
                            model.getPlayer().getDefense(),
                            model.getPlayer().getCurrRoom().getRoomID(),        // Already String
                            model.getPlayer().getEquippedItem() != null ?
                                    model.getPlayer().getEquippedItem().getItemName() : "NONE"
                    );

                    // ----- Save solved puzzles -----
                    for (Puzzle p : model.getPuzzles().values()) {
                        if (p.isPuzzleIsSolved()) {
                            GameSaveManager.markPuzzleSolved(saveId, p.getPuzzleID()); // puzzleID is String
                        }
                    }

                    // ----- Save inventory -----
                    for (Item i : model.getPlayer().getInventory()) {
                        GameSaveManager.addItemToInventory(saveId, i.getItemID(), "inventory"); // itemID is String
                    }
                    view.displayGameSaved();
                    System.exit(0);
                    break;


                default:
                    this.view.displayInvalidCommand();
                    break;
            }
        }

        while (fight) {

            if (this.model.getPlayer().getHealth() <= 0) {
                this.view.displayDefeat();
                model.getPlayer().loseItemOnDefeat();
                fight = false;
                break;
            }
            String input = this.view.getInput();
            switch (input) {
                case "ATTACK":
                    this.model.getPlayer().inflictDamage(currentMonster);
                    this.view.displayPlayerAttack(currentMonster.getName(), this.model.getPlayer().getAttackPower()); // make getter display item damage if one is equipped
                    break;
                case "TOOL BELT": // opens tool belt
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt());
                    break;
                case "B": // Shows Inventory
                    this.view.displayInv(this.model.getPlayer().getInventory());
                    break;
                case String s when input.matches("^EQUIP\\s.*$"): //Equip Item
                    x = this.model.getPlayer().equipItemToHands(s);
                    this.view.displayEquipItem(x, s);
                    break;
                case "FLEE":
                    this.model.getPlayer().flee();
                    this.view.displayFlee(currentMonster.getName());
                    fight = false;
                default:
                    this.view.displayInvalidCommand();
                    break;
            }

            // check if we killed the monster before it damages us
            if (currentMonster.getHealth() <= 0) {
                this.view.displayVictory(currentMonster.getName(), currentMonster.getDropItem());
                this.model.getPlayer().getCurrRoom().removeMonster(currentMonster);
                fight = false;
                break;
            }

            this.model.getPlayer().receiveDamage(currentMonster.getAttackPower());
            this.view.displayMonsterAttack(currentMonster.getName(), currentMonster.getAttackPower());
        }


        while (shop) {
            String input = this.view.getInput();
            switch (input) {
                case "VIEW ITEMS": // displays items for sale
                    (Shop)(this.model.getPlayer().getCurrRoom()).displayStock();
                    break;
                case String s when input.matches("^BUY\\s.*$"): // buy item
                    x = this.model.getPlayer().buyItem(s.substring(4).trim()); // -1 = not enough money, -2 = item not found, else return price of item
                    this.view.DisplayPurchaseItem(x, s);
                    break;
                case String s when input.matches("^SELL\\s.*$"): // sell item
                    x = this.model.getPlayer().sellItem(s.substring(5).trim()); // -1 = item not found, else return sell price
                    this.view.displaySellItem(x, s);
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

    private List<Puzzle> getItemPuzzlesForCurrentRoom(Item item) {
        List<Puzzle> relevant = new ArrayList<>();
        if (item.getPuzzleIDs() == null) return relevant;

        String currentRoomId = model.getPlayer().getCurrRoom().getRoomID();

        for (String pid : item.getPuzzleIDs()) {
            Puzzle p = model.getPuzzles().get(pid);
            if (p != null && p.getRoomID().equals(currentRoomId)) {
                relevant.add(p);
            }
        }
        return relevant;  //Only returns puzzles attached to this item in the current room.
    }


    /**
     * Runs the puzzle solving loop for a specific puzzle.
     * Returns true if puzzle solved, false if failed or locked.
     */

    private boolean runPuzzleLoop(Puzzle puzzle) {
        view.displayPuzzleQuestion(puzzle);

        while (!puzzle.isPuzzleIsSolved() && !puzzle.isPuzzleLocked()) {
            String input = view.getInput();
            int result = puzzle.solvePuzzle(model.getPlayer().getCurrRoom(), model.getPlayer(), input);

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

    public void handleBoundaryPuzzle(Room room) {
        Puzzle boundaryPuzzle = room.getRoomPuzzle(); // One boundary puzzle per room

        if (boundaryPuzzle != null
                && !boundaryPuzzle.isPuzzleIsSolved()
                && boundaryPuzzle.getType().equalsIgnoreCase("boundary")) {

            view.displayBoundaryPuzzlePrompt(boundaryPuzzle); // e.g., "This room has a puzzle (Examine / Ignore)"
            String choice = view.getInput();

            if (choice.equalsIgnoreCase("EXAMINE")) {
                boolean solved = runPuzzleLoop(boundaryPuzzle);
                if (!solved) {
                    view.displayPuzzleFailed(boundaryPuzzle);
                    movePlayerToPreviousRoom(); // return player to previous room
                }
            } else {
                view.displayPuzzleIgnored(boundaryPuzzle);
                movePlayerToPreviousRoom(); // return player to previous room
            }
        }
    }

    private void movePlayerToPreviousRoom() {
        Room previousRoom = model.getPlayer().getPrevRoom();
        if (previousRoom != null) {
            model.getPlayer().setCurrRoom(previousRoom);
            view.displayReturnToPreviousRoom(previousRoom); // optional message
        } else {
            view.displayMessage();
        }
    }

}
