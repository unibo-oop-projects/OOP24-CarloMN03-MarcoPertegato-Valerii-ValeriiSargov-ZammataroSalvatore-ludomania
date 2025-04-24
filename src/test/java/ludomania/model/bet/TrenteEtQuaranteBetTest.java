package ludomania.model.bet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.impl.CouleurBet;
import ludomania.model.bet.impl.RougeBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetColor;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;

public class TrenteEtQuaranteBetTest {

    private static final int ROUGE_AMOUNT = 100;
    private static final int COULEUR_AMOUNT = 55;    
    
    private RougeBet rBet;
    private double rougePayout;

    private CouleurBet cBet;
    private double couleurPayout;

    @BeforeEach
    public void setUp(){
        rBet = new RougeBet(ROUGE_AMOUNT, TrenteEtQuaranteBetColor.ROUGE);
        rougePayout = rBet.getColor().getPayout();
        cBet = new CouleurBet(COULEUR_AMOUNT, TrenteEtQuaranteBetType.ENVERSE);
        couleurPayout = cBet.getType().getPayout();
    }

    @Test
    public void testGetValue() {
        assertEquals(ROUGE_AMOUNT, rBet.getValue());
        assertEquals(COULEUR_AMOUNT, cBet.getValue());
    }

    @Test
    public void testGetters() {
        assertEquals(TrenteEtQuaranteBetColor.ROUGE, rBet.getColor());
        assertEquals("Rouge", rBet.getColor().getDisplayName());
        assertEquals(TrenteEtQuaranteBetType.ENVERSE, cBet.getType());
        assertEquals("Enverse", cBet.getType().getDisplayName());
    }

    @Test
    public void testEvaluate() {
        assertEquals(ROUGE_AMOUNT * rougePayout, rBet.evaluate());
        assertEquals(COULEUR_AMOUNT * couleurPayout, cBet.evaluate());
    }
}