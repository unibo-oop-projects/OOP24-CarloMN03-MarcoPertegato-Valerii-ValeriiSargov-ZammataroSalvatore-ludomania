package ludomania.model.croupier.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.api.Player;

/**
 * Abstract class representing a generic game croupier (dealer).
 * <p>
 * A croupier is responsible for collecting bets from players and
 * evaluating them based on the outcome of a game round.
 *
 * @param <T> the type of result produced by the game logic (e.g., card color, number)
 */
public abstract class Croupier<T> {
    private final List<Pair<Player, Bet>> roundBet;

    /**
     * Constructs a new croupier with the given list of current round bets.
     *
     * @param roundBet the list of player-bet pairs for the current round
     */
    public Croupier(final List<Pair<Player, Bet>> roundBet) {
        this.roundBet = new LinkedList<>(roundBet);
    }

     /**
     * Adds a bet placed by a player to the current round.
     *
     * @param player the player placing the bet
     * @param bet    the bet placed by the player
     */
    public void addBet(final Player player, final Bet bet) {
        roundBet.add(new Pair<>(player, bet));
    }

    /**
     * Clears all bets placed in the current round.
     * This method is typically called at the end of a round to prepare for a new one.
     */
    public void clearRound() {
        roundBet.clear();
    }

    /**
     * Returns the list of all bets placed in the current round.
     *
     * @return a list of player-bet pairs
     */
    public List<Pair<Player, Bet>> getRoundBet() {
        return new LinkedList<>(roundBet);
    }

    /**
     * Evaluates all bets based on the given game result and returns
     * a map of players to their winnings (if any).
     *
     * @param result the outcome of the round to use for bet evaluation
     * @return a map associating each winning player with the amount won
     */
    public abstract Map<Player, Double> checkBets(CounterResult<T> result);
}
