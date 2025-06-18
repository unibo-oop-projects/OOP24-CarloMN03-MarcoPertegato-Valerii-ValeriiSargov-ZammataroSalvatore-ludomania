package ludomania.model.bet.api;

/**
 * Represents an abstract bet placed by a player.
 * Subclasses must implement how the bet is evaluated
 * to determine potential winnings.
 */
public abstract class Bet {
    private final double value;
    private final BetType type;

    /**
     * Constructs a new bet with a specified value and type.
     *
     * @param value the amount of money wagered
     * @param type  the type of bet
     */
    public Bet(final double value, final BetType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Returns the value of the bet.
     *
     * @return the amount wagered
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the value of the bet.
     *
     * @return the amount wagered
     */
    public BetType getType() {
        return type;
    }

    /**
     * Evaluates the bet and calculates the resulting payout
     * based on game-specific rules and outcomes.
     *
     * @return the amount won
     */
    public abstract Double evaluate();
}
