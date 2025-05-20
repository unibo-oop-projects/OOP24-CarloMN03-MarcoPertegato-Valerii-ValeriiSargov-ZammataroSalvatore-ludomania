package ludomania.core.api;

/**
 * Defines the interface for managing scene transitions within the application.
 * <p>
 * Implementations handle switching between major views (e.g., main menu,
 * settings)
 * and provide access to the {@link LanguageManager} for localization.
 */
public interface SceneManager {
    /**
     * Switches the current scene to the main menu.
     */
    void switchToMainMenu();

    /**
     * Switches the current scene to the settings screen.
     */
    void switchToSettings();

    /**
     * Switches the current scene to the TrenteEtQuarante screen.
     */
    void switchToTrenteEtQuarante();

    /**
     * Returns the {@link LanguageManager} associated with the scene manager,
     * used for localized UI updates.
     *
     * @return the language manager instance
     */
    LanguageManager getLanguageManager();

    /**
     * Retrieves the {@link ImageProvider} instance associated with this scene
     * manager.
     * It provides image resources like cards, backgrounds, and fiches for the
     * current scene.
     * 
     * @return the ImageProvider instance used by this scene manager
     */
    ImageProvider getImageProvider();

    /**
     * Switches the current scene to the cosmetics screen, where users can adjust
     * visual themes.
     * This method changes the visible view to the cosmetics screen.
     */
    void switchToCosmetics();
}
