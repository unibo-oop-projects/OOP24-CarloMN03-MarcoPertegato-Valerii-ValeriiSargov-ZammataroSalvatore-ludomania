package ludomania.model.game.api;

public interface Game {

    CounterResult runGame();

    Boolean isOver();

    Boolean playAgain();
}