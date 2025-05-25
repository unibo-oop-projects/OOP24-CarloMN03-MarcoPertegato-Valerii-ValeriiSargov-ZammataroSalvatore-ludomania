package ludomania.model.game.api;

public abstract class CounterResult<T> {
    protected final T result;

    public CounterResult(final T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
