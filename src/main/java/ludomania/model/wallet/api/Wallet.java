package ludomania.model.wallet.api;

public interface Wallet {

    Double withdraw(Double amount);

    Double deposit(Double amount);
}