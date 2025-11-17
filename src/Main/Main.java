package Main;

import Controller.Controller;
import Model.Entities.Player;
import Model.Items.Weapon;
import Model.LoadRooms;
import Model.Model;
import View.View;

import java.util.HashMap;

public class Main {

    public static LoadRooms M;

    public static void main(String[] args) { //caleb


       M = new LoadRooms(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
       M.loadRooms();

        Controller controller = new Controller(new Model(new Player(M.getStartRoom(), "Player", 100, 10, (Weapon) M.getItems().get("ar_rSword"))), new View());
        System.out.println("WELCOME TO NEXUS ESCAPE! (N,E,S,W)" + '\n');
        while (true) {
            controller.processInput();
        }
    }
}
