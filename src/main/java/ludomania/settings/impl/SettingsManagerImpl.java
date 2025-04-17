package ludomania.settings.impl;

import java.util.Locale;
import java.util.prefs.Preferences;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.settings.api.SettingsManager;

public final class SettingsManagerImpl implements SettingsManager {
    private static final String PREFS_NODE = "ludomania.settings";
    private static final double DEFAULT_AUDIO_VALUE = 0.8;
    private static final int DEFAULT_WIDTH_VALUE = 800;
    private static final int DEFAULT_HEIGHT_VALUE = 600;

    private final ObjectProperty<Locale> currentLocale = new SimpleObjectProperty<>();
    private final DoubleProperty volume = new SimpleDoubleProperty();
    private final BooleanProperty fullscreen = new SimpleBooleanProperty();
    private final IntegerProperty resolutionWidth = new SimpleIntegerProperty();
    private final IntegerProperty resolutionHeight = new SimpleIntegerProperty();
    private final ObjectProperty<CosmeticTheme> cardTheme = new SimpleObjectProperty<>();
    private final ObjectProperty<CosmeticTheme> ficheTheme = new SimpleObjectProperty<>();
    private final ObjectProperty<CosmeticTheme> backgroundTheme = new SimpleObjectProperty<>();

    public SettingsManagerImpl() {
        load();
    }

    private void load() {
        final Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
        final Locale savedLocale = Locale.forLanguageTag(
                prefs.get("locale", Locale.ITALIAN.toLanguageTag()));
        currentLocale.set(savedLocale);
        volume.set(prefs.getDouble("volume", DEFAULT_AUDIO_VALUE));
        fullscreen.set(prefs.getBoolean("fullscreen", false));
        resolutionWidth.set(prefs.getInt("resolutionWidth", DEFAULT_WIDTH_VALUE));
        resolutionHeight.set(prefs.getInt("resolutionHeight", DEFAULT_HEIGHT_VALUE));
        cardTheme.set(CosmeticTheme.fromId(prefs.get("cardThemeId", CosmeticTheme.EUROPEAN.name())));
        ficheTheme.set(CosmeticTheme.fromId(prefs.get("ficheThemeId", CosmeticTheme.EUROPEAN.name())));
        backgroundTheme.set(CosmeticTheme.fromId(prefs.get("backgroundThemeId", CosmeticTheme.EUROPEAN.name())));
    }

    @Override
    public void save() {
        final Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
        prefs.put("locale", currentLocale.get().toLanguageTag());
        prefs.putDouble("volume", volume.get());
        prefs.putBoolean("fullscreen", fullscreen.get());
        prefs.putInt("resolutionWidth", resolutionWidth.get());
        prefs.putInt("resolutionHeight", resolutionHeight.get());
        prefs.put("cardThemeId", cardTheme.getName());
        prefs.put("ficheThemeId", cardTheme.getName());
        prefs.put("backgroundThemeId", cardTheme.getName());
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
    public ObjectProperty<Locale> currentLocaleProperty() {
        return currentLocale;
    }

    @Override
    public DoubleProperty volumeProperty() {
        return volume;
    }

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
}