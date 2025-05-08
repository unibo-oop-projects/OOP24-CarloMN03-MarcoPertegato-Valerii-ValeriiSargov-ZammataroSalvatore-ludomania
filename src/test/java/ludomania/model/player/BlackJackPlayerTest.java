package ludomania.model.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.impl.WalletImpl;

public class BlackJackPlayerTest {

    private static final double INITIAL_MONEY = 50.0;
    private WalletImpl wallet;
    private BlackJackPlayer player;

    @BeforeEach
    void setUp() {
        wallet = new WalletImpl(INITIAL_MONEY);
        player = new BlackJackPlayer(wallet);
    }

    @Test
    void testValidBet() {
        Bet bet = player.makeBet(20.0, BlackJackBetType.BASE);
        assertEquals(20.0, bet.getValue(), "Bet value should match amount placed");
        assertEquals(30.0, wallet.getMoney(), "Wallet should reflect amount deducted");
    }

    @Test
    void testInvalidBetType() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(10.0, BlackJackBetType.BASE);
        });
    }

    @Test
    void testInsufficientFunds() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(100.0, BlackJackBetType.BASE);
        });
    }
}
