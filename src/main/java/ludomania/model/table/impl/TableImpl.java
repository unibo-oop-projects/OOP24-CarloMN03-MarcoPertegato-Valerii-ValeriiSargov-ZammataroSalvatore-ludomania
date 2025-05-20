package ludomania.model.table.impl;

import java.util.List;
import java.util.Map;

import ludomania.model.croupier.api.Croupier;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.table.api.Table;

public final class TableImpl<T> implements Table {
    private final Game<T> game;
    private final Croupier<T> croupier;

    public TableImpl(final Game<T> game, final Croupier<T> croupier) {
        this.game = game;
        this.croupier = croupier;
    }

    @Override
    public void openTable(final List<Player> players) {
        final Map<Player, Double> winners;
        final CounterResult<T> result = game.runGame();
        winners = croupier.checkBets(result);
        payUp(winners);
    }

    @Override
    public void payUp(final Map<Player, Double> winner) {
        for (final Map.Entry<Player, Double> entry : winner.entrySet()) {
            final Player player = entry.getKey();
            player.deposit(entry.getValue());
        }
    }

}
