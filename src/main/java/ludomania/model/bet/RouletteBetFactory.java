package ludomania.model.bet;

import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteWheel;

import java.util.Objects;
import java.util.Set;

public class RouletteBetFactory {
    public static RouletteBet PleinBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,coll) -> {
                    if (coll != null && !coll.isEmpty()) {
                        return coll.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + coll);
                    }
                },
                choice,
                amount,
                RouletteBetType.PLEIN);
    }

    public static RouletteBet ChevalBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,coll) -> {
                    if (coll != null && !coll.isEmpty()) {
                        return coll.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + coll);
                    }
                },
                choice,
                amount,
                RouletteBetType.CHEVAL);
    }

    public static RouletteBet CarreBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,coll) -> {
                    if (coll != null && !coll.isEmpty()) {
                        return coll.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + coll);
                    }
                },
                choice,
                amount,
                RouletteBetType.CARRE);
    }

    public static RouletteBet DouzaineBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,coll) -> {
                    if (coll != null && !coll.isEmpty()) {
                        return coll.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + coll);
                    }
                },
                choice,
                amount,
                RouletteBetType.DOUZAINE);
    }

    public static RouletteBet ColonneBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,coll) -> {
                    if (coll != null && !coll.isEmpty()) {
                        return coll.stream().anyMatch(c -> Objects.equals(c, cr.getKey()));
                    } else {
                        throw new IllegalArgumentException("Invalid choice " + coll);
                    }
                },
                choice,
                amount,
                RouletteBetType.COLONNE);
    }

    public static RouletteBet PairBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> cr.getKey() != 0 && cr.getKey() % 2 == 0,
                choice,
                amount,
                RouletteBetType.PAIR);
    }

    public static RouletteBet ImpairBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> cr.getKey() != 0 && cr.getKey() % 2 != 0,
                choice,
                amount,
                RouletteBetType.IMPAIR);
    }

    public static RouletteBet PasseBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> RouletteWheel.passe().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                choice,
                amount,
                RouletteBetType.PASSE);
    }

    public static RouletteBet ManqueBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> RouletteWheel.manque().stream().anyMatch(c -> Objects.equals(c, cr.getKey())),
                choice,
                amount,
                RouletteBetType.MANQUE);
    }

    public static RouletteBet RougeBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> cr.getValue() == RouletteColor.ROUGE,
                choice,
                amount,
                RouletteBetType.ROUGE);
    }

    public static RouletteBet NoirBet(Set<Object> choice, double amount) {
        return new RouletteBet(
                (cr,b) -> cr.getValue() == RouletteColor.NOIR,
                choice,
                amount,
                RouletteBetType.NOIR);
    }
}
