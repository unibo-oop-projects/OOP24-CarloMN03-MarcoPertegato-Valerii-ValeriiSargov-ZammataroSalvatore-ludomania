package ludomania.model.croupier.roulette;

import ludomania.model.Pair;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouletteWheel {
    private final static Integer MAX = 36;
    private final static Integer FIRST_DOUZAINE_BOTTOM_BUOND = 1;
    private final static Integer FIRST_DOUZAINE_UP_BUOND = 12;
    private final static Integer SECOND_DOUZAINE_UP_BUOND = 24;
    private final static Integer THIRD_DOUZAINE_UP_BUOND = 36;
    private final static Integer FIRST_HALF_UP_BOUND = 18;
    private final static Integer FIRST_HALF_BOTTOM_BOUND = 1;

    private RouletteWheel() { }

    public static final Map<Integer, RouletteColor> ROULETTE_NUMBERS = Map.ofEntries(
            Map.entry(0, RouletteColor.GREEN),
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

    public final static Set<Object> FIRST_COLONNE = Set.of(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34);
    public final static Set<Object> SECOND_COLONNE = Set.of(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35);
    public final static Set<Object> THIRD_COLONNE = Set.of(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36);

    public static Pair<Integer, RouletteColor> random() {
        Integer number = new Random().nextInt(MAX + 1);
        return new Pair<>(number, ROULETTE_NUMBERS.get(number));
    }

    public static RouletteColor color(Integer number) {
        return ROULETTE_NUMBERS.get(number);
    }

    public static Set<Object> firstDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= FIRST_DOUZAINE_BOTTOM_BUOND && key <= FIRST_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    public static Set<Object> secondDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= (FIRST_DOUZAINE_UP_BUOND + 1) && key <= SECOND_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    public static Set<Object> thirdDouzaine() {
        return ROULETTE_NUMBERS.keySet().stream()
                .filter(key -> key >= (SECOND_DOUZAINE_UP_BUOND + 1) && key <= THIRD_DOUZAINE_UP_BUOND)
                .collect(Collectors.toSet());
    }

    public static Set<Object> manque() {
        return IntStream.rangeClosed(FIRST_HALF_BOTTOM_BOUND, FIRST_HALF_UP_BOUND).boxed().collect(Collectors.toSet());
    }

    public static Set<Object> passe() {
        return IntStream.rangeClosed(FIRST_HALF_UP_BOUND + 1, MAX).boxed().collect(Collectors.toSet());
    }

}
