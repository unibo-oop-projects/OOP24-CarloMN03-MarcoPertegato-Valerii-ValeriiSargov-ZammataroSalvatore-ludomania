package ludomania.model.bet.api;

/**
 * Interface representing the type of a bet in a game.
 * Implementing classes or enums define specific bet types with associated
 * names and payout multipliers.
 */
public interface BetType {

    /**
     * Returns the name of the bet type (for display or identification purposes).
     *
     * @return the type name as a String
     */
    public String getTypeName();

    /**
     * Returns the payout multiplier for the bet type.
     * This multiplier is used to calculate the winnings from a bet.
     *
     * @return the payout multiplier as a Double
     */
    public Double getPayout();

}
