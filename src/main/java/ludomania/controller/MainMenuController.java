package ludomania.controller;

import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    private int selectedGameId;

    /**
     * Constructs a {@link MainMenuController} with the specified
     * {@link SceneManager} and {@link AudioManager}.
     *
     * @param sceneManager the {@link SceneManager} used for scene transitions
     * @param audioManager the {@link AudioManager} used to play sounds
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "References to languageManager and audiomanager are shared intentionally"
    )
    public MainMenuController(final SceneManager sceneManager, final AudioManager audioManager) {
        this.sceneManager = Objects.requireNonNull(sceneManager);
        this.audioManager = Objects.requireNonNull(audioManager);
        viewBuilder = new MainMenuViewBuilder(this, sceneManager.getLanguageManager(), sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleStartGame() {
        click();
        switch (selectedGameId) {
            case 1 -> sceneManager.switchToBlackJackMenu();
            case 2 -> handleRoulette();
            case 3 -> handleTrenteEtQuarante();
            default -> {
            }
        }
    }

    @Override
    public void handleSettings() {
        click();
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
        click();
        sceneManager.switchToCosmetics();
    }

    @Override
    public void handleTrenteEtQuarante() {
        click();
        sceneManager.switchToTrenteEtQuarante();
    }
    
    public void handleRoulette() {
        click();
        sceneManager.switchToRoulette();
    }
    private void click(){
        audioManager.playSound("click");
    }
}
