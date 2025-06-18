package ludomania.model.game.api;

/**
 * Abstract class representing the result of the house (dealer) in a casino game.
 * <p>
 * This class acts as a generic wrapper for a result of type {@code T}, allowing
 * specialized behavior in subclasses for different games.
 *
 * @param <T> the type of the result represented.
 */
public abstract class CounterResult<T> {
    private final T result;

     /**
     * Creates a new CounterResult with the given result.
     *
     * @param result the result of the round
     */
    public CounterResult(final T result) {
        this.result = result;
    }

    /**
     * Returns the result of the round.
     *
     * @return the result
     */
    public T getResult() {
        return result;
    }
}
