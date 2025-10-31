package View;

import java.util.HashMap;

public class DialogueManager {

    // Store all dialogues here
    private static final HashMap<String, String[]> dialogues = new HashMap<>();

    static {
        // Raider Camp
        dialogues.put("raider_intro", new String[] {
                "You approach the Raider Camp.",
                "The smell of smoke and oil hangs in the air.",
                "A Raider steps forward: 'Hand over your supplies, stranger!'"
        });

        dialogues.put("raider_victory", new String[] {
                "The Raider leader falls to the ground.",
                "You’ve cleared the camp and taken their supplies."
        });

        // Shop
        dialogues.put("shop_intro", new String[] {
                "Merchant: 'Welcome, traveler!'",
                "Merchant: 'We have everything from potions to plasma rifles!'"
        });

        // Rest Room
        dialogues.put("rest_start", new String[] {
                "You find a quiet place to rest.",
                "Warmth returns to your limbs.",
                "Your health slowly recovers..."
        });

        // Puzzle
        dialogues.put("puzzle_start", new String[] {
                "Strange runes glow on the wall.",
                "A voice whispers: 'Answer correctly, or face the consequences.'"
        });

        dialogues.put("puzzle_solved", new String[] {
                "The runes fade away.",
                "You feel a surge of power as the puzzle unlocks something within you."
        });
    }

    // ========================
    // METHODS
    // ========================

    // Plays a dialogue sequence
    public static void play(String key) {
        if (!dialogues.containsKey(key)) {
            System.out.println("[No dialogue found for key: " + key + "]");
            return;
        }

        String[] lines = dialogues.get(key);
        for (String line : lines) {
            System.out.println(line);
            try {
                Thread.sleep(1200); // short pause between lines
            } catch (InterruptedException ignored) {}
        }
    }

    // Returns the dialogue lines (if needed elsewhere)
    public static String[] get(String key) {
        return dialogues.getOrDefault(key, new String[] {"[Dialogue not found]"});
    }
}






