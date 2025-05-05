package ludomania.model.croupier.impl;

import java.util.Map;

import io.lyuda.jcards.DeckFactory;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.player.api.Player;

public class BlackJackDealer<T> extends CardDealer<T> {

    public BlackJackDealer(Map<Player, Bet> roundBet, DeckFactory decks) {
        super(roundBet, decks);
    }

    @Override
    public Map<Player, Double> checkBets(CounterResult<T> result) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkBets'");
    }    
}