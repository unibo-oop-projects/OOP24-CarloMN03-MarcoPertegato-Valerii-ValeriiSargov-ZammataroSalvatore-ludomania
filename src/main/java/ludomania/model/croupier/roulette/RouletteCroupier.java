package ludomania.model.croupier.roulette;

import ludomania.model.Pair;
import ludomania.model.bet.RouletteBet;
import ludomania.model.croupier.api.Croupier;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.api.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Croupier for Roulette game.
 * <p>
 *     Keeps track of the round bets and evaluates them.
 * </p>
 */
public class RouletteCroupier extends Croupier<Pair<Integer, RouletteColor>> {

    /**
     * Instantiate round bets to empty collection.
     */
    public RouletteCroupier() {
        super(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     * @param result the outcome of the round to use for bet evaluation.
     * @return
     */
    @Override
    public Map<Player, Double> checkBets(final CounterResult<Pair<Integer, RouletteColor>> result) {
        final Map<Player, Double> winningBets = new HashMap<>();

        this.getRoundBet().forEach(bet -> {
            if (bet.getValue() instanceof RouletteBet rouletteBet) {
                if (rouletteBet.getSuccess().apply(result.getResult(), rouletteBet.getChoice())) {
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
}
