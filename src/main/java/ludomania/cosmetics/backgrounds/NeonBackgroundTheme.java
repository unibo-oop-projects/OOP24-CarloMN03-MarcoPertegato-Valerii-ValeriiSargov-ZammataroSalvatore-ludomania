package ludomania.cosmetics.backgrounds;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CosmeticTheme;

public class NeonBackgroundTheme implements BackgroundTheme {

    @Override
    public String getCosmetic() {
        return "#b300cb";
    }

    @Override
    public CosmeticTheme getTheme() {
        return CosmeticTheme.NEON;
    }

}
