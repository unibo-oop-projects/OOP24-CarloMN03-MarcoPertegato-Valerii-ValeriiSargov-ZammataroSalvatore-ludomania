package ludomania.cosmetics;

import org.junit.jupiter.api.Test;

import ludomania.cosmetics.backgrounds.AmericanBackgroundTheme;
import ludomania.cosmetics.backgrounds.EuropeanBackgroundTheme;
import ludomania.cosmetics.backgrounds.NeonBackgroundTheme;
import ludomania.cosmetics.cards.AmericanCardTheme;
import ludomania.cosmetics.cards.EuropeanCardTheme;
import ludomania.cosmetics.cards.NeonCardTheme;
import ludomania.cosmetics.fiches.AmericanFicheTheme;
import ludomania.cosmetics.fiches.EuropeanFicheTheme;
import ludomania.cosmetics.fiches.NeonFicheTheme;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the CosmeticTheme enum.
 */
public class CosmeticThemeTest {

    @Test
    void testCreateCardTheme() {
        assertTrue(CosmeticTheme.EUROPEAN.createCardTheme() instanceof EuropeanCardTheme);
        assertTrue(CosmeticTheme.AMERICAN.createCardTheme() instanceof AmericanCardTheme);
        assertTrue(CosmeticTheme.NEON.createCardTheme() instanceof NeonCardTheme);
    }

    @Test
    void testCreateBackgroundTheme() {
        assertTrue(CosmeticTheme.EUROPEAN.createBackgroundTheme() instanceof EuropeanBackgroundTheme);
        assertTrue(CosmeticTheme.AMERICAN.createBackgroundTheme() instanceof AmericanBackgroundTheme);
        assertTrue(CosmeticTheme.NEON.createBackgroundTheme() instanceof NeonBackgroundTheme);
    }

    @Test
    void testCreateFicheTheme() {
        assertTrue(CosmeticTheme.EUROPEAN.createFicheTheme() instanceof EuropeanFicheTheme);
        assertTrue(CosmeticTheme.AMERICAN.createFicheTheme() instanceof AmericanFicheTheme);
        assertTrue(CosmeticTheme.NEON.createFicheTheme() instanceof NeonFicheTheme);
    }

    @Test
    void testFromIdWithValidId() {
        assertEquals(CosmeticTheme.EUROPEAN, CosmeticTheme.fromId("EUROPEAN"));
        assertEquals(CosmeticTheme.AMERICAN, CosmeticTheme.fromId("AMERICAN"));
        assertEquals(CosmeticTheme.NEON, CosmeticTheme.fromId("NEON"));
    }

    @Test
    void testFromIdWithInvalidId() {
        assertEquals(CosmeticTheme.EUROPEAN, CosmeticTheme.fromId("INVALID_ID"));
    }

    @Test
    void testFromIdWithNullId() {
        assertEquals(CosmeticTheme.EUROPEAN, CosmeticTheme.fromId(null));
    }
}