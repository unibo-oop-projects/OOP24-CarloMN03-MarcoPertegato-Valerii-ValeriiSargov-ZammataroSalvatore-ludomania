package ludomania.model.player;

import ludomania.model.wallet.impl.WalletImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoulettePlayerTest {
    private static final Double mockAmount = 100.0;
    private RoulettePlayer mockPlayer;

    @BeforeEach
    void setUp() {
        final Double mockStartingBalance = 1000.0;
        final String mockUsername = "TestPlayer";
        this.mockPlayer = new RoulettePlayer(new WalletImpl(mockStartingBalance), mockUsername);
    }

    @Test
    void betAmount() {
        this.mockPlayer.addBetAmount(this.mockAmount);
        assertEquals(this.mockAmount, this.mockPlayer.getBetAmount());
    }

    @Test
    void addBetAmount() {
        this.mockPlayer.addBetAmount(this.mockAmount);
        assertEquals(this.mockAmount, this.mockPlayer.getBetAmount());
    }

    @Test
    void resetBetAmount() {
        this.mockPlayer.addBetAmount(this.mockAmount);
        this.mockPlayer.resetBetAmount();

        assertEquals(0.0, this.mockPlayer.getBetAmount());
    }

    @Test
    void restoreBalance() {
        final Double startingBalance = this.mockPlayer.getBalance();
        this.mockPlayer.addBetAmount(this.mockAmount);
        this.resetBetAmount();

        assertEquals(startingBalance, this.mockPlayer.getBalance());
    }
}
