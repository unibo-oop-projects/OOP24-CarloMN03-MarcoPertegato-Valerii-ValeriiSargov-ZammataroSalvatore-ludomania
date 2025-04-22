package ludomania.core.api;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

public interface ImageProvider {
    void setCardTheme(CardTheme theme);

    void setBackgroundTheme(BackgroundTheme theme);

    void setFicheTheme(FicheTheme theme);

    void setBackgroundTheme(CosmeticTheme theme);

    void setCardTheme(CosmeticTheme theme);

    void setFicheTheme(CosmeticTheme theme);

    Image getImage(String id);

    Color getBackgroundColor();

    Region getSVGCard(Rank rank, Suit suit);

    Region getSVGFiche(Integer number);

    Region svgHelperMethod(String svg);
}