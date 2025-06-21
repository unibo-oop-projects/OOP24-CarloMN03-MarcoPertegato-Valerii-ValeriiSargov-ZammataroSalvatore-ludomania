package ludomania.model.player;

import ludomania.model.wallet.impl.WalletImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoulettePlayerTest {
    private static final Double MOCK_AMOUNT = 100.0;
    private RoulettePlayer mockPlayer;

    @BeforeEach
    void setUp() {
        final Double mockStartingBalance = 1000.0;
        final String mockUsername = "TestPlayer";
        this.mockPlayer = new RoulettePlayer(new WalletImpl(mockStartingBalance), mockUsername);
    }

    @Test
    void betAmount() {
        this.mockPlayer.addBetAmount(MOCK_AMOUNT);
        assertEquals(MOCK_AMOUNT, this.mockPlayer.getBetAmount());
    }

    @Test
    void addBetAmount() {
        this.mockPlayer.addBetAmount(MOCK_AMOUNT);
        assertEquals(MOCK_AMOUNT, this.mockPlayer.getBetAmount());
    }

    @Test
    void resetBetAmount() {
        this.mockPlayer.addBetAmount(MOCK_AMOUNT);
        this.mockPlayer.resetBetAmount();

        assertEquals(0.0, this.mockPlayer.getBetAmount());
    }

    @Test
    void restoreBalance() {
        final Double startingBalance = this.mockPlayer.getBalance();
        this.mockPlayer.addBetAmount(MOCK_AMOUNT);
        this.resetBetAmount();

        assertEquals(startingBalance, this.mockPlayer.getBalance());
    }
}
