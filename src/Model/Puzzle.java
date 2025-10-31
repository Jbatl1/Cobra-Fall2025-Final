package Model;

import Model.Items.Item;

public class Puzzle {
    protected String id;
    protected String question;
    protected int attempts;
    protected String solution;
    protected Item reward;

    public Puzzle(String id, String question, String solution, Item reward, int attempts) {
        this.id = id;
        this.question = question;
        this.solution = solution;
        this.reward = reward;
        this.attempts = attempts;
    }

    public boolean solve(String answer) {
        if (attempts <= 0) {
            System.out.println("No attempts left!");
            return false;
        }

        if (answer.equalsIgnoreCase(solution)) {
            System.out.println("Correct! You solved the puzzle!");
            return true;
        } else {
            attempts--;
            System.out.println("Wrong answer. Attempts left: " + attempts);
            return false;
        }
    }

    public String hint() {
        return "Hint: The answer begins with '" + solution.charAt(0) + "'";
    }

    public String examine() {
        return question;
    }

    public int getAttempts() { return attempts; }
}