package ludomania.model.game.api;

/**
 * Interface representing a game that can be run, checked for completion, and
 * replayed.
 * <p>
 * Provides methods for executing the game logic, checking if the game is over,
 * and offering the option to play again.
 */
public interface Game<T> {
    /**
     * Runs the game and returns the result.
     * 
     * @return the result of the game
     */
    CounterResult<T> runGame();
}
