package ludomania.model.bet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;

/**
 * Test class for {@link BlackJackBet} to ensure its correct functionality.
 * This class verifies the behavior of BlackJackBet instances, including
 * value retrieval, type checking, payout calculations, and bet evaluation
 * for both winning and losing scenarios.
 */
final class BlackJackBetTest {
    private static final double DELTA = 0.001; // For double comparisons
    private static final double FIVE = 5.0;
    private static final double TWENTY = 20.0;

    private Bet winningBet;
    private Bet losingBet;

    /**
     * Sets up the test environment before each test method.
     * Initializes a winning bet and a losing bet for consistent testing.
     * <p>
     * Subclasses that override this method should call {@code super.setUp()}
     * to ensure that the base bets ({@code winningBet} and {@code losingBet})
     * are properly initialized, unless they intend to completely redefine
     * these instances.
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Initialize bets for each test method
        winningBet = new BlackJackBet(10.0, BlackJackBetType.BASE);
        losingBet = new BlackJackBet(FIVE, BlackJackBetType.LOSE);
    }

    @Test
    void testGetValue() {
        assertEquals(10.0, winningBet.getValue(), DELTA);
        assertEquals(FIVE, losingBet.getValue(), DELTA);
    }

    @Test
    void testGetType() {
        assertEquals(BlackJackBetType.BASE, winningBet.getType());
        assertEquals(BlackJackBetType.LOSE, losingBet.getType());
    }

    @Test
    void testBetTypeGetPayout() {
        assertEquals(2.0, winningBet.getType().getPayout(), DELTA);
        assertEquals(0.0, losingBet.getType().getPayout(), DELTA);
    }

    @Test
    void testEvaluateWinningBet() {
        // A winning bet of 10.0 with a 2.0 payout should return 20.0
        assertEquals(TWENTY, winningBet.evaluate(), DELTA);
    }

    @Test
    void testEvaluateLosingBet() {
        // A losing bet should return 0.0
        assertEquals(0.0, losingBet.evaluate(), DELTA);
    }

    @Test
    void testEvaluateBetWithZeroValue() {
        final Bet zeroValueBet = new BlackJackBet(0.0, BlackJackBetType.BASE);
        assertEquals(0.0, zeroValueBet.evaluate(), DELTA);
    }
}

