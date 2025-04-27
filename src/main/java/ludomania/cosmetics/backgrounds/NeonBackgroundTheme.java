package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

public class NeonBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#800000";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.NEON;
    }

}
