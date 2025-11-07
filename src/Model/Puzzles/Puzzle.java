package Model.Puzzles;

import Model.Items.Item;

public class Puzzle{
    protected String puzzleID;
    protected String puzzleQuestion;
    protected int puzzleAttempts;
    protected String puzzleSolution;
    //protected String puzzleRewardItem;
    protected Item reward;
    protected String roomID;
    protected String Type;
    // protected Item reward;

    public Puzzle(String puzzleID, String puzzleQuestion, int puzzleAttempts, String puzzleSolution, Item reward, String roomID, String type) {
        this.puzzleID = puzzleID;
        this.puzzleQuestion = puzzleQuestion;
        this.puzzleAttempts = puzzleAttempts;
        this.puzzleSolution = puzzleSolution;
        Type = type;
        this.roomID = roomID;
    }

    public String getPuzzleID() {
        return puzzleID;
    }

    public String getPuzzleQuestion() {
        return puzzleQuestion;
    }

    public int getPuzzleAttempts() {
        return puzzleAttempts;
    }

    public String getPuzzleSolution() {
        return puzzleSolution;
    }

    public Item getReward() {
        return reward;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getType() {
        return Type;
    }


    public boolean solve(String answer) {
        if (puzzleAttempts <= 0) {
            System.out.println("No attempts left!");
            return false;
        }

        if (answer.equalsIgnoreCase(puzzleSolution)) {
            System.out.println("Correct! You solved the puzzle!");
            return true;
        } else {
            puzzleAttempts--;
            System.out.println("Wrong answer. Attempts left: " + puzzleAttempts);
            return false;
        }
    }

    public String hint() {
        return "Hint: The answer begins with '" + puzzleSolution.charAt(0) + "'";
    }

    public String examine() {
        return puzzleQuestion;
    }

    public int getAttempts() { return puzzleAttempts; }
}
