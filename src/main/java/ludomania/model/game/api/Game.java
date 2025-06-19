package ludomania.model.game.api;

import ludomania.model.game.impl.CounterResult;

/**
 * Interface representing a game that can be run.
 * <p>
 * Provides methods for executing the game logic.
 *
 * @param <T> the type of the game result
 */
public interface Game<T> {
    /**
     * Runs the game and returns the result.
     *
     * @return the result of the game
     */
    CounterResult<T> runGame();
}
