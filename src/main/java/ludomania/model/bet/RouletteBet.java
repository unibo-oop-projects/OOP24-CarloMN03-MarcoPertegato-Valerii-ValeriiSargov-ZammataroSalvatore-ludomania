package ludomania.model.bet;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;

public class RouletteBet {
    public Bet pleinBet(double amount, int number) {
        return new Bet(amount, RouletteBetType.PLEIN) {

            @Override
            public Double evaluate() {
                return 0.0;
            }
        };
    }

}
