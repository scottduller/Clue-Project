package clue.model.card;

public enum Weapon implements CardType {
    CANDLESTICK("Candlestick"),
    REVOLVER("Revolver"),
    KNIFE("Knife"),
    LEAD("Lead"),
    PIPE("Pipe"),
    ROPE("Rope"),
    WRENCH("Wrench");

    private String weapon;

    Weapon(String weapon) {
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "weapon='" + weapon + '\'' +
                '}';
    }
}
