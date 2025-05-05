package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

public class BlackJackBet extends Bet{

    private final BlackJackBetType type;

    public BlackJackBet(double value, BlackJackBetType type) {
        super(value);
        this.type = type;
    }

    public BlackJackBetType getType() {
        return type;
    }

    @Override
    public Double evaluate() {
        return value * type.getPayout();
    }

}