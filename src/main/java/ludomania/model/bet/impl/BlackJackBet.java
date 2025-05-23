package ludomania.model.bet.impl;

import ludomania.model.bet.api.Bet;
import static ludomania.model.bet.impl.BlackJackBetType.BASE;
import static ludomania.model.bet.impl.BlackJackBetType.BLACKJACK;
import static ludomania.model.bet.impl.BlackJackBetType.LOSE;
import static ludomania.model.bet.impl.BlackJackBetType.PUSH;

public class BlackJackBet extends Bet {
     
    public BlackJackBet(double value, BlackJackBetType type) {
        super(value, type);
    }

    @Override
    public Double evaluate() {
        return switch (type) {
            case BASE -> value * BASE.getPayout();
            case BLACKJACK -> value * BLACKJACK.getPayout(); 
            case PUSH -> value * PUSH.getPayout();
            case LOSE -> value * LOSE.getPayout();
            default -> 0.0;
        };
    }

}
