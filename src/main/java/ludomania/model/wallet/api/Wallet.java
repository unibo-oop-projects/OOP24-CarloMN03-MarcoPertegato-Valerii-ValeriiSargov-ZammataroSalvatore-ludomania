package ludomania.model.wallet.api;

/**
 * Interface for managing a wallet's balance.
 * <p>
 * This interface provides methods for withdrawing money, depositing funds,
 * and checking the current balance. Implementations should ensure that
 * withdrawals do not exceed the available balance.
 */
public interface Wallet {
    /**
     * Withdraws a specified amount of money from the wallet.
     * 
     * @param amount the amount to withdraw
     * @return {@code true} if the withdrawal was successful, {@code false} if
     *         insufficient funds
     */
    boolean withdraw(Double amount);

    /**
     * Deposits a specified amount of money into the wallet.
     * 
     * @param amount the amount to deposit
     * @return {@code true} after successfully depositing the amount
     */
    boolean deposit(Double amount);

    /**
     * Returns the current balance of the wallet.
     * 
     * @return the current balance of the wallet
     */
    Double getMoney();
}
