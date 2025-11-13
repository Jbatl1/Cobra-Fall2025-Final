package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.Rooms.Room;

public class Puzzle {
    private String puzzleID;
    private String puzzleQuestion;
    private int puzzleMaxAttempts;
    private String puzzleSolution;
    private boolean puzzleIsSolved;
    private Item reward;
    private String roomID;
    private String type;
    private int attemptsLeft;
    private boolean puzzleLocked;

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
        this.puzzleIsSolved = false;
        this.puzzleLocked = false;
    }

    // Getters
    public String getPuzzleID() { return puzzleID; }
    public String getPuzzleQuestion() { return puzzleQuestion; }
    public int getPuzzleAttempts() { return puzzleMaxAttempts; }
    public String getPuzzleSolution() { return puzzleSolution; }
    public Item getReward() { return reward; }
    public String getRoomID() { return roomID; }
    public String getType() { return type; }
    public boolean isPuzzleLocked() { return puzzleLocked; }
    public boolean isPuzzleIsSolved() { return puzzleIsSolved; }
    public int getMaxAttempts() { return puzzleMaxAttempts; }

    /**
     * Decrements the remaining attempts and locks the puzzle if none remain.
     * @return Remaining attempts after decrement.
     */
    public int decrementAttempts() {
        if (attemptsLeft > 0) {
            attemptsLeft--;
        }
        if (attemptsLeft <= 0) {
            puzzleLocked = true;
        }
        return attemptsLeft;
    }

    /**
     * Adds the reward to the player's inventory if it exists.
     */
    public void addLootToInventory(Player player) {
        if (reward != null) {
            player.addItem(reward);
        }
    }

    /**
     * Main puzzle-solving logic.
     *
     * Return codes:
     *  1 → Solved successfully
     *  0 → Incorrect attempt (still can try)
     * -1 → Locked, invalid, or not applicable
     */
    public int solvePuzzle(Room room, Player player, String userInput) {
        // Only "normal" puzzles are directly solvable
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
