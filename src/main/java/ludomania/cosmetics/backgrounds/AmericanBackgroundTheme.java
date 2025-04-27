package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

public class AmericanBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#2596be";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.AMERICAN;
    }
}
