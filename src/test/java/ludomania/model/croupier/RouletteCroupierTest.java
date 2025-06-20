package ludomania.model.croupier;

import ludomania.model.Pair;
import ludomania.model.bet.RouletteBet;
import ludomania.model.bet.RouletteBetFactory;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.croupier.roulette.RouletteColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Roulette Wheel Methods Test")
public class RouletteCroupierTest {
    private double mockAmount;

    @BeforeEach
    void setUp() {
        mockAmount = 10.0;
    }

}
