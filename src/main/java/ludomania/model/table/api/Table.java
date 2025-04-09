package ludomania.model.table.api;

import java.util.Map;

import ludomania.model.player.api.Player;

public interface Table {
    void payUp(Map<Player, Double> winner);

}