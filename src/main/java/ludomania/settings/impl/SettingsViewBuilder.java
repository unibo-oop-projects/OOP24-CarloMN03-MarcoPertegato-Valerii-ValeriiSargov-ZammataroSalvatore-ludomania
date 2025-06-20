package ludomania.settings.impl;

import java.util.Locale;
import java.util.Objects;

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

/**
 * A builder class for creating the settings menu view.
 * <p>
 * Constructs a JavaFX layout where users can adjust settings such as
 * language, volume, fullscreen mode, and screen resolution.
 * <p>
 * The view is localized using the provided {@link LanguageManager} and
 * interacts with user preferences through the {@link SettingsHandler}.
 *
 * Implements the {@link ViewBuilder} interface.
 */

public final class SettingsViewBuilder implements ViewBuilder {
    private static final int TOP_RIGHT_BOTTOM_LEFT = 15;
    private static final int DEFAULT_SPACING = 10;
    private static final double VOLUME_TICK = 0.1;

    private final LanguageManager languageManager;
    private final SettingsHandler eventHandler;

    /**
     * Constructs a SettingsViewBuilder with the necessary dependencies.
     *
     * @param eventHandler    the handler for managing user settings and actions
     * @param languageManager the manager responsible for providing localized text
     */
    @SuppressWarnings("EI2")
    public SettingsViewBuilder(final SettingsHandler eventHandler,
            final LanguageManager languageManager) {
        this.eventHandler = Objects.requireNonNull(eventHandler);
        this.languageManager = Objects.requireNonNull(languageManager);
    }

    @Override
    public Region build() {
        final VBox container = new VBox(DEFAULT_SPACING,
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
        final ComboBox<Locale> comboBox = new ComboBox<>();
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
        final Slider slider = new Slider(0, 1, eventHandler.getVolumeProperty().getValue());
        slider.setBlockIncrement(VOLUME_TICK);
        slider.valueProperty().bindBidirectional(eventHandler.getVolumeProperty());
        slider.setShowTickLabels(true);
        return slider;
    }

    private VBox createFullscreenCheck() {
        final CheckBox checkBox = new CheckBox();
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
        final ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("1280x720", "1920x1080", "2560x1440");
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

    private <T extends Control> VBox createLabeledControl(final String labelKey, final T control) {
        final Label label = new Label();
        setText(label, labelKey);
        return new VBox(label, control);
    }

    private Button createButton(final String textKey, final EventHandler<ActionEvent> handler) {
        final Button button = new Button();
        setText(button, textKey);
        button.setOnAction(handler);
        return button;
    }

    private void setText(final Labeled target, final String property) {
        target.textProperty().bind(languageManager.bind(property));
    }
}
