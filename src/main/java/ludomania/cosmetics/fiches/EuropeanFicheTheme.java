package ludomania.cosmetics.fiches;

import java.util.Map;

import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheValue;

/**
 * The {@code EuropeanFicheTheme} class defines the color scheme for fiches
 * using
 * a European-style theme.
 * <p>
 * It sets specific background, text, and fiche colors to match a style
 * commonly associated with European casino fiches.
 * </p>
 * <p>
 * This class extends {@link SimpleFicheTheme} and overrides the methods needed
 * to
 * initialize the color mapping and define the theme type.
 * </p>
 *
 * @see SimpleFicheTheme
 */
public final class EuropeanFicheTheme extends SimpleFicheTheme {

    /**
     * Constructs a new {@code EuropeanFicheTheme} instance,
     * initializing the color scheme by calling {@link #initialize()}.
     */
    public EuropeanFicheTheme() {
        initialize();
    }

    @Override
    protected void initColori() {
        setBackgroundColor("#EFBF04");
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
        return CosmeticTheme.EUROPEAN;
    }
}
