package ludomania.model.croupier;

import ludomania.model.Pair;
import ludomania.model.bet.RouletteBet;
import ludomania.model.bet.RouletteBetFactory;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.RoulettePlayer;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.impl.WalletImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Roulette Wheel Methods Test")
public class RouletteCroupierTest {
    private final Integer _22 = 22;
    private final CounterResult<Pair<Integer, RouletteColor>> mockResult22 =
            new CounterResult<>(new Pair<>(22, RouletteColor.NOIR));

    private Set<Object> mockChoice;
    private RoulettePlayer mockPlayer;
    private RouletteCroupier mockCroupier;
    private double mockAmount;

    @BeforeEach
    void setUp() {
        mockChoice = Set.of(this._22);
        mockCroupier = new RouletteCroupier();
        mockAmount = 10.0;
        this.mockPlayer = new RoulettePlayer(new WalletImpl(1000.0), "TestPlayer");
    }

    @Test
    void checkBets() {
        final RouletteBet mockPleinBet = RouletteBetFactory.pleinBet(this.mockChoice, this.mockAmount);
        this.mockCroupier.addBet(this.mockPlayer, mockPleinBet);

        final Map<Player, Double> winningBets = this.mockCroupier.checkBets(this.mockResult22);

        assertTrue(winningBets.containsKey(this.mockPlayer));
        assertEquals(mockPleinBet.evaluate(), winningBets.get(this.mockPlayer));
    }

    @Test
    void getRoundBet() {
        final RouletteBet mockPleinBet = RouletteBetFactory.pleinBet(this.mockChoice, this.mockAmount);
        this.mockCroupier.addBet(this.mockPlayer, mockPleinBet);

        assertEquals(1, this.mockCroupier.getRoundBet().size());
        assertEquals(1, this.mockCroupier.getRoundBet()
                        .stream()
                        .filter(b -> b.getKey().equals(mockPlayer) && b.getValue().equals(mockPleinBet))
                        .count());
    }

    @Test
    void addBet() {
        final RouletteBet mockPleinBet = RouletteBetFactory.pleinBet(this.mockChoice, this.mockAmount);
        this.mockCroupier.addBet(this.mockPlayer, mockPleinBet);

        assertFalse(this.mockCroupier.getRoundBet().isEmpty());
    }

    @Test
    void clearRound() {
        final RouletteBet mockPleinBet = RouletteBetFactory.pleinBet(this.mockChoice, this.mockAmount);
        this.mockCroupier.addBet(this.mockPlayer, mockPleinBet);

        this.mockCroupier.clearRound();
        assertTrue(this.mockCroupier.getRoundBet().isEmpty());
    }

}
