package ludomania.model.bet.impl;

import ludomania.model.bet.api.BetType;

/**
 * Enum representing different types of blackjack bets and their associated payout multipliers.
 * Implements the {@link BetType} interface to standardize bet behavior.
 */
public enum BlackJackBetType implements BetType {

    BASE("Base", 2.0),
    BLACKJACK("BlackJack", 2.5),
    PUSH("Push", 1.0),
    LOSE("Lose", 0.0);

    private final String typeName;
    private final double payout;

    /**
     * Constructs a BlackJackBetType enum constant with a display name and payout multiplier.
     *
     * @param displayName the human-readable name of the bet type
     * @param payout the multiplier used to calculate the payout
     */
    BlackJackBetType(String displayName, double payout) {
        this.typeName = displayName;
        this.payout = payout;
    }

    /**
     * Returns the display name of the bet type.
     *
     * @return the type name
     */
    @Override
    public String getTypeName() {
        return typeName;
    }

    /**
     * Returns the payout multiplier for this bet type.
     *
     * @return the payout amount as a double
     */
    @Override
    public Double getPayout() {
        return payout;
    } 
}
