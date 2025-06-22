package ludomania.model.bet;

import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.croupier.roulette.RouletteColor;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Represents the Roulette game Bet.
 */
public final class RouletteBet extends Bet {
    private final BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> success;
    private final Set<Object> choice;

    /**
     * Creates a new {@link RouletteBet}.
     * @param success the function that will evaluate if the bet is winning.
     * @param choice the choice operated by the player.
     * @param value the value of the bet.
     * @param type the type of the bet.
     */
    public RouletteBet(
            final double value,
            final BetType type,
            final BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> success,
            final Set<Object> choice
    ) {
        super(value, type);
        this.success = success;
        this.choice = Collections.unmodifiableSet(choice);
    }

    /**
     * Gets the success {@link BiFunction}.
     * @return the instance of private property {@code success}.
     */
    public BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> getSuccess() {
        return success;
    }

    /**
     * Evaluates the bet win.
     * @return the bet win amount.
     */
    @Override
    public Double evaluate() {
        return getValue() + (getValue() * getType().getPayout());
    }

    @Override
    public String toString() {
        return String.format(
                "%1$,.2f $, %2$s on %3$s", this.getValue(), this.getType().getTypeName(), this.getChoice().toString());
    }

    /**
     * Gets the choiche on which the bet is placed.
     * @return the corresponding color or numbers.
     */
    public Set<Object> getChoice() {
        return Collections.unmodifiableSet(this.choice);
    }
}
