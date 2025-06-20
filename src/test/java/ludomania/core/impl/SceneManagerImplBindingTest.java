package ludomania.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
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

class SceneManagerImplBindingTest extends ApplicationTest {
    private static final double EXPECTED_VOLUME = 0.66;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final double VOLUME_TOLERANCE = 0.001;
    private static final int RESOLUTION_WIDTH_1 = 1280;
    private static final int RESOLUTION_HEIGHT_1 = 720;
    private static final int RESOLUTION_WIDTH_2 = 1920;
    private static final int RESOLUTION_HEIGHT_2 = 1080;
    private TestSettingsManager settingsManager;
    private TestAudioManager audioManager;
    private TestLanguageManager languageManager;
    private Stage stage;
    @BeforeEach
    @Override
    public void start(final Stage stage) {
        this.stage = stage;
        settingsManager = new TestSettingsManager();
        audioManager = new TestAudioManager();
        languageManager = new TestLanguageManager();
        stage.setScene(new Scene(new StackPane(), DEFAULT_WIDTH, DEFAULT_HEIGHT));
        stage.show();
        new SceneManagerImpl(stage, settingsManager, audioManager, languageManager, new TestImageProvider());
    }

    @Test
    void testVolumeBinding() {
        settingsManager.volumeProperty().set(EXPECTED_VOLUME);
        assertEquals(EXPECTED_VOLUME, audioManager.getMasterVolume(), VOLUME_TOLERANCE);
    }

    @Test
    void testLanguageBinding() {
        final Locale newLocale = Locale.FRENCH;
        settingsManager.currentLocaleProperty().set(newLocale);
        assertEquals(newLocale, languageManager.getLocale());
    }

    @Test
    void testResolutionBinding() {
        settingsManager.resolutionWidthProperty().set(RESOLUTION_WIDTH_1);
        settingsManager.resolutionHeightProperty().set(RESOLUTION_HEIGHT_1);
        assertEquals(RESOLUTION_WIDTH_1, stage.getWidth());
        assertEquals(RESOLUTION_HEIGHT_1, stage.getHeight());
        settingsManager.resolutionWidthProperty().set(RESOLUTION_WIDTH_2);
        settingsManager.resolutionHeightProperty().set(RESOLUTION_HEIGHT_2);
        assertEquals(RESOLUTION_WIDTH_2, stage.getWidth());
        assertEquals(RESOLUTION_HEIGHT_2, stage.getHeight());
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
        private final ObjectProperty<CosmeticTheme> 
            cardTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));
        private final ObjectProperty<CosmeticTheme> 
            ficheTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));
        private final ObjectProperty<CosmeticTheme> 
            backgroundTheme = new SimpleObjectProperty<>(CosmeticTheme.fromId("EUROPEAN"));

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
        public void setMasterVolume(final double volume) {
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
        public void loadSoundEffect(final String id, final String filePath) {
        }

        @Override
        public void loadBackgroundTrack(final String id, final String filePath) {
        }

        @Override
        public void playSound(final String id) {
        }

        @Override
        public void playMusic(final String id) {
        }

        @Override
        public void stopAllMusic() {
        }

		@Override
		public AudioManager copy() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'copy'");
		}
    }

    class TestLanguageManager implements LanguageManager {

        private Locale locale;

        @Override
        public void setLocale(final Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public StringBinding bind(final String key) {
            return new StringBinding() {
                @Override
                protected String computeValue() {
                    return "";
                }
            };
        }

        @Override
        public String getString(final String key) {
            return "ciao";
        }

        @Override
        public ObjectProperty<ResourceBundle> bundleProperty() {
            return new SimpleObjectProperty<>(ResourceBundle.getBundle("dummy"));
        }

		@Override
		public LanguageManager copy() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'copy'");
		}
    }

    class TestImageProvider implements ImageProvider {

        @Override
        public Color getBackgroundColor() {
            return Color.BLUE;
        }

        @Override
        public void setCardTheme(final CardTheme theme) {
        }

        @Override
        public void setBackgroundTheme(final BackgroundTheme theme) {
        }

        @Override
        public void setFicheTheme(final FicheTheme theme) {
        }

        @Override
        public void setBackgroundTheme(final CosmeticTheme theme) {
        }

        @Override
        public void setCardTheme(final CosmeticTheme theme) {
        }

        @Override
        public void setFicheTheme(final CosmeticTheme theme) {
        }

        @Override
        public Image getImage(final String id) {
            return new Image("data:image/png;base64"
            + ",iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wIAAgMBApU3JxkAAAAASUVORK5CYII=");
        }

        @Override
        public Region getSVGCard(final Rank rank, final Suit suit) {
            return new Region();
        }

        @Override
        public Region getSVGFiche(final Integer number) {
            return new Region();
        }

        @Override
        public Region svgHelperMethod(final String svg) {
            return new Region();
        }

		@Override
		public ImageProvider copy() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Unimplemented method 'copy'");
		}
    }
}
