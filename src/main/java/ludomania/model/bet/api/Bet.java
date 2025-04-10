package ludomania.model.bet.api;

public abstract class Bet {
    protected final double value;

    public Bet(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public abstract Double evaluate();
}
