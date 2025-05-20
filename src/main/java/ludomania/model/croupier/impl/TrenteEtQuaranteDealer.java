package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import javafx.util.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.impl.TrenteEtQuaranteResult;
import ludomania.model.player.api.Player;

public class TrenteEtQuaranteDealer extends CardDealer<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {

    private static final String RED = "#f00";
    private static final String BLACK = "#000";
    private static final int MAX_HAND_VALUE = 31;
    //private static final int FACE_CARDS_VALUE = 10;
    private Hand rouge;
    private Hand noir;
    private int rougeTotal;
    private int noirTotal;

    public TrenteEtQuaranteDealer(final Map<Player, Bet> roundBet, final DeckFactory decks) {
        super(roundBet, decks);
        this.rouge = new Hand();
        this.noir = new Hand();
        this.rougeTotal = 0;
        this.noirTotal = 0;
    }

    @Override
    public Map<Player, Double> checkBets(final CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> result) {
        if (!(result instanceof TrenteEtQuaranteResult)) {
            throw new IllegalArgumentException("Invalid result type for TrenteEtQuaranteDealer");
        }
        final Map<Player, Double> winners = new HashMap<Player, Double>();
        final TrenteEtQuaranteResult trqResult = (TrenteEtQuaranteResult) result;
        if (trqResult.getColor() == TrenteEtQuaranteBetType.DRAW || trqResult.getKind() == TrenteEtQuaranteBetType.DRAW) {
            getRoundBet().forEach((player, bet) -> {
                winners.put(player, bet.getValue());
            });
        } else {
            getRoundBet().forEach((player, bet) -> {
                final BetType type = bet.getType();
                if (type == trqResult.getColor() || type == trqResult.getKind()) {
                    winners.put(player, bet.evaluate());
                }
            });
        }        
        return winners;
    }

    public void reset() {
        rouge = new Hand();
        noir = new Hand();
        rougeTotal = 0;
        noirTotal = 0;
        clearRound();
    }

    public Hand getRouge() {
        return rouge;
    }

    public Hand getNoir() {
        return noir;
    }

    public int getRougeTotal() {
        return rougeTotal;
    }

    public int getNoirTotal() {
        return noirTotal;
    }

    public void increaseRougeTotal(final int amount) {
        rougeTotal += amount;
    }

    public void increaseNoirTotal(final int amount) {
        noirTotal += amount;
    }
    
    public Card extractNewCard(final Hand hand) {
        final Card extractedCard = drawCard();
        hand.addCard(extractedCard);        
        return extractedCard;
        //The CardValue is added from Game who knows which hand it is gonna get added
    }

    //Is gonna get moved inside Game
    /*
    private int tureCardValue(Card cards){
        if(cards.getRank().getValue()>FACE_CARDS_VALUE){
            return FACE_CARDS_VALUE;
        }
        return cards.getRank().getValue();
    }
    */
    public boolean isEnough(final int handTotal) {
        return handTotal >= MAX_HAND_VALUE;
    }

    private TrenteEtQuaranteBetType evaluateWinningColor() {
        if (rougeTotal == noirTotal) {
            return TrenteEtQuaranteBetType.DRAW;
        }        
        if (rougeTotal < noirTotal) {
            return TrenteEtQuaranteBetType.ROUGE;
        }    
        return TrenteEtQuaranteBetType.NOIR;
    }

    private TrenteEtQuaranteBetType evaluateWinningKind(BetType winningColor) {
        if (winningColor == TrenteEtQuaranteBetType.DRAW) {
            return TrenteEtQuaranteBetType.DRAW;
        }
        String firstCardColor = noir.getCards().getFirst().getSuit().getColor();
        if (winningColor == TrenteEtQuaranteBetType.ROUGE && firstCardColor.equals(RED) || winningColor == TrenteEtQuaranteBetType.NOIR && firstCardColor.equals(BLACK)) {
            return TrenteEtQuaranteBetType.COULEUR;
        }
        return TrenteEtQuaranteBetType.ENVERSE;
    }

    public TrenteEtQuaranteResult declareResult() {
        final TrenteEtQuaranteBetType color = evaluateWinningColor();
        final TrenteEtQuaranteBetType kind = evaluateWinningKind(color);
        return new TrenteEtQuaranteResult(new Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>(color, kind));
    }
}
