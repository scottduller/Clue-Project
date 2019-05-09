package clue.model.card;

/**
 * enum class that contains all of the weapon types
 */
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
