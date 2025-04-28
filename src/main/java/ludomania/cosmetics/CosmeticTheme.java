package ludomania.cosmetics;

import ludomania.cosmetics.backgrounds.AmericanBackgroundTheme;
import ludomania.cosmetics.backgrounds.EuropeanBackgroundTheme;
import ludomania.cosmetics.backgrounds.NeonBackgroundTheme;
import ludomania.cosmetics.cards.AmericanCardTheme;
import ludomania.cosmetics.cards.EuropeanCardTheme;
import ludomania.cosmetics.cards.NeonCardTheme;
import ludomania.cosmetics.fiches.AmericanFicheTheme;
import ludomania.cosmetics.fiches.EuropeanFicheTheme;
import ludomania.cosmetics.fiches.NeonFicheTheme;

/**
 * 
 * Enum that defines different cosmetic themes for the application.
 * 
 * Each theme includes specific styles for cards, backgrounds, and fiches.
 * 
 * <p>
 * The available themes are:
 * 
 * {@link #EUROPEAN}: Represents the European cosmetic theme.
 * 
 * {@link #AMERICAN}: Represents the American cosmetic theme.
 * 
 * {@link #NEON}: Represents the Neon cosmetic theme.
 * 
 * <p>
 * Each theme creates corresponding {@link CardTheme}, {@link BackgroundTheme},
 * 
 * and {@link FicheTheme} implementations when requested.
 */

public enum CosmeticTheme {
    /**
     * Represents the European cosmetic theme.
     */
    EUROPEAN {
        @Override
        public CardTheme createCardTheme() {
            return new EuropeanCardTheme();
        }

        @Override
        public BackgroundTheme createBackgroundTheme() {
            return new EuropeanBackgroundTheme();
        }

        @Override
        public FicheTheme createFicheTheme() {
            return new EuropeanFicheTheme();
        }
    },

    /**
     * Represents the American cosmetic theme.
     */
    AMERICAN {
        @Override
        public CardTheme createCardTheme() {
            return new AmericanCardTheme();
        }

        @Override
        public BackgroundTheme createBackgroundTheme() {
            return new AmericanBackgroundTheme();
        }

        @Override
        public FicheTheme createFicheTheme() {
            return new AmericanFicheTheme();
        }
    },

    /**
     * Represents the Neon cosmetic theme.
     */
    NEON {
        @Override
        public CardTheme createCardTheme() {
            return new NeonCardTheme();
        }

        @Override
        public BackgroundTheme createBackgroundTheme() {
            return new NeonBackgroundTheme();
        }

        @Override
        public FicheTheme createFicheTheme() {
            return new NeonFicheTheme();
        }
    };

    /**
     * Abstract method to create a {@link CardTheme} for the current cosmetic theme.
     * 
     * @return the CardTheme associated with this cosmetic theme
     */
    public abstract CardTheme createCardTheme();

    /**
     * Abstract method to create a {@link BackgroundTheme} for the current cosmetic
     * theme.
     * 
     * @return the BackgroundTheme associated with this cosmetic theme
     */
    public abstract BackgroundTheme createBackgroundTheme();

    /**
     * Abstract method to create a {@link FicheTheme} for the current cosmetic
     * theme.
     * 
     * @return the FicheTheme associated with this cosmetic theme
     */
    public abstract FicheTheme createFicheTheme();

    /**
     * Returns a {@link CosmeticTheme} based on the given string ID.
     * If the ID is invalid or null, the default theme is {@link #EUROPEAN}.
     * 
     * @param id the string ID of the cosmetic theme
     * @return the corresponding CosmeticTheme
     */
    public static CosmeticTheme fromId(final String id) {
        if (id == null) {
            return EUROPEAN;
        }
        try {
            return CosmeticTheme.valueOf(id);
        } catch (final IllegalArgumentException e) {
            return EUROPEAN;
        }
    }
}
