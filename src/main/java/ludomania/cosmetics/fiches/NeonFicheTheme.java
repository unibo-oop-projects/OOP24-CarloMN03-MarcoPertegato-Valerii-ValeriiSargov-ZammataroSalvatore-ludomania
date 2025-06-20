package ludomania.cosmetics.fiches;

import java.util.Map;

import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheValue;

/**
 * The {@code NeonFicheTheme} class defines the color scheme for fiches using
 * a vibrant neon-style theme.
 * <p>
 * It sets specific background, text, and fiche colors to create a bright and
 * energetic
 * aesthetic typical of neon designs.
 * </p>
 * <p>
 * This class extends {@link SimpleFicheTheme} and overrides the methods needed
 * to
 * initialize the color mapping and define the theme type.
 * </p>
 *
 * @see SimpleFicheTheme
 */
public final class NeonFicheTheme extends SimpleFicheTheme {

    /**
     * Constructs a new {@code NeonFicheTheme} instance,
     * initializing the color scheme by calling {@link #initialize()}.
     */
    public NeonFicheTheme() {
        initialize();
    }

    @Override
    protected void initColori() {
        setBackgroundColor("#D04FE9");
        setTextColor("#000000");
        final Map<FicheValue, String> colori = getColori();
        colori.put(FicheValue.UNO, "#FFFFFF");
        colori.put(FicheValue.CINQUE, "#FF0000");
        colori.put(FicheValue.DIECI, "#2626FF");
        colori.put(FicheValue.VENTICINQUE, "#008000");
        colori.put(FicheValue.CENTO, "#808080");
        colori.put(FicheValue.CINQUECENTO, "#800080");
    }

    @Override
    protected CosmeticTheme getThemeType() {
        return CosmeticTheme.NEON;
    }
}
