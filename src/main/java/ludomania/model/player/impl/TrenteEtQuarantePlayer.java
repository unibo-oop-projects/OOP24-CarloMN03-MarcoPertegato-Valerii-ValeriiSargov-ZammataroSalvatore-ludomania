package ludomania.model.player.impl;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

/**
 * Player implementation for the game Trente et Quarante.
 * <p>
 * Handles the creation of valid bets for this specific game.
 */
public class TrenteEtQuarantePlayer extends Player {

    /**
     * Constructs a TrenteEtQuarantePlayer with the given wallet and username.
     *
     * @param wallet the player's wallet
     * @param username the player's username
     */
    public TrenteEtQuarantePlayer(final Wallet wallet, final String username) {
        super(wallet, username);
    }

    /**
     * Creates a Trente et Quarante bet after checking the type and available balance.
     *
     * @param amount the amount to bet
     * @param type the type of bet (must be {@link TrenteEtQuaranteBetType})
     * @return the created {@link TrenteEtQuaranteBet}
     * @throws IllegalArgumentException if the bet type is invalid or the balance is insufficient
     */
    @Override
    public Bet makeBet(final Double amount, final BetType type) {
        if (!(type instanceof TrenteEtQuaranteBetType)) {
            throw new IllegalArgumentException("Invalid bet type for Trente et Quarante.");
        }
        if (!withdraw(amount)) {
            throw new IllegalArgumentException("Not enough money to place a bet.");
        }
        return new TrenteEtQuaranteBet(amount, (TrenteEtQuaranteBetType) type);
    }

}
