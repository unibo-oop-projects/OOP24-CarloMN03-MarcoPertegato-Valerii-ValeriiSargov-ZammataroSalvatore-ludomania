package ludomania.core.api;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.paint.Color;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

/**
 * Interface for managing cosmetic themes in the application,
 * 
 * allowing customization of the background, cards, and fiches.
 * 
 * Provides methods to set either a complete cosmetic theme or specific theme
 * components.
 */

public interface CosmeticSet {

    /**
     * Sets the overall background, card, and fiche themes based on a CosmeticTheme.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setBackgroundTheme(CosmeticTheme theme);

    /**
     * Sets the card appearance based on a CosmeticTheme.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setCardTheme(CosmeticTheme theme);

    /**
     * Sets the fiche appearance based on a CosmeticTheme.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setFicheTheme(CosmeticTheme theme);

    /**
     * Sets a specific background theme.
     * 
     * @param theme the BackgroundTheme to apply
     */
    void setBackgroundTheme(BackgroundTheme theme);

    /**
     * Sets a specific card theme.
     * 
     * @param theme the CardTheme to apply
     */
    void setCardTheme(CardTheme theme);

    /**
     * Sets a specific fiche theme.
     * 
     * @param theme the FicheTheme to apply
     */
    void setFicheTheme(FicheTheme theme);

    /**
     * Retrieves the color used for the background based on the current theme.
     * 
     * @return the background color
     */
    Color getBackground();

    /**
     * Retrieves the image path or identifier for a card based on its rank and suit.
     * 
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @return the string representing the card image
     */
    String getCard(Rank rank, Suit suit);

    /**
     * Retrieves the image path or identifier for a fiche based on its number.
     * 
     * @param number the value of the fiche
     * @return the string representing the fiche image
     */
    String getFiche(Integer number);

    CosmeticSet copy();
}
