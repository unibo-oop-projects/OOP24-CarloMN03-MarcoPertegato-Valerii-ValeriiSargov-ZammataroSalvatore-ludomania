package ludomania.model.croupier.api;

import java.util.Map;
import ludomania.model.bet.api.Bet;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

/**
 * Classe astratta che rappresenta un croupier specifico per il gioco del BlackJack.
 */
public abstract class BlackJackDealer extends Croupier<Integer> {

    public BlackJackDealer(Map<Player, Bet> roundBet) {
        super(roundBet);
    }

    // Metodo astratto specifico per BlackJack
    public abstract void dealInitialCards();

    public abstract void playDealerTurn();

    //Metodo per verificare la vincita (da impl)
    @Override
    public Map<Player, Double> checkBets(CounterResult<Integer> result) {
        //  Calcolo delle vincite 
        return null; // Placeholder
    }
}
