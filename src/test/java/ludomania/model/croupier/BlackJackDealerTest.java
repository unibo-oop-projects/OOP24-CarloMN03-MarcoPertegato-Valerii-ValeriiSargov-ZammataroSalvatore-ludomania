package ludomania.model.croupier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.impl.BlackJackOutcome;
import ludomania.model.game.impl.BlackJackOutcomeResult;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.api.Wallet;
import ludomania.model.wallet.impl.WalletImpl;

/**
 * Unit tests for the {@link BlackJackDealer} class.
 * This class verifies the core functionality of the Blackjack dealer,
 * including card dealing, hand management, totals calculation,
 * and bet evaluation logic for various game outcomes.
 */
final class BlackJackDealerTest {

    private static final  double DELTA = 000.1;
    private static final int FIVE = 5;
    private static final int TWENTY = 20;
    private static final int PLAYER_TOT = 15;
    private static final int DEALER_TOT = 18;
    private static final double EXPECTED = 110.0;

    private Player player1;
    private Player player2;
    private BlackJackBet betP1;
    private BlackJackBet betP2;
    private BlackJackDealer dealer;

    // Common test cards with predefined values
    private final Card card5 = new Card(Rank.FIVE, Suit.CLUBS);
    private final Card card10 = new Card(Rank.TEN, Suit.HEARTS);

    /**
     * Sets up the test environment before each test method.
     * Initializes players, bets, and a new {@link TestBlackJackDealer} instance
     * to ensure a clean state for every test.
     */
    @BeforeEach
    void setUp() {
        final Wallet wallet = new WalletImpl(100.0);
        player1 = new BlackJackPlayer(wallet, "Alice");
        player2 = new BlackJackPlayer(wallet, "Bob");

        betP1 = new BlackJackBet(10.0, BlackJackBetType.BASE);
        betP2 = new BlackJackBet(TWENTY, BlackJackBetType.BASE);

        final List<Pair<Player, Bet>> initialRoundBets = new ArrayList<>();
        initialRoundBets.add(new Pair<>(player1, betP1));
        initialRoundBets.add(new Pair<>(player2, betP2));

        final DeckFactory decks = new DeckFactory();
        // Initialize dealer with no initial cards; cards will be set per test case
        dealer = new BlackJackDealer(initialRoundBets, decks);
    }

    /**
     * Tests the constructor and the {@code getBjRoundBet()} method.
     * Verifies that the dealer correctly stores the initial round bets provided
     * during construction.
     */
    @Test
    void testConstructorAndGetBjRoundBet() {
        assertNotNull(dealer.getBjRoundBet());
        assertEquals(2, dealer.getBjRoundBet().size());
        assertEquals(betP1, dealer.getBjRoundBet().get(player1));
        assertEquals(betP2, dealer.getBjRoundBet().get(player2));
    }

    /**
     * Tests the {@code reset()} method.
     * Verifies that player and dealer hands and their total values are correctly
     * reset to their initial empty states (empty hands, zero totals) after a round.
     */
    @Test
    void testReset() {
        // Simulate some state by increasing totals and adding cards
        dealer.increasePlayerTot(PLAYER_TOT);
        dealer.increaseDealerTot(DEALER_TOT);
        dealer.getPlayer().addCard(card10);
        dealer.getDealer().addCard(card5);

        // Assert that state is not initially zero/empty
        assertNotEquals(0, dealer.getPlayerTot());
        assertNotEquals(0, dealer.getDealerTot());
        assertFalse(dealer.getPlayer().getCards().isEmpty());
        assertFalse(dealer.getDealer().getCards().isEmpty());

        dealer.reset(); // Call the method under test

        // Assert that state has been reset
        assertEquals(0, dealer.getPlayerTot());
        assertEquals(0, dealer.getDealerTot());
        assertTrue(dealer.getPlayer().getCards().isEmpty());
        assertTrue(dealer.getDealer().getCards().isEmpty());
    }

    /**
     * Tests the {@code extractNewCard()} method.
     * Verifies that a new card is drawn from the controlled sequence,
     * added to the specified hand, and that the drawn card is correctly returned.
     */
    @Test
    void testExtractNewCard() {
        final Hand playerHand = new Hand();
        final Card drawnCard = dealer.extractNewCard(playerHand);

        assertNotNull(drawnCard);
        assertEquals(1, playerHand.getCards().size());
    }

    /**
     * Tests the {@code increaseDealerTot()} method.
     * Verifies that the dealer's total hand value is correctly increased by the specified amount.
     */
    @Test
    void testIncreaseDealerTot() {
        assertEquals(0, dealer.getDealerTot());
        dealer.increaseDealerTot(10);
        assertEquals(10, dealer.getDealerTot());
        dealer.increaseDealerTot(FIVE);
        assertEquals(10 + FIVE, dealer.getDealerTot());
    }

    /**
     * Tests the {@code increasePlayerTot()} method.
     * Verifies that a player's total hand value is correctly increased by the specified amount.
     */
    @Test
    void testIncreasePlayerTot() {
        assertEquals(0, dealer.getPlayerTot());
        dealer.increasePlayerTot(FIVE + 2);
        assertEquals(FIVE + 2, dealer.getPlayerTot());
        dealer.increasePlayerTot(FIVE + 3);
        assertEquals(PLAYER_TOT, dealer.getPlayerTot());
    }

