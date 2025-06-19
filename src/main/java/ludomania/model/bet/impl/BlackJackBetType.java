package ludomania.model.bet.impl;

import ludomania.model.bet.api.BetType;

/**
 * Enum representing different types of blackjack bets and their associated payout multipliers.
 * Implements the {@link BetType} interface to standardize bet behavior.
 */
public enum BlackJackBetType implements BetType {

    /**
     * Standard win bet. Pays 2x the amount.
     */
    BASE("Base", 2.0),

    /**
     * Blackjack win (Ace + 10). Pays 2.5x the amount.
     */
    BLACKJACK("BlackJack", 2.5),

    /**
     * Push — no winner, no loser. Returns the original bet (1x).
     */
    PUSH("Push", 1.0),

    /**
     * Player loses the bet. Pays 0.
     */
    LOSE("Lose", 0.0);

    private final String typeName;
    private final double payout;

    /**
     * Constructs a BlackJackBetType enum constant with a display name and payout multiplier.
     *
     * @param displayName the human-readable name of the bet type
     * @param payout the multiplier used to calculate the payout
     */
    BlackJackBetType(final String displayName, final double payout) {
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
