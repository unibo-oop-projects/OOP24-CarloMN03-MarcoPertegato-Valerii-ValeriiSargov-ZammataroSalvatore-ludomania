package ludomania.controller.impl;

import javafx.scene.Parent;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.BlackJackHandler;
import ludomania.view.blackjack.BlackJackViewBuilder;

public final class BlackJackController implements Controller, BlackJackHandler {

    private final SceneManager sceneManager;

    public BlackJackController(SceneManager sceneManager, AudioManager audioManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public Parent getView() {
        return new BlackJackViewBuilder(this).build();
    }

    @Override
    public void handleExit() {
        sceneManager.switchToMainMenu(); // Oppure Platform.exit() se vuoi chiudere tutto
    }

    @Override
    public void handleDrawCard(String playerId) {
        // Logica per pescare carta
    }

    @Override
    public void handleCancel(String playerId) {
        // Logica per annullare
    }

    @Override
    public void handleBet(int value, String playerId) {
        // Logica per piazzare scommessa
    }

    @Override
    public void handleStartRound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleStartRound'");
    }
}

