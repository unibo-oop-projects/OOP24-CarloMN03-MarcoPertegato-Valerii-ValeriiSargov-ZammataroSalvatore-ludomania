package ludomania.model.player.api;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.wallet.api.Wallet;

public abstract class Player {
    private final Wallet wallet;
    private final String username;

    public Player(final Wallet wallet, final String username) {
        this.wallet = wallet;
        this.username = username;
    }

    public boolean deposit(final Double amount) {
        return wallet.deposit(amount);
    }

    public boolean withdraw(final Double amount) {
        return wallet.withdraw(amount);
    }

    public String getUsername(){
        return username;
    }
    
    public abstract Bet makeBet(Double amount, BetType type);
}
