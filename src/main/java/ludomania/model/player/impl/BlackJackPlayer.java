package ludomania.model.player.impl;

import java.util.Objects;
import java.util.UUID;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

public class BlackJackPlayer extends Player{

    private final UUID id = UUID.randomUUID();
    private Bet currentBet;

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
        this.currentBet = new BlackJackBet(amount, (BlackJackBetType) type);
        return currentBet;
    }

    public double getBalance() {
        return wallet.getMoney();
    }

    public Bet getPlacedBet() {
        return this.currentBet;
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlackJackPlayer)) return false;
        BlackJackPlayer that = (BlackJackPlayer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
