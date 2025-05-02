package ludomania.model.bet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;

public class TrenteEtQuaranteBetTest {

    private static final int ROUGE_AMOUNT = 100;
    private static final int ENVERSE_AMOUNT = 55;    
    
    private TrenteEtQuaranteBet rBet;
    private TrenteEtQuaranteBet cBet;

    @BeforeEach
    public void setUp(){
        rBet = new TrenteEtQuaranteBet(ROUGE_AMOUNT, TrenteEtQuaranteBetType.ROUGE);        
        cBet = new TrenteEtQuaranteBet(ENVERSE_AMOUNT, TrenteEtQuaranteBetType.ENVERSE);
    }

    @Test
    public void testGetters() {
        assertEquals(ROUGE_AMOUNT, rBet.getValue());
        assertEquals(TrenteEtQuaranteBetType.ROUGE, rBet.getType());
        assertEquals("Rouge", rBet.getType().getTypeName());

        assertEquals(ENVERSE_AMOUNT, cBet.getValue());        
        assertEquals(TrenteEtQuaranteBetType.ENVERSE, cBet.getType());
        assertEquals("Enverse", cBet.getType().getTypeName());
    }

    @Test
    public void testEvaluate() {
        assertEquals(ROUGE_AMOUNT * rBet.getType().getPayout(), rBet.evaluate());
        assertEquals(ENVERSE_AMOUNT * cBet.getType().getPayout(), cBet.evaluate());
    }
}