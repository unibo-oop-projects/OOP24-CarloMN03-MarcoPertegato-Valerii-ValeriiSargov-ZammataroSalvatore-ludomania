package ludomania.model.player.api;

import java.util.List;

import ludomania.model.bet.api.Bet;
import ludomania.model.wallet.api.Wallet;

public interface Player {

    List<Bet> getBets();

    Wallet getWallet();

    void makeBet(Bet singleBet);
}