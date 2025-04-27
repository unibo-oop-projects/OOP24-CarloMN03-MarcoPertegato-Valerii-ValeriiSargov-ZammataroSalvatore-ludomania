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

public enum CosmeticTheme {
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

    public abstract CardTheme createCardTheme();

    public abstract BackgroundTheme createBackgroundTheme();

    public abstract FicheTheme createFicheTheme();

    public static CosmeticTheme fromId(String id) {
        try {
            return CosmeticTheme.valueOf(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            return CosmeticTheme.EUROPEAN;
        }
    }
}
