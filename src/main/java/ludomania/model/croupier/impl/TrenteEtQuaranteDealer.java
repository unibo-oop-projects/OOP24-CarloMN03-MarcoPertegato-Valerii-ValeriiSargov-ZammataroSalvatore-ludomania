package ludomania.model.croupier.impl;

import java.util.Map;

import io.lyuda.jcards.DeckFactory;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public class TrenteEtQuaranteDealer<T> extends CardDealer<T> {

    public TrenteEtQuaranteDealer(Map<Player, Bet> roundBet, DeckFactory decks) {
        super(roundBet, decks);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Map<Player, Double> checkBets(CounterResult<T> result) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkBets'");
    }    
}