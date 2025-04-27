package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

public class EuropeanBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#287233";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.EUROPEAN;
    }
}
