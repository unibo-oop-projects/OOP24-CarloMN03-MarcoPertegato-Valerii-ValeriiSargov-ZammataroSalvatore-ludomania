package ludomania.settings;

import java.util.Locale;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import ludomania.core.LanguageManager;
import ludomania.core.SceneManager;

public class SettingsController {
    private final SettingsManager settingsManager;
    private final VBox view;
    SceneManager sceneManager;

    public SettingsController(SettingsManager settingsManager, SceneManager sceneManager) {
        this.settingsManager = settingsManager;
        this.sceneManager = sceneManager;
        this.view = buildView();
        LanguageManager.initialize(settingsManager.currentLocaleProperty().get());
    }

    public VBox getView() {
        return view;
    }

    private VBox buildView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getStyleClass().add("settings-container");

        // 1. Sezione Lingua
        Label languageLabel = new Label("Lingua:");
        ComboBox<Locale> languageCombo = new ComboBox<>();
        languageCombo.getItems().addAll(
                Locale.ITALIAN,
                Locale.ENGLISH);
        languageCombo.setConverter(new LocaleStringConverter());
        languageCombo.valueProperty().bindBidirectional(settingsManager.currentLocaleProperty());
        settingsManager.currentLocaleProperty().addListener((obs, oldLocale, newLocale) -> {
            if (newLocale != null) {
                LanguageManager.setLocale(newLocale);
            }
        });
        // 2. Sezione Volume
        Label volumeLabel = new Label("Volume:");
        Slider volumeSlider = new Slider(0, 1, settingsManager.volumeProperty().get());
        volumeSlider.valueProperty().bindBidirectional(settingsManager.volumeProperty());
        volumeSlider.setShowTickLabels(true);

        // 3. Sezione Schermo Intero
        CheckBox fullscreenCheck = new CheckBox("Schermo intero");
        fullscreenCheck.selectedProperty().bindBidirectional(settingsManager.fullscreenProperty());

        // 4. Sezione Risoluzione
        Label resolutionLabel = new Label("Risoluzione:");
        ChoiceBox<String> resolutionChoice = new ChoiceBox<>();
        resolutionChoice.getItems().addAll("800x600", "1280x720", "1920x1080", "2560x1440");
        resolutionChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String[] parts = newVal.split("x");
                settingsManager.resolutionWidthProperty().set(Integer.parseInt(parts[0]));
                settingsManager.resolutionHeightProperty().set(Integer.parseInt(parts[1]));
            }
        });

        // 5. Pulsanti
        Button applyButton = new Button("Applica");
        applyButton.setOnAction(e -> settingsManager.save());

        Button resetButton = new Button("Ripristina");
        resetButton.setOnAction(e -> resetToDefaults());
        Button back = new Button("TORNA INDIETRO");
        back.setOnMouseClicked(evt -> {
            sceneManager.switchToMainMenu();
        });
        HBox buttonBox = new HBox(10, applyButton, resetButton, back);

        // Costruzione layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, languageLabel, languageCombo);
        grid.addRow(1, volumeLabel, volumeSlider);
        grid.addRow(2, new Label(), fullscreenCheck);
        grid.addRow(3, resolutionLabel, resolutionChoice);

        root.getChildren().addAll(
                new Label("IMPOSTAZIONI"),
                grid,
                buttonBox);

        return root;
    }

    private void resetToDefaults() {
        settingsManager.currentLocaleProperty().set(Locale.getDefault());
        settingsManager.volumeProperty().set(0.8);
        settingsManager.fullscreenProperty().set(false);
        settingsManager.resolutionWidthProperty().set(800);
        settingsManager.resolutionHeightProperty().set(600);
    }

    private static class LocaleStringConverter extends StringConverter<Locale> {
        @Override
        public String toString(Locale locale) {
            return locale.getDisplayName(locale);
        }

        @Override
        public Locale fromString(String string) {
            return Locale.forLanguageTag(string);
        }
    }
}