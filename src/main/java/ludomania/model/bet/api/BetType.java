package ludomania.model.bet.api;

/**
 * Interface representing the type of a bet in a game.
 * <p>
 * This interface defines the contract for all bet types,
 * including their display name and payout multiplier.
 * Typically implemented by enums that enumerate all possible bet types
 */
public interface BetType {

    /**
     * Returns the display name of the bet type.
     *
     * @return the name of the bet type
     */
    String getTypeName();

    /**
     * Returns the payout multiplier associated with this bet type.
     * <p>
     * The payout is usually a decimal value that represents how much
     * the player wins in addition to their original bet.
     *
     * @return the payout multiplier
     */
    Double getPayout();

}
