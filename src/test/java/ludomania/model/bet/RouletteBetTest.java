package ludomania.model.bet;

import ludomania.model.Pair;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteWheel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RouletteBet Factory Methods Test")
class RouletteBetTest {
    private double mockAmount;

    @BeforeEach
    void setUp() {
        mockAmount = 10.0;
    }

    @Test
    void testImpairBet() {
        final RouletteBet bet = RouletteBetFactory.impairBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.IMPAIR, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(17, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(11, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(29, RouletteColor.NOIR), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(22, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(4, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.NOIR), bet.getChoice()));
    }

    @Test
    void testPairBet() {
        final RouletteBet bet = RouletteBetFactory.pairBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.PAIR, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(16, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(12, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(30, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(21, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(3, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }

    @Test
    void testPasseBet() {
        final RouletteBet bet = RouletteBetFactory.passeBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.PASSE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(19, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(22, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(36, RouletteColor.NOIR), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(1, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(18, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.NOIR), bet.getChoice()));
    }

    @Test
    void testManqueBet() {
        final RouletteBet bet = RouletteBetFactory.manqueBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.MANQUE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(1, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(11, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(18, RouletteColor.NOIR), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(19, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(36, RouletteColor.NOIR), bet.getChoice()));
    }

    @Test
    void testRougeBet() {
        final RouletteBet bet = RouletteBetFactory.rougeBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.ROUGE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(1, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(3, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(5, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(7, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(9, RouletteColor.ROUGE), bet.getChoice()));

        // False cases (Black or Green numbers)
        assertFalse(bet.success.apply(new Pair<>(2, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(4, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(6, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }

    @Test
    void testNoirBet() {
        final RouletteBet bet = RouletteBetFactory.noirBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.NOIR, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(2, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(4, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(6, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(35, RouletteColor.NOIR), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(3, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(5, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(9, RouletteColor.ROUGE), bet.getChoice()));
    }

    @Test
    void pleinBet() {
        final Set<Object> mockChoice = Set.of(23);
        final RouletteBet bet = RouletteBetFactory.pleinBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.PLEIN, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(23, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(25, RouletteColor.ROUGE), bet.getChoice()));
    }

    @Test
    void chevalBet() {
        final Set<Object> mockChoice = Set.of(25, 26);
        final RouletteBet bet = RouletteBetFactory.chevalBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.CHEVAL, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(25, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(26, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(17, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }

    @Test
    void carreBet() {
        final Set<Object> mockChoice = Set.of(25,26,29,28);
        final RouletteBet bet = RouletteBetFactory.carreBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.CARRE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(25, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(26, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(28, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(29, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(23, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(30, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(17, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.ROUGE), bet.getChoice()));
    }

    @Test
    void douzaineBet() {
        final Set<Object> mockChoice = RouletteWheel.firstDouzaine();
        final RouletteBet bet = RouletteBetFactory.douzaineBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.DOUZAINE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(1, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(5, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(12, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(17, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(13, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }

    @Test
    void colonneBet() {
        final Set<Object> mockChoice = RouletteWheel.SECOND_COLONNE;
        final RouletteBet bet = RouletteBetFactory.colonneBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.COLONNE, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(5, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(11, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(23, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(12, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(6, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }
}
