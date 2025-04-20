package ludomania.controller.impl;

import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.CosmeticMenuHandler;
import ludomania.settings.api.SettingsManager;
import ludomania.view.CosmeticMenuViewBuilder;

public class CosmeticController implements Controller, CosmeticMenuHandler {
    private final SettingsManager settingsManager;
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    public CosmeticController(final SettingsManager settingsManager, final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.settingsManager = settingsManager;
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new CosmeticMenuViewBuilder(this, sceneManager.getLanguageManager(),
                sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

}
