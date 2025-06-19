package ludomania.model.bet;

import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.croupier.roulette.RouletteColor;

import java.util.function.BiFunction;

public class RouletteBet extends Bet {

    public final BiFunction<Pair<Integer, RouletteColor>, Object, Boolean> success;

    private final Object choice;

    public RouletteBet(
            BiFunction<Pair<Integer, RouletteColor>, Object, Boolean> success,
            Object choice,
            double value,
            BetType type
    ) {
        super(value, type);
        this.success = success;
        this.choice = choice;
    }

    @Override
    public Double evaluate() {
        return this.value + this.value * this.type.getPayout();
    }

    public Object getChoice() {
        return this.choice;
    }

//    public RouletteBet PleinBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.PLEIN);
//    }
//
//    public RouletteBet ChevalBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.CHEVAL);
//    }
//
//    public RouletteBet CarreBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.CARRE);
//    }
//
//    public RouletteBet DouzaineBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.DOUZAINE);
//    }
//
//    public RouletteBet ColonneBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.COLONNE);
//    }
//
//    public RouletteBet PairBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.PAIR);
//    }
//
//    public RouletteBet ImpairBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.IMPAIR);
//    }
//
//    public RouletteBet PasseBet(Set<Integer> winningNumbers, double amount) {
//        return new RouletteBet(winningNumbers, (cr,b) -> b.contains(cr.getKey()), amount, RouletteBetType.PASSE);
//    }
}
