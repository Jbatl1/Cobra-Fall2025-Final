package Controller;

import Model.Model;
import Model.Rooms.Shop;
import View.View;

public class Controller {
    private Model model;
    private View view;



    public void processInput () { // Caleb
        boolean rest = model.getPlayer().getCurrRoom().isRestRoom();
        boolean shop = model.getPlayer().getCurrRoom() instanceof Shop;
        boolean fight = false;
        boolean puzzle = false;
        int x;

        while (!shop && !fight) {
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
                case String s when input.matches("^EQUIP\\s.*$"): //pickup item
                    x = this.model.getPlayer().equipItemToHands(s);
                    this.view.equipItem();
                    break;
                case String s when input.matches("^EXAMINE\\s.*$"): //pickup item
                    x = this.model.getPlayer().getCurrRoom().getItems().contains();
                    this.view.equipItem();
                    break;
                case "TOOL BELT": // opens tool belt
                    this.view.printToolbelt(this.model.getPlayer().getToolBelt);
                    break;
                case "B": // check inventory
                    this.view.printInv(this.model.getPlayer().getInventory());
                    break;
                case "G": // drop held item
                    String itemName = this.model.getPlayer().getEquippedItem().getName;
                    x = this.model.getPlayer().dropItem(itemName);
                    this.view.displayItemDropped(x, itemName);
                    break;
                case "SHIP": // explore room
                    if (this.model.getPlayer().getCurrRoom() instanceof LaunchSite) {
                        this.view.displayShip(this.model.getPlayer().getShipInventory());
                    }
                    break;

                // MONSTER -----------------------
                case String s when input.matches("^ATTACK\\s.*$"): // attack monster
                    this.model.getPlayer().attack();
                    break;
                case "FIGHT": // Start fight
                    this.model.getPlayer().fightMonster();
                    break;
                case "G": // drop held item
                    String equippedItem = this.model.getPlayer().getEquippedItem().getName;
                    this.model.getPlayer().dropItem(equippedItem);
                    view.dropEquippedItem(equippedItem);
                    break;
                case "INSPECT": // shows monster name / desc / health / atk

                    break;
                case "IGNORE": // explore room

                    break;

                // ROOMS ----------------------

                case "EXPLORE": // explore room

                    break;

                // OTHER --------------------
                case "M": // open map

                    break;
                case "SHOP": // opens shop and displays items for sale

                    shop = true;
                    break;
            }
        }

        while (fight) {
            String input = this.view.getInput();
            switch (input) {
                case "ATTACK":

                    break;
                case "INVENTORY":

                    break;
            }
        }

        while (shop) {
            String input = this.view.getInput();
            switch (input) {
                case "SHOP": // opens shop and displays items for sale
                    (Shop)(this.model.getPlayer().getCurrRoom()).displayStock();
                    break;
                case String s when input.matches("^BUY\\s.*$"): // buy item
                    this.view.printToUser(this.model.getPlayer().dropItem(s.substring(4).trim()));
                    break;
                case String s when input.matches("^SELL\\s.*$"): // sell item
                    this.view.printToUser(this.model.getPlayer().dropItem(s.substring(5).trim()));
                    break;
            }
        }

        while (puzzle) {
            String input = this.view.getInput();
            switch (input) {
                case "EXAMINE":
                    if (this.model.getPlayer().getCurrRoom().getBoundryPuzzle != null) {
                        this.view.printBoundryPuzzle();
                    } else if (this.model.getPlayer().getCurrRoom().getPuzzle() != null) {
                        this.view.printPuzzle();
                    }
                    this.model.getPlayer().examinePuzzle(this.model.getPlayer().getCurrRoom().getPuzzle());
                    break;
                case "SOLVE":
                    this.model.getPlayer().solvePuzzle();
                    break;
                case "IGNORE":
                    puzzle = false;
                    break;
            }
        }


        if (rest) {
            this.model.getPlayer().rest();
        }
    }
}
