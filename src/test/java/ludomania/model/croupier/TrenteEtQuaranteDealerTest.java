package ludomania.model.croupier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.game.impl.TrenteEtQuaranteResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;
import ludomania.model.wallet.impl.WalletImpl;

final class TrenteEtQuaranteDealerTest {

    private static final int DECK_NUM = 6;
    private static final double BET_VALUE = 20.0;
    private static final double INITIAL_MONEY = 100.0;
    private static final String RED = "#f00";

    private TrenteEtQuaranteDealer dealer;
    private TrenteEtQuarantePlayer player;
    private TrenteEtQuaranteBet bet;

    @BeforeEach
    void setUp() {
        player = new TrenteEtQuarantePlayer(new WalletImpl(INITIAL_MONEY), "Player1");
        bet = new TrenteEtQuaranteBet(BET_VALUE, TrenteEtQuaranteBetType.ROUGE);
        final List<Pair<Player, Bet>> roundBet = new LinkedList<>();
        roundBet.add(new Pair<>(player, bet));
        dealer = new TrenteEtQuaranteDealer(roundBet, new DeckFactory());
        dealer.initDeck(DECK_NUM);
    }

    @Test
    void testCheckBetsWithDraw() {
        final TrenteEtQuaranteResult result = new TrenteEtQuaranteResult(
            new Pair<>(TrenteEtQuaranteBetType.DRAW, TrenteEtQuaranteBetType.DRAW));
        final Map<Player, Double> winners = dealer.checkBets(result);
        assertTrue(winners.containsKey(player), "Player should be a winner in a draw");
        assertEquals(BET_VALUE, winners.get(player), "Bet evaluation should return the same amount as the one betted for DRAW");
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
    void testDeclareResult() {
        final Card extractedCard = dealer.extractNewCard(dealer.getNoir());
        assertEquals(dealer.trueCardValue(extractedCard), dealer.getHandTotal(dealer.getNoir()));
        assertEquals(0, dealer.getHandTotal(dealer.getRouge()));
        final TrenteEtQuaranteResult result = dealer.declareResult();
        assertEquals(TrenteEtQuaranteBetType.ROUGE, result.getColor());
        final String firstCardColor = dealer.getNoir().getCards().getFirst().getSuit().getColor();
        if (RED.equals(firstCardColor)) {
            assertEquals(TrenteEtQuaranteBetType.COULEUR, result.getKind());
        } else {
            assertEquals(TrenteEtQuaranteBetType.ENVERSE, result.getKind());
        }
    }
}
