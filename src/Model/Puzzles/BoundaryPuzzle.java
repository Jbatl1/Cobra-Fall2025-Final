package Model.Puzzles;

import Model.Items.Item;

public class BoundaryPuzzle extends Puzzle {
    public BoundaryPuzzle(String puzzleID, String puzzleQuestion, int puzzleAttempts, String puzzleSolution, Item reward, String roomID, String type) {
        super(puzzleID, puzzleQuestion, puzzleAttempts, puzzleSolution, reward, roomID, type);
    }
}
