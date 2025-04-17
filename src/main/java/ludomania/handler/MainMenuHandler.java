package ludomania.handler;

/**
 * Interface for handling user interactions in the main menu.
 * <p>
 * Provides methods for responding to UI actions such as starting the game,
 * opening settings, or exiting the application.
 */
public interface MainMenuHandler {

    /**
     * Handles the action to start the game.
     */
    void handleStartGame();

    /**
     * Handles the action to open the settings screen.
     */
    void handleSettings();

    /**
     * Handles the action to exit the application.
     */
    void handleExit();

    /**
     * Handles the action to open the cosmetic screen.
     */
    void handleCosmetics();

    void selectGame(int gameId);
}