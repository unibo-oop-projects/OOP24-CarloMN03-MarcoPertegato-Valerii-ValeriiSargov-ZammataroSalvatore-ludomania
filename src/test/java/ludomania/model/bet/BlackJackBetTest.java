package ludomania.model.bet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ludomania.model.bet.impl.BlackJackBetType;

public class BlackJackBetTest {

    @Test
    void testBaseBetType() {
        assertEquals("Main", BlackJackBetType.BASE.getTypeName());
        assertEquals(1.0, BlackJackBetType.BASE.getPayout());
    }

    @Test
    void testRaddoppioBetType() {
        assertEquals("Raddoppio", BlackJackBetType.RADDOPPIO.getTypeName());
        assertEquals(2.0, BlackJackBetType.RADDOPPIO.getPayout());
    }

    @Test
    void testBlackJackBetType() {
        assertEquals("BlackJack", BlackJackBetType.BLACKJACK.getTypeName());
        assertEquals(1.5, BlackJackBetType.BLACKJACK.getPayout());
    }

    @Test
    void testAllEnumValuesExist() {
        BlackJackBetType[] values = BlackJackBetType.values();
        assertEquals(3, values.length);
        assertTrue(java.util.EnumSet.allOf(BlackJackBetType.class).contains(BlackJackBetType.BASE));
        assertTrue(java.util.EnumSet.allOf(BlackJackBetType.class).contains(BlackJackBetType.RADDOPPIO));
        assertTrue(java.util.EnumSet.allOf(BlackJackBetType.class).contains(BlackJackBetType.BLACKJACK));
    }
}
