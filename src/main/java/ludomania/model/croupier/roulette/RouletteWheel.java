package ludomania.model.croupier.roulette;

import ludomania.model.Pair;

import java.util.Map;
import java.util.Set;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Roulette game Wheel.
 * <p>
 *     Extracts numbers and supplies static bet sets.
 * </p>
 */
public class RouletteWheel {
    static final Integer MAX = 36;
    static final Integer FIRST_DOUZAINE_BOTTOM_BUOND = 1;
    static final Integer FIRST_DOUZAINE_UP_BUOND = 12;
    static final Integer SECOND_DOUZAINE_UP_BUOND = 24;
    static final Integer THIRD_DOUZAINE_UP_BUOND = 36;
    static final Integer FIRST_HALF_UP_BOUND = 18;
    static final Integer FIRST_HALF_BOTTOM_BOUND = 1;

    /**
     * All the possible entries for the french Roulette game.
     */
    public static final Map<Integer, RouletteColor> ROULETTE_NUMBERS = Map.ofEntries(
            Map.entry(0, RouletteColor.VERT),
            Map.entry(1, RouletteColor.ROUGE),
            Map.entry(2, RouletteColor.NOIR),
            Map.entry(3, RouletteColor.ROUGE),
            Map.entry(4, RouletteColor.NOIR),
            Map.entry(5, RouletteColor.ROUGE),
            Map.entry(6, RouletteColor.NOIR),
            Map.entry(7, RouletteColor.ROUGE),
            Map.entry(8, RouletteColor.NOIR),
            Map.entry(9, RouletteColor.ROUGE),
            Map.entry(10, RouletteColor.NOIR),
            Map.entry(11, RouletteColor.NOIR),
            Map.entry(12, RouletteColor.ROUGE),
            Map.entry(13, RouletteColor.NOIR),
            Map.entry(14, RouletteColor.ROUGE),
            Map.entry(15, RouletteColor.NOIR),
            Map.entry(16, RouletteColor.ROUGE),
            Map.entry(17, RouletteColor.NOIR),
            Map.entry(18, RouletteColor.ROUGE),
            Map.entry(19, RouletteColor.ROUGE),
            Map.entry(20, RouletteColor.NOIR),
            Map.entry(21, RouletteColor.ROUGE),
            Map.entry(22, RouletteColor.NOIR),
            Map.entry(23, RouletteColor.ROUGE),
            Map.entry(24, RouletteColor.NOIR),
            Map.entry(25, RouletteColor.ROUGE),
            Map.entry(26, RouletteColor.NOIR),
            Map.entry(27, RouletteColor.ROUGE),
            Map.entry(28, RouletteColor.NOIR),
            Map.entry(29, RouletteColor.NOIR),
            Map.entry(30, RouletteColor.ROUGE),
            Map.entry(31, RouletteColor.NOIR),
            Map.entry(32, RouletteColor.ROUGE),
            Map.entry(33, RouletteColor.NOIR),
            Map.entry(34, RouletteColor.ROUGE),
            Map.entry(35, RouletteColor.NOIR),
            Map.entry(36, RouletteColor.ROUGE));

    /**
     * The first colonne numbers for french Roulette game.
     */
    public final static Set<Object> FIRST_COLONNE = Set.of(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34);
    /**
     * The second colonne numbers for french Roulette game.
     */
    public final static Set<Object> SECOND_COLONNE = Set.of(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35);
    /**
     * The third colonne numbers for french Roulette game.
     */
    public final static Set<Object> THIRD_COLONNE = Set.of(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36);

    private RouletteWheel() { }

    /**
     * Extracts a random number for french Roulette game.
     * @return a {@link Pair} containing the number extracted with corresponding color
     */
    public static Pair<Integer, RouletteColor> random() {
        final Integer number = new Random().nextInt(MAX + 1);
        return new Pair<>(number, ROULETTE_NUMBERS.get(number));
    }

    /**
     * The french Roulette game number corresponding color.
     * @param number
     * @return the color of the number.
     */
    public static RouletteColor color(final Integer number) {
        return ROULETTE_NUMBERS.get(number);
    }

    /**
     * The first douzaine numbers for french Roulette game.
     * @return a {@link Set<Integer>} containing the first douzaine numbers.
     */
    public static Set<Object> firstDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= FIRST_DOUZAINE_BOTTOM_BUOND && key <= FIRST_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    /**
     * The second douzaine numbers for french Roulette game.
     * @return a {@link Set<Integer>} containing the second douzaine numbers.
     */
    public static Set<Object> secondDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= (FIRST_DOUZAINE_UP_BUOND + 1) && key <= SECOND_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    /**
     * The third douzaine numbers for french Roulette game.
     * @return a {@link Set<Integer>} containing the third douzaine numbers.
     */
    public static Set<Object> thirdDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= (SECOND_DOUZAINE_UP_BUOND + 1) && key <= THIRD_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    /**
     * The manque numbers for french Roulette game.
     * @return a {@link Set<Integer>} containing the manque numbers.
     */
    public static Set<Object> manque() {
        return IntStream.rangeClosed(FIRST_HALF_BOTTOM_BOUND, FIRST_HALF_UP_BOUND).boxed().collect(Collectors.toSet());
    }

    /**
     * The passe numbers for french Roulette game.
     * @return a {@link Set<Integer>} containing the passe numbers.
     */
    public static Set<Object> passe() {
        return IntStream.rangeClosed(FIRST_HALF_UP_BOUND + 1, MAX).boxed().collect(Collectors.toSet());
    }

}
