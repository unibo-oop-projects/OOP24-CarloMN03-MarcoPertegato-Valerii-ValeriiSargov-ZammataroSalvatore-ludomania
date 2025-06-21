package ludomania.model.croupier;

import ludomania.model.Pair;
import ludomania.model.bet.RouletteBet;
import ludomania.model.bet.RouletteBetFactory;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.RoulettePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Roulette Wheel Methods Test")
public class RouletteCroupierTest {
    private Integer _22 = 22;
    private CounterResult<Pair<Integer, RouletteColor>> mockResult22 =
            new CounterResult<>(new Pair<>(22, RouletteColor.NOIR));

    private Set<Object> mockChoice;
    private RoulettePlayer mockPlayer;
    private RouletteCroupier mockCroupier;
    private double mockAmount;

    @BeforeEach
    void setUp() {
        mockChoice = new HashSet<>(this._22);
        mockCroupier = new RouletteCroupier();
        mockAmount = 10.0;
    }

    @Test
    void checkBets() {
        final RouletteBet mockPleinBet = RouletteBetFactory.pleinBet(this.mockChoice, this.mockAmount);
        this.mockCroupier.addBet(this.mockPlayer, mockPleinBet);

        this.mockCroupier.checkBets(this.mockResult22);
        
    }

//    checkBets() {
//        final Set<Object> mockChoice = Set.of(23);
//        final RouletteBet bet = RouletteBetFactory.pleinBet(mockChoice, mockAmount);
//
//        assertNotNull(bet);
//        assertEquals(mockAmount, bet.getValue());
//        assertEquals(mockChoice, bet.getChoice());
//        assertEquals(RouletteBetType.PLEIN, bet.getType());
//
//        assertTrue(bet.success.apply(new Pair<>(23, RouletteColor.ROUGE), bet.getChoice()));
//
//        assertFalse(bet.success.apply(new Pair<>(25, RouletteColor.ROUGE), bet.getChoice()));
//    }
//
//    getRoundBet() {
//
//    }
//
//    clearRound() {
//
//    }
//
//    addBet() {
//
//    }
}
