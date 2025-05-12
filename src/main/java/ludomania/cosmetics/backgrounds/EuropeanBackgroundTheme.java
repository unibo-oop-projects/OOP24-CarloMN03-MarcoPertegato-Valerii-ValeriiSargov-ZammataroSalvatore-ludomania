package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

/**
 * The {@code EuropeanBackgroundTheme} class implements the
 * {@link BackgroundTheme} interface,
 * providing a background color associated with the European cosmetic theme.
 */
public final class EuropeanBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#287233";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.EUROPEAN;
    }
}
