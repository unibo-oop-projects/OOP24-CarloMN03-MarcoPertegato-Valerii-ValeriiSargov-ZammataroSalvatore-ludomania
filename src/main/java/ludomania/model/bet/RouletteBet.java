package ludomania.model.bet;

import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.croupier.roulette.RouletteColor;

import java.util.*;
import java.util.function.BiFunction;

public class RouletteBet extends Bet {

    public final BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> success;

    private final Set<Object> choice;

    public RouletteBet(
            BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> success,
            Set<Object> choice,
            double value,
            BetType type
    ) {
        super(value, type);
        this.success = success;
        this.choice = choice;
    }

    @Override
    public Double evaluate() {
        return getValue() + (getValue() * getType().getPayout());
    }

    @Override
    public String toString() {
        return String.format("%1$,.2f $, %2$s on %3$s", this.getValue(), this.getType().getTypeName(), this.getChoice().toString());
    }

    public Set<Object> getChoice() {
        return this.choice;
    }
}
