package ludomania.cosmetics;

/**
 * The {@code FicheValue} enum defines the different possible values for fiches,
 * each associated with a specific integer amount.
 * <p>
 * It provides methods to retrieve the numerical value and to map an integer
 * to the corresponding {@code FicheValue}.
 * </p>
 */
public enum FicheValue {
    /** Fiche value of 1. */
    UNO(1),
    /** Fiche value of 5. */
    CINQUE(5),
    /** Fiche value of 10. */
    DIECI(10),
    /** Fiche value of 25. */
    VENTICINQUE(25),
    /** Fiche value of 100. */
    CENTO(100),
    /** Fiche value of 500. */
    CINQUECENTO(500);

    private static final int LAST_LIMIT = 5;

    private static final int FOURTH_LIMIT = 10;

    private static final int THIRD_LIMIT = 25;

    private static final int SECOND_LIMIT = 100;
    private static final int FIRST_LIMIT = 500;

    private final int value;

    /**
     * Constructs a {@code FicheValue} with the specified integer value.
     *
     * @param value the integer value associated with the fiche
     */
    FicheValue(final int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with this fiche.
     *
     * @return the integer value of the fiche
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the {@code FicheValue} corresponding to the given integer value.
     * <p>
     * The mapping is based on the following rules:
     * <ul>
     * <li>Value ≥ 500: {@code CINQUECENTO}</li>
     * <li>Value ≥ 100: {@code CENTO}</li>
     * <li>Value ≥ 25: {@code VENTICINQUE}</li>
     * <li>Value ≥ 10: {@code DIECI}</li>
     * <li>Value ≥ 5: {@code CINQUE}</li>
     * <li>Otherwise: {@code UNO}</li>
     * </ul>
     * </p>
     *
     * @param value the integer value to map
     * @return the corresponding {@code FicheValue}
     */
    public static FicheValue fromValue(final int value) {
        if (value >= FIRST_LIMIT) {
            return CINQUECENTO;
        }
        if (value >= SECOND_LIMIT) {
            return CENTO;
        }
        if (value >= THIRD_LIMIT) {
            return VENTICINQUE;
        }
        if (value >= FOURTH_LIMIT) {
            return DIECI;
        }
        if (value >= LAST_LIMIT) {
            return CINQUE;
        }
        return UNO;
    }

}
