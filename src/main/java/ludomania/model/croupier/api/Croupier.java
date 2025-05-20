package ludomania.model.croupier.api;

import java.util.Map;

import ludomania.model.bet.api.Bet;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public abstract class Croupier<T> {
    private final Map<Player, Bet> roundBet;

    public Croupier(final Map<Player, Bet> roundBet) {
        this.roundBet = roundBet;
    }

    public void addBet(final Player player, final Bet bet) {
        roundBet.put(player, bet);
    }

    public void clearRound() {
        roundBet.clear();
    }

    public Map<Player, Bet> getRoundBet() {
        return roundBet;
    }

    public abstract Map<Player, Double> checkBets(CounterResult<T> result);
}
