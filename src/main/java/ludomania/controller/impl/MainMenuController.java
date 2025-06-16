package ludomania.controller.impl;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.MainMenuHandler;
import ludomania.view.MainMenuViewBuilder;

/**
 * Controller for managing the main menu of the application.
 * <p>
 * This controller handles user interactions with the main menu, such as
 * starting the game, opening settings,
 * handling cosmetics, and exiting the application. It communicates with the
 * {@link SceneManager} to switch scenes
 * and with the {@link AudioManager} to play sounds during menu interactions.
 * </p>
 */
public final class MainMenuController implements Controller, MainMenuHandler {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private int selectedGameId = 0;

    /**
     * Constructs a {@link MainMenuController} with the specified
     * {@link SceneManager} and {@link AudioManager}.
     *
     * @param sceneManager the {@link SceneManager} used for scene transitions
     * @param audioManager the {@link AudioManager} used to play sounds
     */
    public MainMenuController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new MainMenuViewBuilder(this, sceneManager.getLanguageManager(), sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleStartGame() {
        switch (selectedGameId) {
            case 1 -> sceneManager.switchToBlackJackMenu();
            case 2 -> handleExit();
            case 3 -> handleTrenteEtQuarante();
            default -> {
            }

        }
    }

    @Override
    public void handleSettings() {
        audioManager.playSound("click");
        sceneManager.switchToSettings();
    }

    @Override
    public void handleExit() {
        Platform.exit();
    }

    @Override
    public void selectGame(final int gameId) {
        this.selectedGameId = gameId;
    }

    @Override
    public void handleCosmetics() {
        audioManager.playSound("click");
        sceneManager.switchToCosmetics();
    }

    @Override
    public void handleTrenteEtQuarante() {
        audioManager.playSound("click");
        sceneManager.switchToTrenteEtQuarante();
    }    

}
