package ludomania.model.croupier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.util.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.game.impl.TrenteEtQuaranteResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;
import ludomania.model.wallet.impl.WalletImpl;

public final class TrenteEtQuaranteDealerTest {

    private static final int NOIR_VALUE = 15;
    private static final int ROUGE_VALUE = 10;
    private static final int DECK_NUM = 6;
    private static final double BET_VALUE = 20.0;
    private static final double INITIAL_MONEY = 100.0;

    private TrenteEtQuaranteDealer dealer;
    private TrenteEtQuarantePlayer player;
    private TrenteEtQuaranteBet bet;
    private WalletImpl wallet;
    private Map<Player, Bet> roundBet;
    private DeckFactory deckFactory;
    private Card card;

    @BeforeEach
    void setUp() {
        wallet = new WalletImpl(INITIAL_MONEY);
        player = new TrenteEtQuarantePlayer(wallet);
        bet = new TrenteEtQuaranteBet(BET_VALUE, TrenteEtQuaranteBetType.ROUGE);
        roundBet = new HashMap<Player, Bet>();
        roundBet.put(player, bet);
        deckFactory = new DeckFactory();
        dealer = new TrenteEtQuaranteDealer(roundBet, deckFactory);
        dealer.initDeck(DECK_NUM);
        card = new Card(Rank.ACE, Suit.SPADES);

    }

    @Test
    void testCheckBetsWithDraw() {
        final TrenteEtQuaranteResult result = new TrenteEtQuaranteResult(
            new Pair<>(TrenteEtQuaranteBetType.DRAW, TrenteEtQuaranteBetType.DRAW));
        final Map<Player, Double> winners = dealer.checkBets(result);
        assertTrue(winners.containsKey(player), "Player should be a winner in a draw");
        assertEquals(BET_VALUE, winners.get(player), "Bet evaluation should return the correct amount for DRAW");
    }

    @Test
    void testCheckBetsWithRougeAndCouleur() {
        final TrenteEtQuaranteResult result = new TrenteEtQuaranteResult(
            new Pair<>(TrenteEtQuaranteBetType.ROUGE, TrenteEtQuaranteBetType.COULEUR));
        final Map<Player, Double> winners = dealer.checkBets(result);
        assertTrue(winners.containsKey(player), "Player should be a winner on ROUGE");
        assertEquals(bet.evaluate(), winners.get(player), "Bet evaluation should return the correct amount for ROUGE");
    }

    @Test
    void testReset() {
        dealer.increaseRougeTotal(ROUGE_VALUE);
        dealer.increaseNoirTotal(NOIR_VALUE);
        assertEquals(ROUGE_VALUE, dealer.getRougeTotal());
        assertEquals(NOIR_VALUE, dealer.getNoirTotal());
        dealer.reset();
        assertEquals(0, dealer.getRougeTotal());
        assertEquals(0, dealer.getNoirTotal());
    }

    @Test
    void testDeclareResult() {
        dealer.getNoir().addCard(card);
        dealer.increaseNoirTotal(card.getRank().getValue());
        assertEquals(card.getRank().getValue(), dealer.getNoirTotal());
        assertEquals(0, dealer.getRougeTotal());
        final TrenteEtQuaranteResult result = dealer.declareResult();
        assertEquals(TrenteEtQuaranteBetType.ROUGE, result.getColor());
        assertEquals(TrenteEtQuaranteBetType.ENVERSE, result.getKind());
    }
}
