package ludomania.model;

/**
 * A generic immutable pair of two elements.
 *
 * @param <E1> the type of the first element (key)
 * @param <E2> the type of the second element (value)
 */
public class Pair<E1, E2> {
    private final E1 e1;
    private final E2 e2;

    /**
     * Constructs a new pair with the given elements.
     *
     * @param x the first element (key)
     * @param y the second element (value)
     */
    public Pair(final E1 x, final E2 y) {
        super();
        this.e1 = x;
        this.e2 = y;
    }

    /**
     * Returns the first element of the pair (key).
     *
     * @return the key
     */
    public E1 getKey() {
        return e1;
    }

    /**
     * Returns the second element of the pair (value).
     *
     * @return the value
     */
    public E2 getValue() {
        return e2;
    }
}
