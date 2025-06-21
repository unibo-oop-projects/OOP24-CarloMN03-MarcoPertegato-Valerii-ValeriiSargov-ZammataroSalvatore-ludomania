package ludomania.model.bet;

import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteWheel;

import java.util.Objects;
import java.util.Set;

public final class RouletteBetFactory {
    private RouletteBetFactory() { }

    public static RouletteBet pleinBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> {
                    if (choices != null && !choices.isEmpty()) {
                        return choices.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + choices);
                    }
                },
                choice,
                amount,
                RouletteBetType.PLEIN);
    }

    public static RouletteBet chevalBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> {
                    if (choices != null && !choices.isEmpty()) {
                        return choices.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + choices);
                    }
                },
                choice,
                amount,
                RouletteBetType.CHEVAL);
    }

    public static RouletteBet carreBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> {
                    if (choices != null && !choices.isEmpty()) {
                        return choices.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + choices);
                    }
                },
                choice,
                amount,
                RouletteBetType.CARRE);
    }

    public static RouletteBet douzaineBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> {
                    if (choices != null && !choices.isEmpty()) {
                        return choices.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + choices);
                    }
                },
                choice,
                amount,
                RouletteBetType.DOUZAINE);
    }

    public static RouletteBet colonneBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> {
                    if (choices != null && !choices.isEmpty()) {
                        return choices.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + choices);
                    }
                },
                choice,
                amount,
                RouletteBetType.COLONNE);
    }

    public static RouletteBet pairBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr, choices) -> cr.getKey() != 0 && cr.getKey() % 2 == 0,
                choice,
                amount,
                RouletteBetType.PAIR);
    }

    public static RouletteBet impairBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getKey() != 0 && cr.getKey() % 2 != 0,
                choice,
                amount,
                RouletteBetType.IMPAIR);
    }

    public static RouletteBet passeBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr,choices) -> RouletteWheel.passe().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                choice,
                amount,
                RouletteBetType.PASSE);
    }

    public static RouletteBet manqueBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr,choices) -> RouletteWheel.manque().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                choice,
                amount,
                RouletteBetType.MANQUE);
    }

    public static RouletteBet rougeBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getValue() == RouletteColor.ROUGE,
                choice,
                amount,
                RouletteBetType.ROUGE);
    }

    public static RouletteBet noirBet(final Set<Object> choice, final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getValue() == RouletteColor.NOIR,
                choice,
                amount,
                RouletteBetType.NOIR);
    }
}
