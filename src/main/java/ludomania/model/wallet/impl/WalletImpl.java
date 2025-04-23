package ludomania.model.wallet.impl;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import ludomania.model.wallet.api.Wallet;

public class WalletImpl implements Wallet {
    private final DoubleProperty money;

    public WalletImpl(Double startingAmount) {
        money = new SimpleDoubleProperty(startingAmount);
    }

    @Override
    public boolean withdraw(Double amount) {
        if (canWithdraw(amount)) {
            money.set(money.get() - amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(Double amount) {
        money.set(money.get() + amount);
        return true;
    }

    public boolean canWithdraw(Double amount) {
        return money.get() - amount >= 0;
    }

    @Override
    public Double getMoney() {
        return money.get();
    }

}
