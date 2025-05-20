package ludomania.model.player.impl;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

public class BlackJackPlayer extends Player{

    public BlackJackPlayer(Wallet wallet) {
        super(wallet);
    }

    @Override
    public Bet makeBet(Double amount, BetType type) {
        if(!(type instanceof BlackJackBetType)) {
            throw new IllegalArgumentException("Invalid bet type for BlackJack");
        }
        if (!withdraw(amount)) {
            throw new IllegalArgumentException("Not enough money to place a bet.");
        }
        return new BlackJackBet(amount, (BlackJackBetType) type);
    }

    public double getBalance() {
        return wallet.getMoney();
    }
}
