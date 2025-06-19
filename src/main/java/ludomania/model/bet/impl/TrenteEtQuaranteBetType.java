package ludomania.model.bet.impl;

import ludomania.model.bet.api.BetType;

/**
 * Enum representing the possible bet types in the game of Trente et Quarante.
 * <p>
 * Each bet type has a display name and a payout multiplier used to
 * evaluate winnings. This enum implements the {@link BetType} interface.
 */
public enum TrenteEtQuaranteBetType implements BetType {
    /**
     * Bet on the Rouge (Red) line.
     */
    ROUGE("Rouge", 1.0),
    /**
     * Bet on the Noir (Black) line.
     */
    NOIR("Noir", 1.0),
    /**
     * Bet on Couleur (when the color of the first card matches the color of the winning row).
     */
    COULEUR("Couleur", 1.0),
    /**
     * Bet on Enverse (opposite of Couleur).
     */
    ENVERSE("Enverse", 1.0),
    /**
     * Represents a draw situation where no player wins.
     * Used for evaluation purposes.
     */
    DRAW("Draw", 0.0);

    private final String typeName;
    private final double payout;

    /**
     * Constructs a TrenteEtQuaranteBetType with the specified name and payout.
     *
     * @param displayName the name to display
     * @param payout      the payout multiplier for this bet type
     */
    TrenteEtQuaranteBetType(final String displayName, final double payout) {
        this.typeName = displayName;
        this.payout = payout;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Double getPayout() {
        return payout;
    }
}
