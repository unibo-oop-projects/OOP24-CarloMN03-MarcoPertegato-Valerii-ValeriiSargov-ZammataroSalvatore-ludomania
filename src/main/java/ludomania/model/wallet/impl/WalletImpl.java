package ludomania.model.wallet.impl;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import ludomania.model.wallet.api.Wallet;

/**
 * Implementation of the {@link Wallet} interface.
 * <p>
 * Manages a balance allowing deposit and withdrawal operations,
 * while ensuring that withdrawals do not exceed the available funds.
 */

public final class WalletImpl implements Wallet {
    private final DoubleProperty money;

    /**
     * Constructs a new {@code WalletImpl} with a specified starting balance.
     *
     * @param startingAmount the initial amount of money in the wallet
     */
    public WalletImpl(final Double startingAmount) {
        money = new SimpleDoubleProperty(startingAmount);
    }

    @Override
    public boolean withdraw(final Double amount) {
        if (canWithdraw(amount)) {
            money.set(money.get() - amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(final Double amount) {
        money.set(money.get() + amount);
        return true;
    }

    public boolean canWithdraw(final Double amount) {
        return money.get() - amount >= 0;
    }

    @Override
    public Double getMoney() {
        return money.get();
    }

}
