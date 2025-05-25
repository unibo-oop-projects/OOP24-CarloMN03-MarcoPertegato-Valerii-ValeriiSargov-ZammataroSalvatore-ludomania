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
public class BlackJackPlayer extends Player{

    private final UUID id = UUID.randomUUID();
    private Bet currentBet;

    /**
     * Constructs a new BlackJackPlayer with an associated wallet.
     *
     * @param wallet the player's wallet used for betting and deposits
     */
    public BlackJackPlayer(Wallet wallet, String username) {
        super(wallet, username);
    }

    /**
     * Places a bet for this player in a Blackjack game.
     *
     * @param amount the amount to bet
     * @param type the type of bet (must be of type BlackJackBetType)
     * @return the created bet
     * @throws IllegalArgumentException if the bet type is not Blackjack or balance is insufficient
     */
    @Override
    public Bet makeBet(Double amount, BetType type) {
        if(!(type instanceof BlackJackBetType)) {
            throw new IllegalArgumentException("Invalid bet type for BlackJack");
        }
        if (!withdraw(amount)) {
            throw new IllegalArgumentException("Not enough money to place a bet.");
        }
        this.currentBet = new BlackJackBet(amount, (BlackJackBetType) type);
        return currentBet;
    }

    // Returns the player's current balance
    public double getBalance() {
        return wallet.getMoney();
    }

    // Returns the player's most recent placed bet
    public Bet getPlacedBet() {
        return this.currentBet;
    }

    // Returns the unique identifier of the player
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlackJackPlayer)) return false;
        BlackJackPlayer that = (BlackJackPlayer) o;
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
