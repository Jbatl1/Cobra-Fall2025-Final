package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Rooms.Room;

import java.io.Serializable;

public class Puzzle implements Serializable {

    private String puzzleID;
    private String puzzleQuestion;
    private int puzzleMaxAttempts;
    private String puzzleSolution;
    private boolean puzzleIsSolved;
    private boolean puzzleLocked;
    private Item reward;
    private String roomID;
    private String type;
    private int attemptsLeft;
   // private boolean puzzleLocked;

    // Constructor
    public Puzzle(String puzzleID, String puzzleQuestion, int puzzleMaxAttempts,
                  String puzzleSolution, Item reward, String roomID, String type) {
        this.puzzleID = puzzleID;
        this.puzzleQuestion = puzzleQuestion;
        this.puzzleMaxAttempts = puzzleMaxAttempts;
        this.puzzleSolution = puzzleSolution;
        this.attemptsLeft = puzzleMaxAttempts;
        this.reward = reward;
        this.type = type;
        this.roomID = roomID;
        this.type = type;
        this.attemptsLeft = puzzleMaxAttempts;
        this.puzzleIsSolved = false;
        this.puzzleLocked = false;
    }

    // Getters
    public String getPuzzleID() { return puzzleID; }
    public String getPuzzleQuestion() { return puzzleQuestion; }
    public int getPuzzleAttempts() { return attemptsLeft; }
    public int getMaxAttempts() { return puzzleMaxAttempts; }
    public String getPuzzleSolution() { return puzzleSolution; }
    public Item getReward() { return reward; }
    public String getRoomID() { return roomID; }
    public String getType() { return type; }
    public boolean isPuzzleLocked() { return puzzleLocked; }
    public boolean isPuzzleIsSolved() { return puzzleIsSolved; }

    // Protected setters for child classes
    protected void setPuzzleSolved(boolean solved) { this.puzzleIsSolved = solved; }
    protected void setPuzzleLocked(boolean locked) { this.puzzleLocked = locked; }

    // Decrement attempts and lock if none remain
    public int decrementAttempts() {
        if (attemptsLeft > 0) attemptsLeft--;
        if (attemptsLeft <= 0) puzzleLocked = true;
        return attemptsLeft;
    }

    // Add reward to player inventory
    public void addLootToInventory(Player player) {
        if (reward != null) player.addItem(reward);
    }

    public void resetAttempts() {
        this.attemptsLeft = this.puzzleMaxAttempts;
        this.puzzleLocked = false;
    }

    // Solve puzzle logic
    public int solvePuzzle(Room room, Player player, String userInput) {
        if (!"normal".equalsIgnoreCase(this.type)) return -1;
        if (puzzleLocked) return -1;
        if (puzzleIsSolved) return 1;

        if (userInput.equalsIgnoreCase(puzzleSolution)) {
            puzzleIsSolved = true;
            addLootToInventory(player);
            // Puzzle is solved — Controller will handle removing it from the room list
            return 1;
        } else {
            decrementAttempts();
            return puzzleLocked ? -1 : 0;
        }
    }
}