    /**
     * Tests the {@code isEnough()} method.
     * Verifies that the method correctly determines if a hand total is
     * 17 or more, which is the threshold for the dealer to stop drawing cards.
     */
    @Test
    void testIsEnough() {
        assertFalse(dealer.isEnough(PLAYER_TOT + 1)); // Below threshold
        assertTrue(dealer.isEnough(DEALER_TOT - 1));  // At threshold
        assertTrue(dealer.isEnough(TWENTY));  // Above threshold
    }

    /**
     * Tests the {@code checkBets()} method for a winning outcome ({@code Outcome.WIN}).
     * Verifies that the winning player receives the correct payout (2.0x original bet)
     * and that their wallet is updated accordingly.
     */
    @Test
    void testCheckBetsWin() {
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        // Player1 wins, Player2 loses
        outcomes.put(player1, new BlackJackOutcomeResult(BlackJackOutcome.WIN, BlackJackBetType.BASE));
        outcomes.put(player2, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.LOSE));

        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = new BlackJackResult(outcomes);

        final double initialWalletP1 = player1.getBalance();

        final Map<Player, Double> winners = dealer.checkBets(result);

        assertEquals(1, winners.size()); // Only Player1 should be in the winners map
        assertTrue(winners.containsKey(player1));
        // Winning bet of 10.0 with BASE type (2.0 payout) should return 20.0
        assertEquals(betP1.evaluate(), winners.get(player1), DELTA);

        // Verify wallet update for the winner
        assertEquals(initialWalletP1 + betP1.evaluate(), player1.getBalance(), DELTA);
        assertEquals(player2.getBalance(), 100.0 + TWENTY); // Loser gets 0 deposit explicitly
    }

    /**
     * Tests the {@code checkBets()} method for a Blackjack outcome ({@code Outcome.BLACKJACK}).
     * Verifies that the player achieving Blackjack receives the higher payout (2.5x original bet)
     * and their wallet is updated accordingly.
     */
    @Test
    void testCheckBetsBlackjack() {
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player1, new BlackJackOutcomeResult(BlackJackOutcome.BLACKJACK, BlackJackBetType.BLACKJACK));
        outcomes.put(player2, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.LOSE));

        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = new BlackJackResult(outcomes);

        final double initialWalletP1 = player1.getBalance();
        final Map<Player, Double> winners = dealer.checkBets(result);

        assertEquals(1, winners.size());
        assertTrue(winners.containsKey(player1));
        // Blackjack payout is 2.5 * bet value (10.0 * 2.5 = 25.0)
        final double expectedBlackjackWin = betP1.getValue() * BlackJackBetType.BLACKJACK.getPayout();
        assertEquals(expectedBlackjackWin, winners.get(player1), DELTA);
        assertEquals(initialWalletP1 + expectedBlackjackWin, player1.getBalance(), DELTA);
        assertEquals(player1.getBalance(), 100.0 + TWENTY + FIVE);
    }

    /**
     * Tests the {@code checkBets()} method for a Push outcome ({@code Outcome.PUSH}).
     * Verifies that the player in a push scenario gets their original bet value back,
     * effectively resulting in no net change to their wallet from the bet.
     */
    @Test
    void testCheckBetsPush() {
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player1, new BlackJackOutcomeResult(BlackJackOutcome.PUSH, BlackJackBetType.PUSH));
        outcomes.put(player2, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.LOSE));

        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = new BlackJackResult(outcomes);

        final double initialWalletP1 = player1.getBalance();
        final Map<Player, Double> winners = dealer.checkBets(result);

        assertEquals(1, winners.size());
        assertTrue(winners.containsKey(player1));
        // Original bet returned for a push
        assertEquals(betP1.getValue(), winners.get(player1), DELTA);
        assertEquals(initialWalletP1 + betP1.getValue(), player1.getBalance(), DELTA);
        assertEquals(player1.getBalance(), EXPECTED); // Original bet amount
    }

    /**
     * Tests the {@code checkBets()} method for a Losing outcome ({@code Outcome.LOSE}).
     * Verifies that the losing player receives 0.0, indicating their bet is lost,
     * and their wallet remains unchanged (assuming the bet was withdrawn prior to checkBets).
     */
    @Test
    void testCheckBetsLose() {
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player1, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.LOSE));

        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = new BlackJackResult(outcomes);

        final double initialWalletP1 = player1.getBalance();
        final Map<Player, Double> winners = dealer.checkBets(result);

        assertTrue(winners.isEmpty(), "No winners expected for a losing outcome.");
        assertEquals(initialWalletP1, player1.getBalance(), DELTA); // Wallet unchanged, assuming bet was withdrawn earlier
        assertEquals(player1.getBalance(), 100.0); // Explicitly deposits 0 for a loss
    }

    /**
     * Tests that {@code checkBets()} correctly calls {@code clearRound()} at the end of its execution.
     * Note: This test indirectly verifies the call, as `clearRound()` is a superclass method.
     * The `bjRoundBet` map in `BlackJackDealer` is a local copy and is not expected to be cleared by this method.
     */
    @Test
    void testCheckBetsCallsClearRound() {
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player1, new BlackJackOutcomeResult(BlackJackOutcome.WIN, BlackJackBetType.BASE));
        final CounterResult<Map<Player, BlackJackOutcomeResult>> result = new BlackJackResult(outcomes);

        dealer.checkBets(result);

        // Verify that the bjRoundBet map, which is a local copy made in the constructor,
        // is NOT cleared by `checkBets`. The `clearRound()` in the superclass might
        // clear the super's internal round bets, but not this specific map.
        assertFalse(dealer.getBjRoundBet().isEmpty(),
            "bjRoundBet should not be empty after checkBets if it's a local copy and not cleared by clearRound().");
    }
}

