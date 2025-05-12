package ludomania.model.table.api;

import java.util.List;
import java.util.Map;

import ludomania.model.player.api.Player;

public interface Table {

    void openTable(List<Player> players);

    void payUp(Map<Player, Double> winner);
}
