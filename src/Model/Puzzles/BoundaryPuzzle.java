package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Rooms.Room;

import java.io.Serializable;

public class BoundaryPuzzle extends Puzzle implements Serializable {

    public BoundaryPuzzle(String puzzleID, String puzzleQuestion, int puzzleMaxAttempts,
                          String puzzleSolution, Item reward, String roomID, String type) {
        super(puzzleID, puzzleQuestion, puzzleMaxAttempts, puzzleSolution, reward, roomID, type);
    } //Anita Philip

    @Override
    public int solvePuzzle(Room room, Player player, String userInput) { //Anita Philip
        // Already solved
        if (isPuzzleIsSolved()) return 1;

        // Puzzle locked
        if (isPuzzleLocked()) return -1;

        // Player chooses to ignore puzzle
        if (userInput.equalsIgnoreCase("IGNORE")) {
            player.receiveDamage(20);
            setPuzzleLocked(true);
            return -1;
        }

        // Correct answer
        if (userInput.equalsIgnoreCase(getPuzzleSolution())) {
            addLootToInventory(player);
            setPuzzleSolved(true);
            return 1;
        }

        // Incorrect attempt
        decrementAttempts();
        return isPuzzleLocked() ? -1 : 0;
    }
}