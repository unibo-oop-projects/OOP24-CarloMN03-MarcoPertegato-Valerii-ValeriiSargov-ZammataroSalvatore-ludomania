package ludomania.settings.impl;

import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ludomania.core.api.LanguageManager;
import ludomania.settings.api.SettingsHandler;
import ludomania.view.ViewBuilder;

public final class SettingsViewBuilder implements ViewBuilder {
    private static final int TOP_RIGHT_BOTTOM_LEFT = 15;
    private static final int DEFAULT_SPACING = 10;

    private final LanguageManager languageManager;
    private final SettingsHandler eventHandler;

    public SettingsViewBuilder(final SettingsHandler eventHandler,
            final LanguageManager languageManager) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
    }

    @Override
    public Region build() {
        VBox container = new VBox(DEFAULT_SPACING,
                createLanguageSelector(),
                createVolumeSlider(),
                createFullscreenCheck(),
                createResolutionSelector(),
                createActionButtons());

        container.setPadding(new Insets(TOP_RIGHT_BOTTOM_LEFT));
        container.getStyleClass().add("settings-container");
        return container;
    }

    private VBox createLanguageSelector() {
        return createLabeledControl(
                "language_label",
                createLocaleComboBox());
    }

    private ComboBox<Locale> createLocaleComboBox() {
        ComboBox<Locale> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Locale.ITALIAN, Locale.ENGLISH);
        comboBox.setConverter(eventHandler.getLocaleStringConverter());
        comboBox.valueProperty().bindBidirectional(eventHandler.getCurrentLocaleProperty());
        return comboBox;
    }

    private VBox createVolumeSlider() {
        return createLabeledControl(
                "volume_label",
                createVolumeControl());
    }

    private Slider createVolumeControl() {
        Slider slider = new Slider(0, 1, eventHandler.getVolumeProperty().getValue());
        slider.setBlockIncrement(0.1);
        slider.valueProperty().bindBidirectional(eventHandler.getVolumeProperty());
        slider.setShowTickLabels(true);
        return slider;
    }

    private VBox createFullscreenCheck() {
        CheckBox checkBox = new CheckBox();
        setText(checkBox, "fullscreen_check");
        checkBox.selectedProperty().bindBidirectional(eventHandler.fullscreenProperty());
        return new VBox(checkBox);
    }

    private VBox createResolutionSelector() {
        return createLabeledControl(
                "resolution_label",
                createResolutionChoiceBox());
    }

    private ChoiceBox<String> createResolutionChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("800x600", "1280x720", "1920x1080", "2560x1440");
        choiceBox.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> eventHandler.resolutionHandler(newVal));
        return choiceBox;
    }

    private HBox createActionButtons() {
        return new HBox(DEFAULT_SPACING,
                createButton("apply_button", e -> eventHandler.save()),
                createButton("reset_button", e -> eventHandler.resetToDefaults()),
                createButton("go_back_button", e -> eventHandler.handleBack()));
    }

    private <T extends Control> VBox createLabeledControl(String labelKey, T control) {
        Label label = new Label();
        setText(label, labelKey);
        return new VBox(label, control);
    }

    private Button createButton(String textKey, EventHandler<ActionEvent> handler) {
        Button button = new Button();
        setText(button, textKey);
        button.setOnAction(handler);
        return button;
    }

    private void setText(Labeled target, String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}