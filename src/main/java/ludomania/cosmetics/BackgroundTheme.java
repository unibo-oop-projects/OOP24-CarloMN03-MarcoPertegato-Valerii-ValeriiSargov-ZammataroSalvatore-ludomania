package ludomania.cosmetics;

/**
 * 
 * Defines the interface for a background theme used in the cosmetic system.
 * 
 * <p>
 * Implementations represent the background theme and provide information about
 * 
 * the associated cosmetic style and theme type.
 * 
 */

public interface BackgroundTheme {
    /**
     * Returns the RGB value associated with this background theme.
     * 
     * @return the RGB value for the background color
     */
    String getCosmetic();

    /**
     * Returns the CosmeticTheme associated with this background theme.
     * 
     * @return the associated CosmeticTheme
     */
    CosmeticTheme getTheme();
}
