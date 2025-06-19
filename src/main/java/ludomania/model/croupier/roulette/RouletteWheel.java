package ludomania.model.croupier.roulette;

import ludomania.model.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouletteWheel {
    private static final Integer MAX = 36;
    private static final Integer MIN = 0;

    public static final Map<Integer, RouletteColor> rouletteNumbers = Map.ofEntries(
            Map.entry(0, RouletteColor.GREEN),
            Map.entry(1, RouletteColor.ROUGE),
            Map.entry(2, RouletteColor.GREEN),
            Map.entry(3, RouletteColor.ROUGE),
            Map.entry(4, RouletteColor.GREEN),
            Map.entry(5, RouletteColor.ROUGE),
            Map.entry(6, RouletteColor.GREEN),
            Map.entry(7, RouletteColor.ROUGE),
            Map.entry(8, RouletteColor.GREEN),
            Map.entry(9, RouletteColor.ROUGE),
            Map.entry(10, RouletteColor.GREEN),
            Map.entry(11, RouletteColor.GREEN),
            Map.entry(12, RouletteColor.ROUGE),
            Map.entry(13, RouletteColor.GREEN),
            Map.entry(14, RouletteColor.ROUGE),
            Map.entry(15, RouletteColor.GREEN),
            Map.entry(16, RouletteColor.ROUGE),
            Map.entry(17, RouletteColor.GREEN),
            Map.entry(18, RouletteColor.ROUGE),
            Map.entry(19, RouletteColor.ROUGE),
            Map.entry(20, RouletteColor.GREEN),
            Map.entry(21, RouletteColor.ROUGE),
            Map.entry(22, RouletteColor.GREEN),
            Map.entry(23, RouletteColor.ROUGE),
            Map.entry(24, RouletteColor.GREEN),
            Map.entry(25, RouletteColor.ROUGE),
            Map.entry(26, RouletteColor.GREEN),
            Map.entry(27, RouletteColor.ROUGE),
            Map.entry(28, RouletteColor.GREEN),
            Map.entry(29, RouletteColor.GREEN),
            Map.entry(30, RouletteColor.ROUGE),
            Map.entry(31, RouletteColor.GREEN),
            Map.entry(32, RouletteColor.ROUGE),
            Map.entry(33, RouletteColor.GREEN),
            Map.entry(34, RouletteColor.ROUGE),
            Map.entry(35, RouletteColor.GREEN),
            Map.entry(36, RouletteColor.ROUGE));

    public static Pair<Integer, RouletteColor> random() {
        Integer number = new Random().nextInt(MAX - MIN) + MIN;
        return new Pair<>(number, rouletteNumbers.get(number));
    }

    public static RouletteColor color(Integer number) {
        return rouletteNumbers.get(number);
    }

    public static Set<Object> firstDouzaine() {
        return rouletteNumbers.keySet().stream()
                .filter(key -> key >= 1 && key <= 12)
                .collect(Collectors.toSet());
    }

    public static Set<Object> secondDouzaine() {
        return rouletteNumbers.keySet().stream()
                .filter(key -> key >= 13 && key <= 24)
                .collect(Collectors.toSet());
    }

    public static Set<Object> thirdDouzaine() {
        return rouletteNumbers.keySet().stream()
                .filter(key -> key >= 25 && key <= 36)
                .collect(Collectors.toSet());
    }

    public static Set<Object> firstColonne() {
        return new HashSet<>(List.of(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34));
    }

    public static Set<Object> secondColonne() {
        return new HashSet<>(List.of(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35));
    }

    public static Set<Object> thirdColonne() {
        return new HashSet<>(List.of(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36));
    }

    public static Set<Object> manque() {
        return IntStream.rangeClosed(1, 18).boxed().collect(Collectors.toSet());
    }

    public static Set<Object> passe() {
        return IntStream.rangeClosed(19, MAX).boxed().collect(Collectors.toSet());
    }

}
