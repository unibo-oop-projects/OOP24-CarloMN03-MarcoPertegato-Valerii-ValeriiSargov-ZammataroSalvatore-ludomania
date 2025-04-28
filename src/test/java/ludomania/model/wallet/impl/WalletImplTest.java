/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package ludomania.model.wallet.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WalletImplTest {

    private static final double MAX_DELTA = 0.001;
    private static final double AMOUNT = 100.0;
    private static final double STARTING_AMOUNT = 100.0;
    private WalletImpl wallet;

    @BeforeEach
    void setUp() {
        this.wallet = new WalletImpl(STARTING_AMOUNT);
    }

    /**
     * Test if the Wallet initialization works correctly
     */
    @Test
    void testInitialMoney() {
        assertEquals(AMOUNT, wallet.getMoney(), MAX_DELTA);
    }

    /**
     * Test that the deposit work correctly
     */
    @Test
    void testDepositIncreasesMoney() {
        wallet.deposit(50.0);
        assertEquals(150.0, wallet.getMoney(), MAX_DELTA);

    }

    /**
     * Check that the withdraw is performed correctly on Wallet
     */
    @Test
    void testWithdraw() {
        assertFalse(wallet.withdraw(200.0));
        assertEquals(100.0, wallet.getMoney(), MAX_DELTA);
        assertTrue(wallet.withdraw(30.0));
        assertEquals(70.0, wallet.getMoney(), MAX_DELTA);
    }

    /**
     * Test for the withdraw method
     */
    @Test
    void testCanWithdrawTrue() {
        assertTrue(wallet.canWithdraw(50.0));
        assertFalse(wallet.canWithdraw(150.0));
    }

}