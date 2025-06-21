package ludomania.model.game;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.DeckFactory;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;
import ludomania.model.wallet.impl.WalletImpl;
import ludomania.model.game.impl.TrenteEtQuaranteGame;

class TrenteEtQuaranteGameTest {

    private static final double INITIAL_MONEY = 1000.0;
    private static final int DECK_NUM = 6;
    private TrenteEtQuaranteGame game;

    @BeforeEach
    void setupTQGame() {
        final List<Pair<Player, Bet>> roundBet = new LinkedList<>();
        final TrenteEtQuaranteDealer dealer = new TrenteEtQuaranteDealer(roundBet, new DeckFactory());
        final WalletImpl wallet = new WalletImpl(INITIAL_MONEY);
        final List<TrenteEtQuarantePlayer> players = new LinkedList<>(List.of(new TrenteEtQuarantePlayer(wallet, "Player1")));
        this.game = new TrenteEtQuaranteGame(dealer, players, DECK_NUM);
    }

    @Test
    void testRunGameFillsHands() {
        assertEquals(0, game.getNoir().size());
        assertEquals(0, game.getRouge().size());

        game.runGame();

        assertTrue(game.getNoir().size() > 0, "Noir should contain some cards after runGame");
        assertTrue(game.getRouge().size() > 0, "Rouge should contain some cards after runGame");
    }
}
