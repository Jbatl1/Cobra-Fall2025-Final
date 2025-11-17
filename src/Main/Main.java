package Main;

import Controller.Controller;
import Model.Entities.Player;
import Model.Items.Weapon;
import Model.LoadRooms;
import Model.Model;
import View.View;

import java.io.*;
import java.util.HashMap;

public class Main {

    public static LoadRooms M;

    public static void main(String[] args) { //caleb
        M = new LoadRooms(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        M.loadRooms();

        Controller controller;

        if (new File("game.dat").exists()) {
           // Deserialize.
            try (FileInputStream file = new FileInputStream("game.dat")) {
                ObjectInputStream in = new ObjectInputStream(file);
                controller = (Controller) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Create new data.
           controller = new Controller(new Model(new Player(M.getStartRoom(), "Player", 100, 10, (Weapon) M.getItems().get("ar_rSword"))), new View());
        }

        System.out.println("WELCOME TO NEXUS ESCAPE! (N,E,S,W)" + '\n');
        while (true) {
            controller.processInput();
        }
    }
}
