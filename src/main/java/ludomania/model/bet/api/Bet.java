package ludomania.model.bet.api;

public abstract class Bet {
    protected final double value;
    protected final BetType type;

    public Bet(double value, BetType type) {
        this.value = value;
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public BetType getType() {
        return type;
    }

    public abstract Double evaluate();
}
