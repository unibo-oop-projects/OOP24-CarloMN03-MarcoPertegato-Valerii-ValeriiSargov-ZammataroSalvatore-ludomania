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

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    Boolean isOver();

    /**
     * Asks the user whether they want to play the game again.
     *
     * @return true if the player chooses to play again, false otherwise
     */
    Boolean playAgain();
}
