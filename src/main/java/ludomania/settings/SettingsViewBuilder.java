package ludomania.settings;

import java.util.Locale;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ludomania.core.LanguageManager;

public class SettingsViewBuilder implements Builder<Region> {
    private final LanguageManager languageManager;
    private final SettingsHandler eventHandler;

    public SettingsViewBuilder(final SettingsHandler eventHandler, final LanguageManager languageManager) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
    }

    @Override
    public Region build() {
        final VBox results = new VBox(languageSelector(), volumeSlider(), fullscreenCheck(), resolutionSelector(),
                actionButton());
        results.setPadding(new Insets(15));
        results.getStyleClass().add("settings-container");
        return results;
    }

    VBox languageSelector() {
        final VBox results;
        final Label languageLabel = new Label();
        setText(languageLabel, "language_label");

        final ComboBox<Locale> languageCombo = new ComboBox<>();
        languageCombo.getItems().addAll(
                Locale.ITALIAN,
                Locale.ENGLISH);
        languageCombo.setConverter(eventHandler.getLocaleStringConverter());
        languageCombo.valueProperty().bindBidirectional(eventHandler.getCurrentLocaleProperty());
        results = new VBox(languageLabel, languageCombo);
        return results;
    }

    VBox volumeSlider() {
        final VBox results;
        final Label volumeLabel = new Label();
        setText(volumeLabel, "volume_label");
        final Slider volumeSlider = new Slider(0, 1, eventHandler.getVolumeProperty().getValue());
        volumeSlider.valueProperty().bindBidirectional(eventHandler.getVolumeProperty());
        volumeSlider.setShowTickLabels(true);
        results = new VBox(volumeLabel, volumeSlider);
        return results;
    }

    VBox fullscreenCheck() {
        final CheckBox fullscreenCheck = new CheckBox();
        setText(fullscreenCheck, "fullscreen_check");
        fullscreenCheck.selectedProperty().bindBidirectional(eventHandler.fullscreenProperty());
        return new VBox(fullscreenCheck);
    }

    VBox resolutionSelector() {
        final Label resolutionLabel = new Label();
        setText(resolutionLabel, "resolution_label");
        final ChoiceBox<String> resolutionChoice = new ChoiceBox<>();
        resolutionChoice.getItems().addAll("800x600", "1280x720", "1920x1080", "2560x1440");
        resolutionChoice.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    eventHandler.resolutionHandler(newVal);
                });
        return new VBox(resolutionLabel, resolutionChoice);
    }

    HBox actionButton() {
        final Button applyButton = new Button();
        setText(applyButton, "apply_button");
        applyButton.setOnAction(e -> eventHandler.save());

        final Button resetButton = new Button();
        setText(resetButton, "reset_button");
        resetButton.setOnAction(e -> eventHandler.resetToDefaults());
        final Button back = new Button();
        setText(back, "go_back_button");
        back.setOnMouseClicked(evt -> eventHandler.handleBack());
        final HBox buttonBox = new HBox(10, applyButton, resetButton, back);
        return new HBox(applyButton, resetButton, back, buttonBox);
    }

    void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
