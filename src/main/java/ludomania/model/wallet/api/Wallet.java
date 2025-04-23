package ludomania.model.wallet.api;

public interface Wallet {

    boolean withdraw(Double amount);

    boolean deposit(Double amount);

    Double getMoney();
}