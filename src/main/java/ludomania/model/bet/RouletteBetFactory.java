package ludomania.model.bet;

import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteWheel;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a {@code factory} for the roulette game bets.
 */
public final class RouletteBetFactory {
    private RouletteBetFactory() { }

    /**
     * Creates a plein bet.
     * @param choice the player choice on which to place the bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
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

    /**
     * Creates a cheval bet.
     * @param choice the player choice on which to place the bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
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

    /**
     * Creates a carre bet.
     * @param choice the player choice on which to place the bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
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

    /**
     * Creates a douzaine bet.
     * @param choice the player choice on which to place the bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
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

    /**
     * Creates a colonne bet.
     * @param choice the player choice on which to place the bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
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

    /**
     * Creates a pair bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet pairBet(final double amount) {
        return new RouletteBet(
                (cr, choices) -> cr.getKey() != 0 && cr.getKey() % 2 == 0,
                Set.of(),
                amount,
                RouletteBetType.PAIR);
    }

    /**
     * Creates a impair bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet impairBet(final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getKey() != 0 && cr.getKey() % 2 != 0,
                Set.of(),
                amount,
                RouletteBetType.IMPAIR);
    }

    /**
     * Creates a passe bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet passeBet(final double amount) {
        return new RouletteBet(
                (cr,choices) -> RouletteWheel.passe().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                Set.of(),
                amount,
                RouletteBetType.PASSE);
    }

    /**
     * Creates a manque bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet manqueBet(final double amount) {
        return new RouletteBet(
                (cr,choices) -> RouletteWheel.manque().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                Set.of(),
                amount,
                RouletteBetType.MANQUE);
    }

    /**
     * Creates a rouge bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet rougeBet(final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getValue() == RouletteColor.ROUGE,
                Set.of(),
                amount,
                RouletteBetType.ROUGE);
    }

    /**
     * Creates a noir bet.
     * @param amount the value of the bet.
     * @return the new bet instance.
     */
    public static RouletteBet noirBet(final double amount) {
        return new RouletteBet(
                (cr,choices) -> cr.getValue() == RouletteColor.NOIR,
                Set.of(),
                amount,
                RouletteBetType.NOIR);
    }
}
