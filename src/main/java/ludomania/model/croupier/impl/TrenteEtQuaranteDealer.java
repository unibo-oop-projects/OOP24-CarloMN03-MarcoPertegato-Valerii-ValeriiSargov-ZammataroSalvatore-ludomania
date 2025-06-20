package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.game.impl.TrenteEtQuaranteResult;
import ludomania.model.player.api.Player;

/**
 * Dealer (Croupier) for the Trente et Quarante card game.
 * <p>
 * Extends {@link CardDealer} to manage the game flow, including deck drawing,
 * hand evaluation, and payout calculation for Trente et Quarante.
 * </p>
 * <p>
 * Handles two hands (Rouge and Noir), evaluates which one wins,
 * determines the type of win, and checks bets accordingly.
 * </p>
 */
public final class TrenteEtQuaranteDealer extends CardDealer<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {

    private static final String RED = "#f00";
    private static final String BLACK = "#000";
    private static final int MAX_HAND_VALUE = 31;
    private static final int FACE_CARDS_VALUE = 10;
    private final Map<Hand, Integer> hands = new HashMap<>();
    private final Hand rouge;
    private final Hand noir;

    /**
     * Constructs a new TrenteEtQuaranteDealer with given player bets and deck manager.
     *
     * @param roundBet the list of player-bet pairs for the round
     * @param decks    the deck factory to use for card drawing
     */
    public TrenteEtQuaranteDealer(final List<Pair<Player, Bet>> roundBet, final DeckFactory decks) {
        super(roundBet, decks);
        rouge = new Hand();
        noir = new Hand();
        hands.put(noir, 0);
        hands.put(rouge, 0);
    }

    @Override
    public Map<Player, Double> checkBets(final CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> result) {
        if (!(result instanceof TrenteEtQuaranteResult)) {
            throw new IllegalArgumentException("Invalid result type for TrenteEtQuaranteDealer");
        }
        final Map<Player, Double> winners = new HashMap<>();
        final TrenteEtQuaranteResult trqResult = (TrenteEtQuaranteResult) result;
        getRoundBet().forEach(pair -> {
            final Player player = pair.getKey();
            final Bet bet = pair.getValue();
            final BetType type = bet.getType();
            if (trqResult.getColor() == TrenteEtQuaranteBetType.DRAW || trqResult.getKind() == TrenteEtQuaranteBetType.DRAW) {
                winners.merge(player, bet.getValue(), Double::sum);
            } else {
                if (type == trqResult.getColor() || type == trqResult.getKind()) {
                    winners.merge(player, bet.evaluate(), Double::sum);
                }
            }
        });
        return winners;
    }

    /**
     * Resets the dealer state for a new round, clearing hands and round bets.
     */
    public void reset() {
        hands.clear();
        noir.getCards().clear();
        rouge.getCards().clear();
        hands.put(noir, 0);
        hands.put(rouge, 0);
        clearRound();
    }

    /**
     * @return the rouge (red) hand
     */
    public Hand getRouge() {
        return rouge;
    }

    /**
     * @return the noir (black) hand
     */
    public Hand getNoir() {
        return noir;
    }

    /**
     * Returns the current total value of a hand.
     *
     * @param hand the hand to evaluate
     * @return the hand's total value
     */
    public int getHandTotal(final Hand hand) {
        return hands.get(hand);
    }

    /**
     * Increases the value total of a hand.
     *
     * @param hand   the hand to update
     * @param amount the amount to add
     */
    public void increaseHandTotal(final Hand hand, final int amount) {
        hands.put(hand, hands.get(hand) + amount);
    }

    /**
     * Extracts a new card from the decks and adds it to the given hand.
     *
     * @param hand the hand receiving the new card
     * @return the card drawn
     */
    public Card extractNewCard(final Hand hand) {
        final Card extractedCard = drawCard();
        hand.addCard(extractedCard);
        increaseHandTotal(hand, trueCardValue(extractedCard));
        return extractedCard;
    }

    /**
     * Returns the effective value of a card (face cards are worth 10).
     *
     * @param card the card to evaluate
     * @return the numeric value for the game
     */
    public int trueCardValue(final Card card) {
        if (card.getRank().getValue() > FACE_CARDS_VALUE) {
            return FACE_CARDS_VALUE;
        }
        return card.getRank().getValue();
    }

    /**
     * Determines whether the hand has reached or exceeded the maximum value.
     *
     * @param hand the hand to evaluate
     * @return true if the hand value is 31 or more
     */
    public boolean isEnough(final Hand hand) {
        return getHandTotal(hand) >= MAX_HAND_VALUE;
    }

    /**
     * Evaluates which color hand (Rouge or Noir) wins the round.
     *
     * @return the winning color type, or DRAW if tied
     */
    private TrenteEtQuaranteBetType evaluateWinningColor() {
        final int rougeTotal = getHandTotal(rouge);
        final int noirTotal = getHandTotal(noir);
        if (rougeTotal == noirTotal) {
            return TrenteEtQuaranteBetType.DRAW;
        }
        if (rougeTotal < noirTotal) {
            return TrenteEtQuaranteBetType.ROUGE;
        }
        return TrenteEtQuaranteBetType.NOIR;
    }

    /**
     * Determines the type of win: Couleur or Enverse, based on the first card's color.
     *
     * @param winningColor the winning color type
     * @return the kind of win (Couleur, Enverse, or DRAW)
     */
    private TrenteEtQuaranteBetType evaluateWinningKind(final BetType winningColor) {
        if (winningColor == TrenteEtQuaranteBetType.DRAW) {
            return TrenteEtQuaranteBetType.DRAW;
        }
        final String firstCardColor = noir.getCards().getFirst().getSuit().getColor();
        if (winningColor == TrenteEtQuaranteBetType.ROUGE && RED.equals(firstCardColor) 
        || winningColor == TrenteEtQuaranteBetType.NOIR && BLACK.equals(firstCardColor)) {
            return TrenteEtQuaranteBetType.COULEUR;
        }
        return TrenteEtQuaranteBetType.ENVERSE;
    }

    /**
     * Declares the final result of the round by determining both the winning color and kind.
     *
     * @return the round result as a {@link TrenteEtQuaranteResult}
     */
    public TrenteEtQuaranteResult declareResult() {
        final TrenteEtQuaranteBetType color = evaluateWinningColor();
        final TrenteEtQuaranteBetType kind = evaluateWinningKind(color);
        return new TrenteEtQuaranteResult(new Pair<>(color, kind));
    }

}
