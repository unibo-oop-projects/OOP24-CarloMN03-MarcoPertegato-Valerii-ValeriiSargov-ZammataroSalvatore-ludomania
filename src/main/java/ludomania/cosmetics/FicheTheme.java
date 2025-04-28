package ludomania.cosmetics;

/**
 * Defines the interface for a fiche theme used in the cosmetic system.
 * 
 * <p>
 * Implementations represent the fiche theme and provide information about
 * 
 * the associated cosmetic style and theme type for specific fiche values.
 * 
 * Instead of a generic cosmetic identifier, it provides the SVG code for the
 * fiche's appearance.
 */

public interface FicheTheme {
    /**
     * Returns the SVG string associated with a specific fiche value.
     * The string contains the raw SVG markup to render the fiche visually.
     * 
     * @param value the value of the fiche
     * @return the SVG markup string for the fiche
     */
    String getCosmetic(Integer value);

    /**
     * Returns the CosmeticTheme associated with this fiche theme.
     * 
     * @return the associated CosmeticTheme
     */
    CosmeticTheme getTheme();
}
