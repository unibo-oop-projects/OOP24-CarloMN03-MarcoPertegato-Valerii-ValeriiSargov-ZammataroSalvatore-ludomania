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
    public BlackJackResult(final Map<Player, BlackJackOutcomeResult> results) {
        super(results);
    }

}


