package ludomania.handler;

import java.util.List;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

/**
 * 
 * Defines the interface for handling cosmetic menu actions and selections.
 * 
 * <p>
 * This interface provides methods for retrieving available themes for cards,
 * 
 * backgrounds, and fiches, and handles changes to the selected themes.
 * 
 * It also includes a method for navigating back to a previous menu or screen.
 */

public interface CosmeticMenuHandler {
    /**
     * Returns a list of available FicheThemes for the cosmetic menu.
     * 
     * @return the list of available FicheThemes
     */
    List<FicheTheme> getFicheThemes();

    /**
     * Returns a list of available CardThemes for the cosmetic menu.
     * 
     * @return the list of available CardThemes
     */
    List<CardTheme> getCardThemes();

    /**
     * Returns a list of available BackgroundThemes for the cosmetic menu.
     * 
     * @return the list of available BackgroundThemes
     */
    List<BackgroundTheme> getBackgroundThemes();

    /**
     * Handles a change in the selected FicheTheme.
     * 
     * @param theme the new FicheTheme to be applied
     */
    void handleFicheChange(FicheTheme theme);

    /**
     * Handles a change in the selected CardTheme.
     * 
     * @param theme the new CardTheme to be applied
     */
    void handleCardChange(CardTheme theme);

    /**
     * Handles a change in the selected BackgroundTheme.
     * 
     * @param theme the new BackgroundTheme to be applied
     */
    void handleBackgroundChange(BackgroundTheme theme);

    /**
     * Handles the action of going back to a previous menu or screen.
     */
    void handleBack();

    /**
     * Returns the currently selected CardTheme.
     * 
     * @return the selected CardTheme
     */
    CosmeticTheme getSelectedCardTheme();

    /**
     * Returns the currently selected FicheTheme.
     * 
     * @return the selected FicheTheme
     */
    CosmeticTheme getSelectedFicheTheme();

    /**
     * Returns the currently selected BackgroundTheme.
     * 
     * @return the selected BackgroundTheme
     */
    CosmeticTheme getSelectedBackgroundTheme();
}
