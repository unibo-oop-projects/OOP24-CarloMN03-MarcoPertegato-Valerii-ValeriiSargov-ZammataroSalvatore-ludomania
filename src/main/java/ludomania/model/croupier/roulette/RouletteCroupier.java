package ludomania.model.croupier.roulette;

import ludomania.model.Pair;
import ludomania.model.bet.RouletteBet;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.api.Croupier;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouletteCroupier extends Croupier<Pair<Integer, RouletteColor>> {
    public RouletteCroupier(List<Pair<Player, Bet>> roundBet) {
        super(roundBet);
    }

    public RouletteCroupier() {
        super(new ArrayList<>());
    }

    @Override
    public Map<Player, Double> checkBets(CounterResult<Pair<Integer, RouletteColor>> result) throws IllegalArgumentException {
        Map<Player, Double> winningBets = new HashMap<>();

        this.getRoundBet().forEach(bet -> {
            if (bet.getValue() instanceof RouletteBet rouletteBet) {
                if (rouletteBet.success.apply(result.getResult(), rouletteBet.getChoice())) {
                    if (winningBets.containsKey(bet.getKey())) {
                        winningBets.put(bet.getKey(), winningBets.get(bet.getKey()) + rouletteBet.evaluate());
                    } else {
                        winningBets.put(bet.getKey(), rouletteBet.evaluate());
                    }
                }
            } else {
                throw new IllegalArgumentException("Bets must be of type RouletteBet");
            }
        });

        return winningBets;
    }

    public Pair<Integer, RouletteColor> spinWheel() {
        return RouletteWheel.random();
    }
}
