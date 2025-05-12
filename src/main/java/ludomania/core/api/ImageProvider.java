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

/**
 * Interface for providing images and SVG-based graphical elements for the
 * application.
 * 
 * Supports setting cosmetic themes and retrieving visual resources as JavaFX
 * nodes.
 */

public interface ImageProvider {
    /**
     * Sets the card theme to use for providing card images and SVGs.
     * 
     * @param theme the CardTheme to apply
     */
    void setCardTheme(CardTheme theme);

    /**
     * Sets the background theme to use for providing background color and related
     * images.
     * 
     * @param theme the BackgroundTheme to apply
     */
    void setBackgroundTheme(BackgroundTheme theme);

    /**
     * Sets the fiche theme to use for providing fiche images and SVGs.
     * 
     * @param theme the FicheTheme to apply
     */
    void setFicheTheme(FicheTheme theme);

    /**
     * Sets the overall cosmetic theme, applying its associated card, background,
     * and fiche themes.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setBackgroundTheme(CosmeticTheme theme);

    /**
     * Sets the overall cosmetic theme for card appearances.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setCardTheme(CosmeticTheme theme);

    /**
     * Sets the overall cosmetic theme for fiche appearances.
     * 
     * @param theme the CosmeticTheme to apply
     */
    void setFicheTheme(CosmeticTheme theme);

    /**
     * Retrieves a preloaded bitmap image based on its identifier.
     * 
     * @param id the unique identifier of the image
     * @return the corresponding Image object
     */
    Image getImage(String id);

    /**
     * Retrieves the background color associated with the current background theme.
     * 
     * @return the background Color
     */
    Color getBackgroundColor();

    /**
     * Retrieves a JavaFX Region representing an SVG rendering of a card, based on
     * its rank and suit.
     * 
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @return a Region containing the card's SVG rendering
     */
    Region getSVGCard(Rank rank, Suit suit);

    /**
     * Retrieves a JavaFX Region representing an SVG rendering of a fiche, based on
     * its value.
     * 
     * @param number the value of the fiche
     * @return a Region containing the fiche's SVG rendering
     */
    Region getSVGFiche(Integer number);

    /**
     * Helper method to convert a raw SVG string into a JavaFX Region node.
     * 
     * @param svg the raw SVG content
     * @return a Region representing the parsed SVG
     */
    Region svgHelperMethod(String svg);
}
