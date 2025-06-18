package ludomania.model.croupier.api;

import java.util.List;
import java.util.Map;

import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public abstract class Croupier<T> {
    private final List<Pair<Player,Bet>> roundBet;

    public Croupier(final List<Pair<Player,Bet>> roundBet) {
        this.roundBet = roundBet;
    }

    public void addBet(final Player player, final Bet bet) {
        roundBet.add(new Pair<>(player, bet));
    }

    public void clearRound() {
        roundBet.clear();
    }

    public List<Pair<Player,Bet>> getRoundBet() {
        return roundBet;
    }

    public abstract Map<Player, Double> checkBets(CounterResult<T> result);
}
