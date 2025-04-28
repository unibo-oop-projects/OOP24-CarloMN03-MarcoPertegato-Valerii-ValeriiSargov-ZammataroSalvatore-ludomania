package ludomania.settings.impl;

import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.scene.Parent;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.settings.api.SettingsHandler;
import ludomania.settings.api.SettingsManager;

/**
 * Controller responsible for handling user interactions in the settings view.
 * <p>
 * Manages settings such as language, audio volume, fullscreen mode, and screen
 * resolution,
 * delegating persistence to the {@link SettingsManager}.
 * <p>
 * Also handles navigation actions and sound feedback via the
 * {@link AudioManager}.
 */

public final class SettingsController implements SettingsHandler, Controller {
    private static final double DEFAULT_AUDIO_VALUE = 0.8;
    private static final int DEFAULT_WIDTH_VALUE = 800;
    private static final int DEFAULT_HEIGHT_VALUE = 600;
    private final SettingsManager settingsManager;
    private final SettingsViewBuilder viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    /**
     * Constructs a SettingsController with the required managers.
     *
     * @param settingsManager the manager handling user preferences persistence
     * @param sceneManager    the manager handling scene navigation
     * @param audioManager    the manager handling audio feedback
     */

    public SettingsController(final SettingsManager settingsManager, final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.settingsManager = settingsManager;
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new SettingsViewBuilder(this, sceneManager.getLanguageManager());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void resetToDefaults() {
        audioManager.playSound("click");
        settingsManager.currentLocaleProperty().set(Locale.getDefault());
        settingsManager.volumeProperty().set(DEFAULT_AUDIO_VALUE);
        settingsManager.fullscreenProperty().set(false);
        settingsManager.resolutionWidthProperty().set(DEFAULT_WIDTH_VALUE);
        settingsManager.resolutionHeightProperty().set(DEFAULT_HEIGHT_VALUE);
    }

    @Override
    public LocaleStringConverter getLocaleStringConverter() {
        return new LocaleStringConverter();
    }

    @Override
    public void handleBack() {
        audioManager.playSound("click");
        sceneManager.switchToMainMenu();
    }

    @Override
    public Property<Locale> getCurrentLocaleProperty() {
        return settingsManager.currentLocaleProperty();
    }

    @Override
    public Property<Boolean> fullscreenProperty() {
        return settingsManager.fullscreenProperty();
    }

    @Override
    public DoubleProperty getVolumeProperty() {
        return settingsManager.volumeProperty();
    }

    @Override
    public void save() {
        audioManager.playSound("click");
        settingsManager.save();
    }

    @Override
    public void resolutionHandler(final String newVal) {
        if (newVal != null) {
            final String[] parts = newVal.split("x");
            settingsManager.resolutionWidthProperty().set(Integer.parseInt(parts[0]));
            settingsManager.resolutionHeightProperty().set(Integer.parseInt(parts[1]));
        }
    }
}