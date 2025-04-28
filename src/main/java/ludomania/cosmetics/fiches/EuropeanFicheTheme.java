package ludomania.cosmetics.fiches;

import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheValue;

public class EuropeanFicheTheme extends SimpleFicheTheme {

    @Override
    protected void initColori() {
        backgroundColor = "#EFBF04";
        textColor = "#000000";

        colori.put(FicheValue.UNO, "#FFFFFF");
        colori.put(FicheValue.CINQUE, "#800000");
        colori.put(FicheValue.DIECI, "#000080");
        colori.put(FicheValue.VENTICINQUE, "#008000");
        colori.put(FicheValue.CINQUANTA, "#FFD700");
        colori.put(FicheValue.CENTO, "#808080");
        colori.put(FicheValue.CINQUECENTO, "#800080");
    }

    @Override
    protected CosmeticTheme getThemeType() {
        return CosmeticTheme.EUROPEAN;
    }
}
