package ludomania.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.impl.WalletImpl;

final class BlackJackPlayerTest {
    private static final double EXCESSIVE_AMOUNT = 150.0;
    private static final double BET_VALUE = 20.0;
    private static final double INITIAL_MONEY = 100.0;

    private WalletImpl wallet;
    private BlackJackPlayer player;

    @BeforeEach
    void setUp() {
        wallet = new WalletImpl(INITIAL_MONEY);
        player = new BlackJackPlayer(wallet, "Player1");
    }

    @Test
    void testMakeBetWithValidType() {
        final BetType betType = BlackJackBetType.BASE;
        final Bet bet = player.makeBet(BET_VALUE, betType);
        assertNotNull(bet);
        assertEquals(BET_VALUE, bet.getValue());
        assertEquals(betType, ((BlackJackBet) bet).getType());
        assertEquals(INITIAL_MONEY - BET_VALUE, wallet.getMoney());
    }

    @Test
    void testMakeBetWithInvalidType() {
        final BetType invalidType = new BetType() {
            @Override
            public String getTypeName() {
                return "Invalid";
            }

            @Override
            public Double getPayout() {
                return 2.0;
            }
        };

        final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(BET_VALUE, invalidType);
        });

        assertEquals("Invalid bet type for BlackJack", exception.getMessage());
        assertEquals(INITIAL_MONEY, wallet.getMoney());
    }

    @Test
    void testMakeBetWithInsufficientFunds() {
        final BetType betType = BlackJackBetType.BLACKJACK;

        final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(EXCESSIVE_AMOUNT, betType);
        });

        assertEquals("Not enough money to place a bet.", exception.getMessage());
        assertEquals(INITIAL_MONEY, wallet.getMoney()); // Saldo invariato
    }
}
