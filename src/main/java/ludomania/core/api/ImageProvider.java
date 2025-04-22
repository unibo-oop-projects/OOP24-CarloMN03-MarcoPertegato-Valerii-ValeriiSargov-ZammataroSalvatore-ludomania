package ludomania.core.api;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import ludomania.cosmetics.CosmeticTheme;

public interface ImageProvider {

    void setBackgroundTheme(CosmeticTheme theme);

    void setCardTheme(CosmeticTheme theme);

    void setFicheTheme(CosmeticTheme theme);

    Image getImage(String id);

    Color getBackgroundColor();

    Region getSVGCard(Rank rank, Suit suit);

    Region getSVGFiche(Integer number);

    Region svgHelperMethod(String svg);
}