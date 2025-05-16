package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

/**
 * The {@code NeonBackgroundTheme} class implements the
 * {@link BackgroundTheme} interface,
 * providing a background color associated with the Neon cosmetic theme.
 */
public final class NeonBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#800000";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.NEON;
    }

}
