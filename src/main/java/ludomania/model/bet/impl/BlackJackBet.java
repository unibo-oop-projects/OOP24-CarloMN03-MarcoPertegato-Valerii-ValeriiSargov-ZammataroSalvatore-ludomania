package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;

public class BlackJackBet extends Bet{

    public BlackJackBet(double value, BlackJackBetType type) {
        super(value, type);
    }

    @Override
    public Double evaluate() {
        return value * type.getPayout();
    }

}