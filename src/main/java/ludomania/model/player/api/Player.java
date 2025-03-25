package ludomania.model.player.api;

import java.util.List;

import ludomania.model.bet.api.Bet;

public interface Player {

    List<Bet> getBets();

    void makeBet(Bet singleBet);
}