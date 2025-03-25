package ludomania.model.croupier.api;

import ludomania.model.player.api.Player;

public interface Croupier {

    void give(Player player);

    Double checkBets(Player player);
}