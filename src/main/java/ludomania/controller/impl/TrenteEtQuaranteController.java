package ludomania.controller.impl;

import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.TrenteEtQuaranteHandler;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.view.TrenteEtQuaranteViewBuilder;

public class TrenteEtQuaranteController implements Controller, TrenteEtQuaranteHandler {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    public TrenteEtQuaranteController(final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new TrenteEtQuaranteViewBuilder(this, sceneManager.getLanguageManager(),
        sceneManager.getImageProvider());
        
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleBetValueSelection(int value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBetValueSelection'");
    }

    @Override
    public void handleBetPlacement(TrenteEtQuaranteBetType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBetPlacement'");
    }

    @Override
    public void handleNextPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleNextPlayer'");
    }

    @Override
    public void handleNewRound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleNewRound'");
    }

    @Override
    public void handleReturnHome() {
        audioManager.playSound("click");
        sceneManager.switchToMainMenu();
    }
    
}
