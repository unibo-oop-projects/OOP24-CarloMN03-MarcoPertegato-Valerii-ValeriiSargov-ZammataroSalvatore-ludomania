package ludomania.model.table.impl;

import java.util.List;
import java.util.Map;

import ludomania.model.croupier.api.Croupier;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.table.api.Table;

public class TableImpl<T> implements Table {
    private final Game game;
    private final Croupier<T> croupier;
    private boolean playAgainFlag;

    public TableImpl(Game game, Croupier<T> croupier) {
        this.game = game;
        this.croupier = croupier;
    }

    public void openTable(List<Player> players) {
        Map<Player, Double> winners;
        while (playAgain()) {
            CounterResult<T> result = game.runGame();
            winners = croupier.checkBets(players, result);
            payUp(winners);
        }
    }

    @Override
    public void payUp(Map<Player, Double> winner) {
    }

    private boolean playAgain() {
        return playAgainFlag;
    }
}
