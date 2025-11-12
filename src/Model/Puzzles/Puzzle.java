package Model.Puzzles;

import Model.Entities.Player;
import Model.Items.Item;
import Model.LoadRooms;
import Model.Rooms.Room;

import java.util.Scanner;

public class Puzzle {
    private String puzzleID;
    private String puzzleQuestion;
    private int puzzleMaxAttempts;
    private String puzzleSolution;
    //protected String puzzleRewardItem;
    private boolean puzzleIsSolved;
    private Item reward;
    private String roomID;
    private String Type;
    private int attemptsLeft;

    private boolean puzzleLocked;
    private int startPuzzle;

    public Puzzle(String puzzleID, String puzzleQuestion, int puzzleMaxAttempts, String puzzleSolution, Item reward, String roomID, String type) {
        this.puzzleID = puzzleID;
        this.puzzleQuestion = puzzleQuestion;
        this.puzzleMaxAttempts = puzzleMaxAttempts;
        this.puzzleSolution = puzzleSolution;
        this.attemptsLeft = puzzleMaxAttempts;
        this.reward = reward;
        Type = type;
        this.roomID = roomID;
        this.puzzleIsSolved = false;

    }

    public String getPuzzleID() {
        return puzzleID;
    }
    public String getPuzzleQuestion() {
        return puzzleQuestion;
    }
    public int getPuzzleAttempts() {
        return puzzleMaxAttempts;
    }
    public String getPuzzleSolution() {
        return puzzleSolution;
    }
    public Item getReward() {
        return reward;
    } // <--- gets item that puzzle drops
    public String getRoomID() {
        return roomID;
    }
    public String getType() {
        return Type;
    }
    public boolean isPuzzleLocked() { return puzzleLocked; }
    public int getMaxAttempts() {return puzzleMaxAttempts;}
    public boolean isPuzzleIsSolved() {return puzzleIsSolved;}


    //if puzzle is not solved and its unlocked, the max attempts(default) will now be the attempts left
    public void resetAttempts() {
        if (!puzzleIsSolved && !puzzleLocked) {
            attemptsLeft = puzzleMaxAttempts;
        }
    }

    public int decrementAttempts(){
       return puzzleMaxAttempts-- ;
    }

    //after puzzle is solved the loot/drop is added to players inventory
    public void addLootToInventory(Player player, Puzzle puzzle) {
        player.addItem(puzzle.getReward());
    }

    public int solvePuzzle(Room room, Player player, String userInput) {
        // find the puzzle for this room
        Puzzle currentPuzzle = null;
        for (Puzzle p : room.getPuzzlePresent()) {
            if (p.getRoomID().equals(room.getRoomID())) {
                currentPuzzle = p;
                break;
            }
        }
        if (currentPuzzle == null || !currentPuzzle.getType().equalsIgnoreCase("normal"))
            return -1;

        if (puzzleIsSolved) return 1;
        if (puzzleLocked) return -1;

        if (userInput.equalsIgnoreCase(puzzleSolution)) {
            puzzleIsSolved = true;
            currentPuzzle.addLootToInventory(player, currentPuzzle);
            room.getPuzzlePresent().remove(currentPuzzle);
            return 1; // success
        } else {
            attemptsLeft--;
            if (attemptsLeft <= 0) puzzleLocked = true;
            return 0; // incorrect attempt
        }
    }


}


