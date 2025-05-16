package ludomania.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.controllers.LudomaniaDBController;
import database.controllers.api.DatabaseController;
import database.schemas.UserEntry;
import database.schemas.WalletEntry;
import database.schemas.api.Entry;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;

public class DatabaseTest {
    private static final String USERS_FILENAME = "users.json";
    private static final String WALLETS_FILENAME = "wallets.json";    
    
    private DatabaseController usersController;
    private DatabaseController walletsController;

    @BeforeEach
    public void setUp() {
        this.usersController = new LudomaniaDBController(new UserEntry("pippo", "ciaobello99"));
        this.walletsController = new LudomaniaDBController(new WalletEntry("pippo", 0.0));
    }

    @Test
    public void testUserRead() {
        Entry usr = this.usersController.read();
        System.out.println(usr);
        // assertEquals(ROUGE_AMOUNT, rBet.getValue());
        // assertEquals(TrenteEtQuaranteBetType.ROUGE, rBet.getType());
        // assertEquals("Rouge", rBet.getType().getTypeName());

        // assertEquals(ENVERSE_AMOUNT, cBet.getValue());        
        // assertEquals(TrenteEtQuaranteBetType.ENVERSE, cBet.getType());
        // assertEquals("Enverse", cBet.getType().getTypeName());
    }

    @Test
    public void testEvaluate() {
        // assertEquals(ROUGE_AMOUNT + (ROUGE_AMOUNT * rBet.getType().getPayout()), rBet.evaluate());
        // assertEquals(ENVERSE_AMOUNT + (ENVERSE_AMOUNT * cBet.getType().getPayout()), cBet.evaluate());
    }
}
