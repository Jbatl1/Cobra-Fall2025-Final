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
        String input;

        while (!shop && !fight) {
            input = View.input();
            switch (input) {

                // MOVEMENT----------------------
                case "N": // move north
                    x = model.getPlayer().move("N");
                    view.enterRoom(x, "North", model.getPlayer().getCurrRoom().getName());
                    if (x == -2) puzzle = true;
                    break;
                case "E": // move east
                    x = model.getPlayer().move("E");
                    view.enterRoom(x, "East", model.getPlayer().getCurrRoom().getName());
                    if (x == -2) puzzle = true;
                    break;
                case "S": // move south
                    x = model.getPlayer().move("S");
                    view.enterRoom(x, "South", model.getPlayer().getCurrRoom().getName());
                    if (x == -2) puzzle = true;
                    break;
                case "W": // move west
                    x = model.getPlayer().move("W");
                    view.enterRoom(x, "West", model.getPlayer().getCurrRoom().getName());
                    if (x == -2) puzzle = true;
                    break;

                // ITEMS---------------------
                case String s when input.matches("^PICKUP\\s.*$"): //pickup item
                    x = model.getPlayer().pickupItem(s.substring(7).trim());
                    view.displayItemPickup(x, s);
                    break;
                case String s when input.matches("^DROP\\s.*$"): // drop item
                    x = model.getPlayer().dropItem(s.substring(5).trim());
                    view.displayItemDropped(x, s);
                    break;
                case String s when input.matches("^EQUIP\\s.*$"): //pickup item
                    x = model.getPlayer().equipItemToHands(s);
                    view.equipItem();
                    break;
                case String s when input.matches("^EXAMINE\\s.*$"): //pickup item
                    x = model.getPlayer().getCurrRoom().getItems().contains();
                    view.equipItem();
                    break;
                case "TOOL BELT": // opens tool belt
                    view.printToolbelt(model.getPlayer().getToolBelt);
                    break;
                case "B": // check inventory
                    view.printInv(model.getPlayer().getInventory());
                    break;
                case "G": // drop held item
                    String itemName = model.getPlayer().getEquippedItem().getName;
                    x = model.getPlayer().dropItem(itemName);
                    view.displayItemDropped(x, itemName);
                    break;
                case "SHIP": // explore room
                    if (model.getPlayer().getCurrRoom() instanceof LaunchSite) {
                        view.displayShip(model.getPlayer().getShipInventory());
                    }
                    break;

                // MONSTER -----------------------
                case String s when input.matches("^ATTACK\\s.*$"): // attack monster
                    model.getPlayer().
                    break;
                case "FIGHT": // Start fight
                    model.getPlayer().fightMonster();
                    break;
                case "G": // drop held item
                    String equippedItem = model.getPlayer().getEquippedItem().getName;
                    model.getPlayer().dropItem(equippedItem);
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
            input = view.input();
            switch (input) {
                case "ATTACK":

                    break;
                case "INVENTORY":

                    break;
            }
        }

        while (shop) {
            input = view.input();
            switch (input) {
                case "SHOP": // opens shop and displays items for sale
                    (Shop)(model.getPlayer().getCurrRoom()).displayStock();
                    break;
                case String s when input.matches("^BUY\\s.*$"): // buy item
                    view.printToUser(model.getPlayer().dropItem(s.substring(4).trim()));
                    break;
                case String s when input.matches("^SELL\\s.*$"): // sell item
                    view.printToUser(model.getPlayer().dropItem(s.substring(5).trim()));
                    break;
            }
        }

        while (puzzle) {
            input = view.input();
            switch (input) {
                case "EXAMINE":
                    if (model.getPlayer().getCurrRoom().getBoundryPuzzle != null) {
                        view.printBoundryPuzzle();
                    } else if (model.getPlayer().getCurrRoom().getPuzzle() != null) {
                        view.printPuzzle();
                    }
                    model.getPlayer().examinePuzzle(model.getPlayer().getCurrRoom().getPuzzle());
                    break;
                case "SOLVE":
                    model.getPlayer().solvePuzzle();
                    break;
                case "IGNORE":
                    puzzle = false;
                    break;
            }
        }


        if (rest) {
            model.getPlayer().rest();
        }
    }
}
