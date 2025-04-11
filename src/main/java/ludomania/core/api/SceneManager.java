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
     * Returns the {@link LanguageManager} associated with the scene manager,
     * used for localized UI updates.
     *
     * @return the language manager instance
     */
    LanguageManager getLanguageManager();
    ImageManager getImageManager();

}