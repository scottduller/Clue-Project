package clue.model;

public enum CommandWord {
    NEW("new"), //Create new game
    ROLL("roll"),
    MOVE("move"), //Move to a location within a certain distance
    TELEPORT("teleport"), //Move to a location (no limits)
    ENTER("enter"), //Enter a room
    QUIT("quit"), //Exit Game
    UNKNOWN("unknown");

    public String word;

    CommandWord(String word) {
        this.word = word;
    }

    public static CommandWord getCommandWord(String s) {
        for (CommandWord c : CommandWord.values()) {
            if (c.getWord().startsWith(s.toLowerCase())) {
                return c;
            }
        }
        return UNKNOWN;
    }

    public String getWord() {
        return word;
    }
}
