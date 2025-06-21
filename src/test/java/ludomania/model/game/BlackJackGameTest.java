package ludomania.model.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.DeckFactory;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.impl.BlackJackGame;
import ludomania.model.game.impl.BlackJackOutcomeResult;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.impl.WalletImpl;

final class BlackJackGameTest {

    private static final double INITIAL_MONEY = 1000.0;
    private static final int DECK_NUM = 6;
    private BlackJackGame game;

    @BeforeEach
    void setupTQGame() {
        final List<Pair<Player, Bet>> roundBet = new LinkedList<>();
        final BlackJackDealer dealer = new BlackJackDealer(roundBet, new DeckFactory());
        dealer.initDeck(DECK_NUM);
        final WalletImpl wallet = new WalletImpl(INITIAL_MONEY);
        final BlackJackPlayer player = new BlackJackPlayer(wallet, "player1");
        this.game = new BlackJackGame(player);
    }

    @Test
    void testRunGameFillsHands() {
        game.startNewRound();
        game.placeBet(100.0);
        game.dealInitialCards();
        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = game.runGame();

        // Controlla che il giocatore abbia almeno due carte
        assertTrue(game.getPlayerTotalCards() >= 2, "Player should have at least 2 cards");

        // Controlla che il dealer abbia almeno due carte
        assertTrue(game.getDealerTotalCards() >= 2, "Dealer should have at least 2 cards");

        // Controlla che il risultato non sia null
        assertNotNull(result, "Result should not be null");
    }
}
