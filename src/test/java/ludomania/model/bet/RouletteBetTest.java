package ludomania.model.bet;

import ludomania.model.Pair;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteWheel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RouletteBet Factory Methods Test")
class RouletteBetTest {
    private double mockAmount;

    @BeforeEach
    void setUp() {
        mockAmount = 10.0;
    }

    @Test
    @DisplayName("ImpairBet should create a correct bet and its predicate should work")
    void testImpairBet() {
        RouletteBet bet = RouletteBetFactory.ImpairBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.IMPAIR, bet.getType());

        // True cases (odd numbers)
        assertTrue(bet.success.apply(new Pair<>(17, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(11, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(29, RouletteColor.NOIR), bet.getChoice()));

        // False cases (even numbers or zero)
        assertFalse(bet.success.apply(new Pair<>(22, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(4, RouletteColor.NOIR), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.NOIR), bet.getChoice()));
    }

    @Test
    @DisplayName("ImpairBet should create a correct bet and its predicate should work")
    void testPairBet() {
        RouletteBet bet = RouletteBetFactory.PairBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.PAIR, bet.getType());

        // True cases (odd numbers)
        assertTrue(bet.success.apply(new Pair<>(16, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(12, RouletteColor.ROUGE), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(30, RouletteColor.ROUGE), bet.getChoice()));

        // False cases (even numbers or zero)
        assertFalse(bet.success.apply(new Pair<>(21, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(3, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
    }

    @Test
    @DisplayName("PasseBet should create a correct bet and its predicate should work")
    void testPasseBet() {
        RouletteBet bet = RouletteBetFactory.PasseBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.PASSE, bet.getType());

        BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> predicate = bet.success;

        assertTrue(predicate.apply(new Pair<>(19, RouletteColor.NOIR), Set.of()));
        assertTrue(predicate.apply(new Pair<>(22, RouletteColor.NOIR), Set.of()));
        assertTrue(predicate.apply(new Pair<>(36, RouletteColor.NOIR), Set.of()));

        assertFalse(predicate.apply(new Pair<>(1, RouletteColor.NOIR), Set.of()));
        assertFalse(predicate.apply(new Pair<>(18, RouletteColor.NOIR), Set.of()));
        assertFalse(predicate.apply(new Pair<>(0, RouletteColor.NOIR), Set.of()));
    }

    @Test
    @DisplayName("ManqueBet should create a correct bet and its predicate should work")
    void testManqueBet() {
        RouletteBet bet = RouletteBetFactory.ManqueBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.MANQUE, bet.getType());

        BiFunction<Pair<Integer, RouletteColor>, Set<Object>, Boolean> predicate = bet.success;

        assertTrue(predicate.apply(new Pair<>(1, RouletteColor.NOIR), Set.of()));
        assertTrue(predicate.apply(new Pair<>(11, RouletteColor.NOIR), Set.of()));
        assertTrue(predicate.apply(new Pair<>(18, RouletteColor.NOIR), Set.of()));

        assertFalse(predicate.apply(new Pair<>(19, RouletteColor.NOIR), Set.of()));
        assertFalse(predicate.apply(new Pair<>(0, RouletteColor.NOIR), Set.of()));
        assertFalse(predicate.apply(new Pair<>(36, RouletteColor.NOIR), Set.of()));
    }

    @Test
    @DisplayName("RougeBet should create a correct bet and its predicate should work")
    void testRougeBet() {
        RouletteBet bet = RouletteBetFactory.RougeBet(Set.of(), mockAmount);

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
    @DisplayName("NoirBet should create a correct bet and its predicate should work")
    void testNoirBet() {
        RouletteBet bet = RouletteBetFactory.NoirBet(Set.of(), mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(RouletteBetType.NOIR, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(2, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(4, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(6, RouletteColor.NOIR), bet.getChoice()));
        assertTrue(bet.success.apply(new Pair<>(35, RouletteColor.NOIR), bet.getChoice()));

        // False cases (Rouge or Green numbers)
        assertFalse(bet.success.apply(new Pair<>(0, RouletteColor.GREEN), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(3, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(5, RouletteColor.ROUGE), bet.getChoice()));
        assertFalse(bet.success.apply(new Pair<>(9, RouletteColor.ROUGE), bet.getChoice()));
    }

    @Test
    @DisplayName("")
    void PleinBet() {
        Set<Object> mockChoice = Set.of(23);
        RouletteBet bet = RouletteBetFactory.PleinBet(mockChoice, mockAmount);

        assertNotNull(bet);
        assertEquals(mockAmount, bet.getValue());
        assertEquals(mockChoice, bet.getChoice());
        assertEquals(RouletteBetType.PLEIN, bet.getType());

        assertTrue(bet.success.apply(new Pair<>(23, RouletteColor.ROUGE), bet.getChoice()));

        assertFalse(bet.success.apply(new Pair<>(25, RouletteColor.ROUGE), bet.getChoice()));
    }

    @Test
    @DisplayName("")
    void ChevalBet() {
        Set<Object> mockChoice = Set.of(25, 26);
        RouletteBet bet = RouletteBetFactory.ChevalBet(mockChoice, mockAmount);

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
    @DisplayName("")
    void CarreBet() {
        Set<Object> mockChoice = Set.of(25,26,29,28);
        RouletteBet bet = RouletteBetFactory.CarreBet(mockChoice, mockAmount);

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
    @DisplayName("")
    void DouzaineBet() {
        Set<Object> mockChoice = RouletteWheel.firstDouzaine();
        RouletteBet bet = RouletteBetFactory.DouzaineBet(mockChoice, mockAmount);

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
    @DisplayName("")
    void ColonneBet() {
        Set<Object> mockChoice = RouletteWheel.secondColonne();
        RouletteBet bet = RouletteBetFactory.ColonneBet(mockChoice, mockAmount);

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
