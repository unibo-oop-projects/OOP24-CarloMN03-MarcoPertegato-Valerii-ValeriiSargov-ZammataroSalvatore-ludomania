package ludomania.model.wallet.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WalletImplTest {

    private static final double MAX_DELTA = 0.001;
    private static final double INITIAL_AMOUNT = 100.0;
    private static final double DEPOSIT_AMOUNT = 50.0;
    private static final double WITHDRAW_AMOUNT = 30.0;
    private static final double OVERDRAW_AMOUNT = 200.0;
    private static final double EXPECTED_INITIAL_MONEY = 100.0;
    private static final double EXPECTED_MONEY_AFTER_DEPOSIT = 150.0;
    private static final double EXPECTED_MONEY_AFTER_WITHDRAW = 70.0;

    private WalletImpl wallet;

    @BeforeEach
    void setUp() {
        this.wallet = new WalletImpl(INITIAL_AMOUNT);
    }

    /**
     * Test if the Wallet initialization works correctly.
     */
    @Test
    void testInitialMoney() {
        assertEquals(EXPECTED_INITIAL_MONEY, wallet.getMoney(), MAX_DELTA);
    }

    /**
     * Test that the deposit works correctly.
     */
    @Test
    void testDepositIncreasesMoney() {
        wallet.deposit(DEPOSIT_AMOUNT);
        assertEquals(EXPECTED_MONEY_AFTER_DEPOSIT, wallet.getMoney(), MAX_DELTA);
    }

    /**
     * Check that the withdraw is performed correctly on Wallet.
     */
    @Test
    void testWithdraw() {
        assertFalse(wallet.withdraw(OVERDRAW_AMOUNT));
        assertEquals(EXPECTED_INITIAL_MONEY, wallet.getMoney(), MAX_DELTA);
        assertTrue(wallet.withdraw(WITHDRAW_AMOUNT));
        assertEquals(EXPECTED_MONEY_AFTER_WITHDRAW, wallet.getMoney(), MAX_DELTA);
    }

}
