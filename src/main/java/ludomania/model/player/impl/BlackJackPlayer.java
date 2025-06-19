package ludomania.model.player.impl;

import java.util.Objects;
import java.util.UUID;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

/**
 * Represents a player in a Blackjack game.
 * Manages the player's identity, wallet, and betting actions.
 */
public class BlackJackPlayer extends Player {

    private final UUID id = UUID.randomUUID();
    private Bet currentBet;

    /**
     * Constructs a new BlackJackPlayer with the provided wallet and username.
     *
     * @param wallet   the player's wallet used for storing balance and handling bets
     * @param username the player's display name
     */
    public BlackJackPlayer(final Wallet wallet, final String username) {
        super(wallet, username);
    }

    /**
     * Places a Blackjack-specific bet for the player.
     * The method checks that the bet type is compatible with Blackjack and that the player has sufficient funds.
     *
     * @param amount the amount of money to bet
     * @param type   the type of the bet (must be an instance of {@link BlackJackBetType})
     * @return the bet created and stored as the current bet
     * @throws IllegalArgumentException if the bet type is invalid or if the player has insufficient funds
     */
    @Override
    public Bet makeBet(final Double amount, final BetType type) {
        if (!(type instanceof BlackJackBetType)) {
            throw new IllegalArgumentException("Invalid bet type for BlackJack");
        }
        if (!withdraw(amount)) {
            throw new IllegalArgumentException("Not enough money to place a bet.");
        }
        this.currentBet = new BlackJackBet(amount, (BlackJackBetType) type);
        return currentBet;
    }

    /**
     * Returns the most recent bet placed by the player.
     *
     * @return the current {@link Bet} placed, or {@code null} if none has been placed
     */
    public Bet getPlacedBet() {
        return this.currentBet;
    }

    /**
     * Returns the unique identifier of this player.
     *
     * @return the UUID representing this player
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Checks equality based on the player's unique ID.
     *
     * @param o the object to compare
     * @return true if the other object is a BlackJackPlayer with the same ID
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlackJackPlayer)) {
            return false;
        }
        final BlackJackPlayer that = (BlackJackPlayer) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Computes a hash code based on the player's ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
