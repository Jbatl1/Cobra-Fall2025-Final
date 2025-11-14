package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Rooms.Room;

public class BoundaryPuzzle extends Puzzle {

    private int puzzleMaxAttempts;
    private boolean puzzleIsSolved;
    private boolean puzzleLocked;
    private int attemptsLeft;
    private String puzzleSolution;

    public BoundaryPuzzle(String puzzleID, String puzzleQuestion, int puzzleMaxAttempts, String puzzleSolution, Item reward, String roomID, String type, int puzzleMaxAttempts1, boolean puzzleIsSolved, boolean puzzleLocked, int attemptsLeft) {
        super(puzzleID, puzzleQuestion, puzzleMaxAttempts, puzzleSolution, reward, roomID, type);
        this.puzzleMaxAttempts = puzzleMaxAttempts1;
        this.puzzleIsSolved = puzzleIsSolved;
        this.puzzleLocked = puzzleLocked;
        this.attemptsLeft = attemptsLeft;
    }

    public boolean isPuzzleLocked() {return puzzleLocked;}

    @Override
    public int solvePuzzle(Room room, Player player, String userInput) { //Anita Philip
        Puzzle currentPuzzle = null;
        for (Puzzle p : room.getPuzzlePresent()) {
            if (p.getRoomID().equals(room.getRoomID())) {
                currentPuzzle = p;
                break;
            }
        }
        if (currentPuzzle == null || !currentPuzzle.getType().equalsIgnoreCase("boundary"))
            return -1;

        if (puzzleIsSolved) return 1; // already solved

        if (puzzleLocked || attemptsLeft <= 0) {
            puzzleLocked = true;
            player.setCurrRoom(player.getPrevRoom());
            return -1;
        }

        if (userInput.equalsIgnoreCase("ignore")) {
            player.setCurrRoom(player.getPrevRoom());
            player.receiveDamage(20);
            return -1;
        }

        if (userInput.equalsIgnoreCase(puzzleSolution)) {
            puzzleIsSolved = true;
            currentPuzzle.addLootToInventory(player);
            room.getPuzzlePresent().remove(currentPuzzle);
            return 1;
        } else {
            attemptsLeft--;
            if (attemptsLeft <= 0) {
                player.receiveDamage(20);
                puzzleLocked = true;
                player.setCurrRoom(player.getPrevRoom());
            }
            return 0;
        }
    }


}
