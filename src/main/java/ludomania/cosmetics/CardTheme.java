package ludomania.cosmetics;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;

/**
 * Defines the interface for a card theme used in the cosmetic system.
 *
 * <p>
 * Implementations represent the card theme and provide information about the
 * associated cosmetic style and theme type for specific card ranks and suits.
 */
public interface CardTheme {

    /**
     * Returns the SVG string associated with a specific card, based on its rank
     * and suit. The string contains the raw SVG markup to render the card
     * visually.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @return the SVG markup string for the card
     */
    String getCosmetic(Rank rank, Suit suit);

    /**
     * Returns the CosmeticTheme associated with this card theme.
     *
     * @return the associated CosmeticTheme
     */
    CosmeticTheme getTheme();
}
