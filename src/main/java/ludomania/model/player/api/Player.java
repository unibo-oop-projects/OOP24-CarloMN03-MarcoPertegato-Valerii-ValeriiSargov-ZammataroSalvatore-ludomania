package ludomania.model.player.api;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.wallet.api.Wallet;

public abstract class Player {
    private final Wallet wallet;

    public Player(Wallet wallet) {
        this.wallet = wallet;
    }

    public boolean deposit(Double amount) {
        return wallet.deposit(amount);
    }

    public boolean withdraw(Double amount) {
        return wallet.withdraw(amount);
    }

    abstract public Bet makeBet(Double amount, BetType type);
}