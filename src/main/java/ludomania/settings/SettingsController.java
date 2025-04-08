package ludomania.settings;

import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.scene.Parent;
import ludomania.core.AudioManager;
import ludomania.core.SceneManager;

public class SettingsController implements SettingsHandler {
    private final SettingsManager settingsManager;
    private final SettingsViewBuilder viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    public SettingsController(final SettingsManager settingsManager,final  SceneManager sceneManager,final  AudioManager audioManager) {
        this.settingsManager = settingsManager;
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new SettingsViewBuilder(this, sceneManager.getLanguageManager());
    }

    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public void resetToDefaults() {
        audioManager.playSound("click");
        settingsManager.currentLocaleProperty().set(Locale.getDefault());
        settingsManager.volumeProperty().set(0.8);
        settingsManager.fullscreenProperty().set(false);
        settingsManager.resolutionWidthProperty().set(800);
        settingsManager.resolutionHeightProperty().set(600);
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