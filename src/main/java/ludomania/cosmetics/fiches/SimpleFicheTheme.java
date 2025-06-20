package ludomania.cosmetics.fiches;

import java.util.EnumMap;
import java.util.Map;

import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;
import ludomania.cosmetics.FicheValue;

/**
 * An abstract implementation of the {@link FicheTheme} interface for creating
 * simple fiche themes.
 * <p>
 * This class defines common properties and methods for fiche themes, including
 * color mapping and SVG generation.
 * Subclasses must provide their own logic for initializing the color mapping
 * and returning the theme type.
 * </p>
 */

public abstract class SimpleFicheTheme implements FicheTheme {
    private final Map<FicheValue, String> colori = new EnumMap<>(FicheValue.class);
    private String backgroundColor;
    private String textColor;

    /**
     * Initializes the color mapping for the fiche values. This method must be
     * implemented
     * by subclasses to define the specific colors for each fiche value.
     * <p>
     * The actual initialization of colors will be handled by subclasses when
     * appropriate.
     * </p>
     */
    protected abstract void initColori();

    /**
     * Initializes the color mapping by calling {@link #initColori()}.
     * This method should be explicitly called after the constructor to ensure the
     * subclass is properly initialized.
     */
    public void initialize() {
        initColori();
    }

    /**
     * Gets the type of cosmetic theme for this fiche theme.
     * <p>
     * Subclasses must implement this method to return the correct theme type.
     * </p>
     *
     * @return the {@link CosmeticTheme} representing the theme type
     */
    protected abstract CosmeticTheme getThemeType();

    private String getFicheSVG(final String ficheColor, final int number) {
        return String.format(
                """
                        <svg version="1.2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100" height="100">
                            <style>
                                tspan { white-space:pre }
                                .s0 { fill: %s }
                                .s1 { fill: %s }
                                .t1 { font-size: 150px; fill: %s; font-weight: 400; font-family: "DejaVuSans", "DejaVu Sans" }
                            </style>
                            <path id="Background" fill-rule="evenodd" class="s0"
                            d="m300 600c-165.9 0-300-134.1-300-300 0-165.9 134.1-300 300-300
                            165.9 0 300 134.1 300 300 0 165.9-134.1 300-300 300z"/>
                            <path id="Forma 1" fill-rule="evenodd" class="s1"
                            d="m300.5 517c-124.7 0-225.5-97-225.5-217 0-120 100.8-217 225.5-217
                            124.7 0 225.5 97 225.5 217 0 120-100.8 217-225.5 217z"/>
                            <text id="Testo" style="transform: matrix(1,0,0,1,261,290)">
                                <tspan x="50%%" y="55%%" text-anchor="middle" dominant-baseline="middle" class="t1">
                                    %s
                                </tspan>
                            </text>
                        </svg>""",
                backgroundColor, ficheColor, textColor, number);
    }

    /**
     * Gets the cosmetic representation for the fiche with the specified value.
     * <p>
     * This method uses the color mapping and generates the corresponding SVG for
     * the fiche value.
     * </p>
     *
     * @param value the fiche value (e.g., the number on the fiche)
     * @return the SVG representation of the fiche
     */
    @Override
    public String getCosmetic(final Integer value) {
        final FicheValue fiche = FicheValue.fromValue(value);
        final String ficheColor = colori.get(fiche);
        return getFicheSVG(ficheColor, value);
    }

    /**
     * Gets the theme associated with this fiche theme.
     * <p>
     * This method returns the theme type for this fiche, as determined by the
     * implementing subclass.
     * </p>
     *
     * @return the {@link CosmeticTheme} for this fiche theme
     */
    @Override
    public CosmeticTheme getTheme() {
        return getThemeType();
    }

    /**
     * Gets the background color for this fiche theme.
     *
     * @return the background color as a string (e.g., "#FFFFFF" for white)
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color for this fiche theme.
     *
     * @param backgroundColor the background color to set (e.g., "#FFFFFF" for
     *                        white)
     */
    public void setBackgroundColor(final String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets the text color for the fiche value in this theme.
     *
     * @return the text color as a string (e.g., "#000000" for black)
     */
    public String getTextColor() {
        return textColor;
    }

    /**
     * Sets the text color for the fiche value in this theme.
     *
     * @param textColor the text color to set (e.g., "#000000" for black)
     */
    public void setTextColor(final String textColor) {
        this.textColor = textColor;
    }

    /**
     * Gets the color mapping for the fiche values.
     * <p>
     * This map stores the color associated with each fiche value, and is used
     * to generate the correct cosmetic appearance for each fiche.
     * </p>
     *
     * @return the map of fiche values to their associated colors
     */
    public Map<FicheValue, String> getColori() {
        return colori;
    }
}
