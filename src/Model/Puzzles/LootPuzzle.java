package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Rooms.Room;

public class LootPuzzle extends Puzzle{ //Anita Philip


    private int puzzleMaxAttempts;
    private boolean puzzleIsSolved;
    private boolean puzzleLocked;
    private int attemptsLeft;
    private String puzzleSolution;

    public LootPuzzle(String puzzleID, String puzzleQuestion, int puzzleAttempts, String puzzleSolution, Item reward, String roomID, String type) {
        super(puzzleID, puzzleQuestion, puzzleAttempts, puzzleSolution, reward, roomID, type);
    } //Anita Philip


    @Override
    public int solvePuzzle(Room room, Player player, String userInput) { //Anita Philip
        // find the puzzle for this room
        Puzzle currentPuzzle = null;
        for (Puzzle p : room.getPuzzlePresent()) {
            if (p.getRoomID().equals(room.getRoomID())) {
                currentPuzzle = p;
                break;
            }
        }
        if (currentPuzzle == null || !currentPuzzle.getType().equalsIgnoreCase("loot"))
            return -1;

        if (puzzleIsSolved) return 1;
        if (puzzleLocked) return -1;

        if (userInput.equalsIgnoreCase(puzzleSolution)) {
            puzzleIsSolved = true;
            currentPuzzle.addLootToInventory(player);
            room.getPuzzlePresent().remove(currentPuzzle);
            return 1; // success
        } else {
            attemptsLeft--;
            if (attemptsLeft <= 0) puzzleLocked = true;
            return 0; // incorrect attempt
        }
    }




}
