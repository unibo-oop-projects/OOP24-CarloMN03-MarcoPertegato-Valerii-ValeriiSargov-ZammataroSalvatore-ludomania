package ludomania.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;
import ludomania.model.wallet.impl.WalletImpl;

public class TrenteEtQuarantePlayerTest {
    private static final double EXCESSIVE_AMOUNT = 150.0;
    private static final double BET_VALUE = 20.0;
    private static final double INITIAL_MONEY = 100.0;

    private WalletImpl wallet;
    private TrenteEtQuarantePlayer player;

    @BeforeEach
    void setUp() {
        wallet = new WalletImpl(INITIAL_MONEY);
        player = new TrenteEtQuarantePlayer(wallet);
    }

    @Test
    void testMakeBetWithValidType() {
        BetType betType = TrenteEtQuaranteBetType.ROUGE;
        Bet bet = player.makeBet(BET_VALUE, betType);
        assertNotNull(bet);
        assertEquals(BET_VALUE, bet.getValue());
        assertEquals(betType, ((TrenteEtQuaranteBet) bet).getType());
        assertEquals(INITIAL_MONEY-BET_VALUE, wallet.getMoney());
    }

    @Test
    void testMakeBetWithInvalidType() {
        BetType invalidType = new BetType() {
            @Override
            public String getTypeName() {
                return "Invalid";
            }

            @Override
            public Double getPayout() {
                return 2.0;
            }
        };

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(BET_VALUE, invalidType);
        });

        assertEquals("Invalid bet type for Trente et Quarante.", exception.getMessage());
        assertEquals(INITIAL_MONEY, wallet.getMoney());
    }

    @Test
    void testMakeBetWithInsufficientFunds() {
        BetType betType = TrenteEtQuaranteBetType.NOIR;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            player.makeBet(EXCESSIVE_AMOUNT, betType);
        });

        assertEquals("Not enough money to place a bet.", exception.getMessage());
        assertEquals(INITIAL_MONEY, wallet.getMoney()); // Saldo invariato
    }
}
