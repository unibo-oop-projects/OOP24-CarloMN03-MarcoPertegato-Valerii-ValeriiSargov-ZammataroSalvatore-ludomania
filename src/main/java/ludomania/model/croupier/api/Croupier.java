package ludomania.model.croupier.api;

import java.util.List;
import java.util.Map;

import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public interface Croupier<T> {

    void give(Player player);

    Map<Player, Double> checkBets(List<Player> player, CounterResult<T> result);
}