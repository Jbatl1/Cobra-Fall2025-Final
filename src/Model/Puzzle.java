package Model;

import Model.Items.Item;
import Model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Puzzle {
    // ==============================
    // Fields
    // ==============================
    protected String id;
    protected String question;
    protected int attempts;
    protected String solution;
    protected Item reward;

    // ==============================
    // Constructors
    // ==============================

    // Constructor for manual creation (non-DB)
    public Puzzle(String id, String question, String solution, Item reward, int attempts) {
        this.id = id;
        this.question = question;
        this.solution = solution;
        this.reward = reward;
        this.attempts = attempts;
    }

    // Constructor to load from database using puzzle ID
    public Puzzle(String id) {
        this.id = id;
        loadFromDatabase();
    }

    // ==============================
    // Database Loading
    // ==============================
    private void loadFromDatabase() {
        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.err.println("❌ Database connection not established for Puzzle " + id);
            return;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Example: adjust field names to match your DB schema
            String sql = "SELECT question, solution, reward_item_id, attempts FROM puzzles WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.question = rs.getString("question");
                this.solution = rs.getString("solution");
                this.attempts = rs.getInt("attempts");

                // If puzzle has an associated reward item, load it
                String rewardItemId = rs.getString("reward_item_id");
                if (rewardItemId != null && !rewardItemId.isEmpty()) {
                    this.reward = new Item(rewardItemId); // assuming Item can also load itself by ID
                }
            } else {
                System.err.println("⚠️ No puzzle found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error loading puzzle from database: " + id);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ==============================
    // Gameplay Methods
    // ==============================
    public boolean solve(String answer) {
        if (attempts <= 0) {
            System.out.println("No attempts left!");
            return false;
        }

        if (answer.equalsIgnoreCase(solution)) {
            System.out.println("✅ Correct! You solved the puzzle!");
            return true;
        } else {
            attempts--;
            System.out.println("❌ Wrong answer. Attempts left: " + attempts);
            return false;
        }
    }

    public String hint() {
        if (solution != null && !solution.isEmpty()) {
            return "Hint: The answer begins with '" + solution.charAt(0) + "'";
        }
        return "No hint available.";
    }

    public String examine() {
        return (question != null) ? question : "This puzzle seems incomplete...";
    }

    // ==============================
    // Getters
    // ==============================
    public int getAttempts() {
        return attempts;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }

    public Item getReward() {
        return reward;
    }
}