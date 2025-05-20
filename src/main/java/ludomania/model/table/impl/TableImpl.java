package ludomania.model.table.impl;

import java.util.List;
import java.util.Map;

import ludomania.model.croupier.api.Croupier;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.table.api.Table;

public class TableImpl<T> implements Table {
    private final Game<T> game;
    private final Croupier<T> croupier;

    public TableImpl(Game<T> game, Croupier<T> croupier) {
        this.game = game;
        this.croupier = croupier;
    }

    @Override
    public void openTable(List<Player> players) {
        Map<Player, Double> winners;
        CounterResult<T> result = game.runGame();
        winners = croupier.checkBets(result);
        payUp(winners);
    }

    @Override
    public void payUp(Map<Player, Double> winner) {
        for (Map.Entry<Player, Double> entry : winner.entrySet()) {
            Player player = entry.getKey();
            double amount = entry.getValue();
            player.deposit(amount);
        }
    }

}
