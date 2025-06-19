package ludomania.model.game.impl;

import java.util.Map;

import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

/**
 * Represents the result of a blackjack game for multiple players.
 * This class extends {@link CounterResult} and stores a mapping between each player
 * and their corresponding {@link BlackJackOutcomeResult}.
 */
public class BlackJackResult extends CounterResult<Map<Player, BlackJackOutcomeResult>> {

    /**
     * Constructs a BlackJackResult with the specified result map.
     *
     * @param results a map associating each player with their blackjack outcome result
     */
    public BlackJackResult(Map<Player, BlackJackOutcomeResult> results) {
        super(results);
    }

    /**
     * Returns the result of the blackjack game.
     * This is a map of players and their corresponding outcome results.
     *
     * @return a map where each key is a player and the value is their outcome result
     */
    @Override
    public Map<Player, BlackJackOutcomeResult> getResult() {
        return getResult();
    }
}


