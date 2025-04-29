package ludomania.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ludomania.core.api.AudioManager;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;
import ludomania.settings.api.SettingsManager;

class SceneManagerImplBindingTest extends ApplicationTest{

    private SceneManagerImpl sceneManager;
    private TestSettingsManager settingsManager;
    private TestAudioManager audioManager;
    private TestLanguageManager languageManager;
    private TestImageProvider imageProvider;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        settingsManager = new TestSettingsManager();
        audioManager = new TestAudioManager();
        languageManager = new TestLanguageManager();
        imageProvider = new TestImageProvider();

        stage.setScene(new Scene(new StackPane(), 800, 600));
        stage.show();

        sceneManager = new SceneManagerImpl(stage, settingsManager, audioManager, languageManager, imageProvider);
    }

    @Test
    void testVolumeBinding() {
        settingsManager.volumeProperty().set(0.66);
        assertEquals(0.66, audioManager.getMasterVolume(), 0.001);
    }

    @Test
    void testLanguageBinding() {
        Locale newLocale = Locale.FRENCH;
        settingsManager.currentLocaleProperty().set(newLocale);
        assertEquals(newLocale, languageManager.getLocale());
    }

    @Test
    void testResolutionBinding() {
        settingsManager.resolutionWidthProperty().set(1280);
        settingsManager.resolutionHeightProperty().set(720);
        assertEquals(1280, stage.getWidth());
        assertEquals(720, stage.getHeight());
        settingsManager.resolutionWidthProperty().set(1920);
        settingsManager.resolutionHeightProperty().set(1080);
        assertEquals(1920, stage.getWidth());
        assertEquals(1080, stage.getHeight());
    }

    class TestSettingsManager implements SettingsManager {

        private static final double DEFAULT_AUDIO_VALUE = 0.8;
        private static final int DEFAULT_WIDTH_VALUE = 800;
        private static final int DEFAULT_HEIGHT_VALUE = 600;

        private final ObjectProperty<Locale> currentLocale = new SimpleObjectProperty<>(Locale.ITALIAN);
        private final DoubleProperty volume = new SimpleDoubleProperty(DEFAULT_AUDIO_VALUE);
        private final BooleanProperty fullscreen = new SimpleBooleanProperty(true);
        private final IntegerProperty resolutionWidth = new SimpleIntegerProperty(DEFAULT_WIDTH_VALUE);
        private final IntegerProperty resolutionHeight = new SimpleIntegerProperty(DEFAULT_HEIGHT_VALUE);
        private final ObjectProperty<CosmeticTheme> cardTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));
        private final ObjectProperty<CosmeticTheme> ficheTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));
        private final ObjectProperty<CosmeticTheme> backgroundTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));

        @Override
        public BooleanProperty fullscreenProperty() {
            return fullscreen;
        }

        @Override
        public IntegerProperty resolutionWidthProperty() {
            return resolutionWidth;
        }

        @Override
        public IntegerProperty resolutionHeightProperty() {
            return resolutionHeight;
        }

        @Override
        public DoubleProperty volumeProperty() {
            return volume;
        }

        @Override
        public ObjectProperty<Locale> currentLocaleProperty() {
            return currentLocale;
        }

        @Override
        public ObjectProperty<CosmeticTheme> cardThemeProperty() {
            return cardTheme;
        }

        @Override
        public ObjectProperty<CosmeticTheme> ficheThemeProperty() {
            return ficheTheme;
        }

        @Override
        public ObjectProperty<CosmeticTheme> backgroundThemeProperty() {
            return backgroundTheme;
        }

        @Override
        public void save() {
        }
    }

    class TestAudioManager implements AudioManager {

        private double volume = 1.0;

        @Override
        public void setMasterVolume(double volume) {
            this.volume = volume;
        }

        @Override
        public double getMasterVolume() {
            return volume;
        }

        @Override
        public void initialize() {
        }

        @Override
        public void loadSoundEffect(String id, String filePath) {
        }

        @Override
        public void loadBackgroundTrack(String id, String filePath) {
        }

        @Override
        public void playSound(String id) {
        }

        @Override
        public void playMusic(String id) {
        }

        @Override
        public void stopAllMusic() {
        }
    }

    class TestLanguageManager implements LanguageManager {

        private Locale locale;

        @Override
        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public StringBinding bind(String key) {
            return new StringBinding() {
                @Override
                protected String computeValue() {
                    return "";
                }
            };
        }

        @Override
        public String getString(String key) {
            return "ciao";
        }

        @Override
        public ObjectProperty<ResourceBundle> bundleProperty() {
            return new SimpleObjectProperty<>(ResourceBundle.getBundle("dummy"));
        }
    }

    class TestImageProvider implements ImageProvider {

        @Override
        public Color getBackgroundColor() {
            return Color.BLUE;
        }

        @Override
        public void setCardTheme(CardTheme theme) {
        }

        @Override
        public void setBackgroundTheme(BackgroundTheme theme) {
        }

        @Override
        public void setFicheTheme(FicheTheme theme) {
        }

        @Override
        public void setBackgroundTheme(CosmeticTheme theme) {
        }

        @Override
        public void setCardTheme(CosmeticTheme theme) {
        }

        @Override
        public void setFicheTheme(CosmeticTheme theme) {
        }

        @Override
        public Image getImage(String id) {
            return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wIAAgMBApU3JxkAAAAASUVORK5CYII=");
        }

        @Override
        public Region getSVGCard(Rank rank, Suit suit) {
            return new Region();
        }

        @Override
        public Region getSVGFiche(Integer number) {
            return new Region();
        }

        @Override
        public Region svgHelperMethod(String svg) {
            return new Region();
        }
    }
}