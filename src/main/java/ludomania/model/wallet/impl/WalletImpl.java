package ludomania.model.wallet.impl;

import javafx.beans.property.DoubleProperty;
import ludomania.model.wallet.api.Wallet;

public class WalletImpl implements Wallet {
    private DoubleProperty money;

    @Override
    public boolean withdraw(Double amount) {
        if (money.getValue() - amount >= 0) {
            money.subtract(amount).doubleValue();
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(Double amount) {
        money.add(amount).doubleValue();
        return true;
    }

}
