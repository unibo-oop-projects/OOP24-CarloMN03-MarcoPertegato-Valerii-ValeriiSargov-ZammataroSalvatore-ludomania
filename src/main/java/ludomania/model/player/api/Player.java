package ludomania.model.player.api;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.wallet.api.Wallet;

/**
 * Abstract class representing a player in a casino game.
 * <p>
 * A player has a username and a wallet to manage their balance.
 * Concrete subclasses must implement how a bet is created.
 */
public abstract class Player {
    private final Wallet wallet;
    private final String username;

    /**
     * Constructs a player with a given wallet and username.
     *
     * @param wallet the wallet associated with the player
     * @param username the player's username
     */
    public Player(final Wallet wallet, final String username) {
        this.wallet = wallet;
        this.username = username;
    }

    /**
     * Deposits the specified amount of money into the player's wallet.
     *
     * @param amount the amount to deposit
     * @return {@code true} if successful, {@code false} otherwise
     */
    public boolean deposit(final Double amount) {
        return wallet.deposit(amount);
    }

    /**
     * Withdraws the specified amount of money from the player's wallet.
     *
     * @param amount the amount to withdraw
     * @return {@code true} if successful, {@code false} otherwise
     */
    public boolean withdraw(final Double amount) {
        return wallet.withdraw(amount);
    }

    /**
     * Returns the player's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the current balance of the player's wallet.
     *
     * @return the balance
     */
    public Double getBalance() {
        return wallet.getMoney();
    }

    /**
     * Creates a new bet based on the amount and type.
     * This must be implemented by each specific player type.
     *
     * @param amount the amount of money to bet
     * @param type the type of bet
     * @return the created {@link Bet}
     */
    public abstract Bet makeBet(Double amount, BetType type);
}
