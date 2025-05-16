package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

/**
 * The {@code AmericanBackgroundTheme} class implements the
 * {@link BackgroundTheme} interface,
 * providing a background color associated with the American cosmetic theme.
 */
public final class AmericanBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#2596be";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.AMERICAN;
    }
}
