package Controller;

import Model.Entities.Monster;
import Model.Model;
import Model.Rooms.Shop;
import View.View;
import Model.Puzzle;

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
                    view.enterRoom(x, "North", model.getPlayer().getCurrRoom());
                    if (x == -2) puzzle = true;
                    break;
                case "E": // move east
                    x = model.getPlayer().move("E");
                    view.enterRoom(x, "East", model.getPlayer().getCurrRoom());
                    if (x == -2) puzzle = true;
                    break;
                case "S": // move south
                    x = model.getPlayer().move("S");
                    view.enterRoom(x, "South", model.getPlayer().getCurrRoom());
                    if (x == -2) puzzle = true;
                    break;
                case "W": // move west
                    x = model.getPlayer().move("W");
                    view.enterRoom(x, "West", model.getPlayer().getCurrRoom());
                    if (x == -2) puzzle = true;
                    break;


                // ITEMS---------------------
                case String s when input.matches("^PICKUP\\s.*$"): //pickup item
                    x = this.model.getPlayer().pickupItem(s.substring(7).trim());
                    this.view.displayItemPickup(x, s);
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
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt);
                    break;
                case "B": // Shows Inventory
                    this.view.displayInv(this.model.getPlayer().getInventory());
                    break;
                case "G": // drop held item
                    String itemName = this.model.getPlayer().getEquippedItem().getName;
                    x = this.model.getPlayer().dropItem(itemName);
                    this.view.displayItemDropped(x, itemName);  // need to update player dropItem method to return -1 when item cant be dropped
                    break;
                case "SHIP": // Opens Ship Inventory
                    if (this.model.getPlayer().getCurrRoom() instanceof LaunchSite) {
                        this.view.displayShipInv(this.model.getPlayer().getShipInventory());
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
                default:
                    this.view.displayInvalidCommand();
                    break;
            }
        }

        while (puzzle) {
            String input = this.view.getInput();
            switch (input) {
                case "EXAMINE":
                    if (this.model.getPlayer().getCurrRoom().getBoundryPuzzle() != null) {
                        currentPuzzle = model.getPlayer().getCurrRoom().getBoundryPuzzle();
                        this.view.displayBoundaryPuzzle(currentPuzzle);
                    } else if (this.model.getPlayer().getCurrRoom().getPuzzle() != null) {
                        currentPuzzle = model.getPlayer().getCurrRoom().getPuzzle();
                        this.view.displayPuzzle(currentPuzzle);
                    }
                    break;
                case "SOLVE":
                    this.view.displayPuzzleStart();
                    x = this.model.getPlayer().solvePuzzle();
                    solvePuzzle = true;
                    puzzle = false;
                    break;
                case "IGNORE":
                    puzzle = false;
                    break;
            }
        }

        while (solvePuzzle) {

            if (this.model.getPlayer().getCurrRoom().getPuzzle().isSolved()) {
                this.view.displayPuzzleSolved();
                solvePuzzle = false;
                break;
            }

            while (currentPuzzle.getAttempts() >= 0) {
                String input = this.view.getInput();
                if (input.equals(currentPuzzle.getSolution())) {
                    currentPuzzle.setSolved(true);
                    this.view.displayPuzzleSolved();
                    solvePuzzle = false;
                    currentPuzzle = null;
                    break;
                } else {
                    currentPuzzle.decrementAttempts();
                    if (currentPuzzle.getAttempts() >= 0) {
                        this.view.displayPuzzleIncorrect(currentPuzzle.getAttempts());
                    } else {
                        this.view.displayPuzzleFailed();
                        solvePuzzle = false;
                        break;
                    }
                }
            }
        }

        while (fight) {

            if (this.model.getPlayer().getHealth() <= 0) {
                this.view.displayDefeat();
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
                    this.view.displayToolbelt(this.model.getPlayer().getToolBelt);
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
            this.model.getPlayer().rest();
        }
    }
}
