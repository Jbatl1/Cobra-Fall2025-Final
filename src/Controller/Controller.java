package Controller;

import Model.Model;
import View.View;

public class Controller {
    private Model model;
    private View view;



    public void processInput () { // Caleb
        boolean rest;
        boolean shop;
        boolean fight;

        String input = View.getInput();
        switch (input) {

            // MOVEMENT----------------------
            case "N": // move north

                break;
            case "E": // move east

                break;
            case "S": // move south

                break;
            case "W": // move west

                break;

            // ITEMS---------------------
            case String s when input.matches("^PICKUP\\s.*$"): //pickup item
                view.printToUser(model.getPlayer().pickupItem(s.substring(7).trim()));
                break;
            case String s when input.matches("^DROP\\s.*$"): // drop item
                view.printToUser(model.getPlayer().dropItem(s.substring(5).trim()));
                break;
            case "TOOL BELT": // opens tool belt

                break;
            case "B": // check inventory

                break;
            case "G": // drop held item

                break;
            case "SHIP": // explore room

                break;

                // MONSTER -----------------------
            case "Attack": // Attack monster

                break;
            case "FIGHT": // Start fight

                break;
            case "G": // drop held item

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

        while (fight) {
            input = view.getInput();
            switch (input) {
                case
            }
        }

        while (shop) {
            input = view.gitInput() {
                switch (input) {
                    case "SHOP": // opens shop and displays items for sale

                        break;
                    case String s when input.matches("^BUY\\s.*$"): // buy item
                        view.printToUser(model.getPlayer().dropItem(s.substring(4).trim()));
                        break;
                    case String s when input.matches("^SELL\\s.*$"): // drop item
                        view.printToUser(model.getPlayer().dropItem(s.substring(5).trim()));
                        break;
                }
            }
        }

        if (rest) {

        }
    }
}
