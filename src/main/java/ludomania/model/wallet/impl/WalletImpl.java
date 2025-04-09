package ludomania.model.wallet.impl;

import javafx.beans.property.DoubleProperty;
import ludomania.model.wallet.api.Wallet;

public class WalletImpl implements Wallet {
    private DoubleProperty money;

    @Override
    public Double withdraw(Double amount) {
        return money.subtract(amount).doubleValue();
    }

    @Override
    public Double deposit(Double amount) {
        return money.add(amount).doubleValue();
    }

}
