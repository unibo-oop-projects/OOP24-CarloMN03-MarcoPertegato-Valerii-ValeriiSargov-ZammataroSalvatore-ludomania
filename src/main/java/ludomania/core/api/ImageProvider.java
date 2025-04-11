package ludomania.core.api;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.Node;
import javafx.scene.image.Image;
import ludomania.cosmetics.CosmeticTheme;

public interface ImageProvider {

    void setBackgroundTheme(CosmeticTheme theme);

    void setCardTheme(CosmeticTheme theme);

    void setFicheTheme(CosmeticTheme theme);

    Image getImage(String id);

    Node getBackground();

    Node getCard(Rank rank, Suit suit);

    Node getFiche();

}