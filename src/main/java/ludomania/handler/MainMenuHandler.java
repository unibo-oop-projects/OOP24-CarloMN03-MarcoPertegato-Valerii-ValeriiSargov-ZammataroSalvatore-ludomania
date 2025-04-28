package ludomania.handler;

/**
 * Interface for handling user interactions in the main menu.
 * <p>
 * This interface defines the methods for responding to user actions in the main
 * menu,
 * such as starting a new game, opening the settings screen, exiting the
 * application,
 * or navigating to the cosmetic screen.
 * </p>
 */
public interface MainMenuHandler {

    /**
     * Handles the action to start the game.
     * <p>
     * This method should contain the logic to begin a new game, such as
     * initializing game data,
     * transitioning to the game scene, and any necessary setup for the gameplay.
     * </p>
     */
    void handleStartGame();

    /**
     * Handles the action to open the settings screen.
     * <p>
     * This method should trigger the transition to the settings scene where users
     * can adjust application settings
     * such as language, volume, resolution, and fullscreen mode.
     * </p>
     */
    void handleSettings();

    /**
     * Handles the action to exit the application.
     * <p>
     * This method should gracefully close the application, either by closing the
     * main window
     * or terminating the application process.
     * </p>
     */
    void handleExit();

    /**
     * Handles the action to open the cosmetic screen.
     * <p>
     * This method should trigger the transition to the cosmetics screen where users
     * can
     * change the game's cosmetic settings (e.g., themes, backgrounds, cards).
     * </p>
     */
    void handleCosmetics();

    /**
     * Selects an existing game based on the provided game ID.
     * <p>
     * This method should load and select a previously saved game using the provided
     * game ID.
     * Once selected, it should transition to the appropriate game scene.
     * </p>
     *
     * @param gameId the ID of the game to be selected
     */
    void selectGame(int gameId);
}