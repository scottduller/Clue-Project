package clue.model.card;

public enum WeaponType implements CardType {
    DAGGER("Dagger"),
    CANDLESTICK("Candlestick"),
    REVOLVER("Revolver"),
    LEAD_PIPE("Lead Pipe"),
    ROPE("Rope"),
    SPANNER("Spanner");

    private String weapon;

    WeaponType(String weapon) {
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return "WeaponType{" +
                "weapon='" + weapon + '\'' +
                '}';
    }
}
