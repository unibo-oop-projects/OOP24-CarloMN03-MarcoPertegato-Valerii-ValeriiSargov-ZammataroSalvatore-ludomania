package ludomania.model;

/**
 * A generic immutable pair of two elements.
 *
 * @param <E> the type of the first element (key)
 * @param <G> the type of the second element (value)
 */
public class Pair<E, G> {
    private final E e;
    private final G g;

    /**
     * Constructs a new pair with the given elements.
     *
     * @param x the first element (key)
     * @param y the second element (value)
     */
    public Pair(final E x, final G y) {
        super();
        this.e = x;
        this.g = y;
    }

    /**
     * Returns the first element of the pair (key).
     *
     * @return the key
     */
    public E getKey() {
        return e;
    }

    /**
     * Returns the second element of the pair (value).
     *
     * @return the value
     */
    public G getValue() {
        return g;
    }
}
