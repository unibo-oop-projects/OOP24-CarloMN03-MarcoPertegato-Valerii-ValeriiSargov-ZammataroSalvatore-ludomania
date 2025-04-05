package ludomania.settings;

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
import ludomania.core.LanguageManager;

public class SettingsManager {
    private static final String PREFS_NODE = "ludomania.settings";

    private final ObjectProperty<Locale> currentLocale = new SimpleObjectProperty<>();
    private final DoubleProperty volume = new SimpleDoubleProperty();
    private final BooleanProperty fullscreen = new SimpleBooleanProperty();
    private final IntegerProperty resolutionWidth = new SimpleIntegerProperty();
    private final IntegerProperty resolutionHeight = new SimpleIntegerProperty();

    public SettingsManager() {
        load();
    }

    public void load() {
        Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
        Locale savedLocale = Locale.forLanguageTag(
                prefs.get("locale", Locale.ITALIAN.toLanguageTag()));
        currentLocale.set(savedLocale);
        LanguageManager.setLocale(savedLocale);
        volume.set(prefs.getDouble("volume", 0.8));
        fullscreen.set(prefs.getBoolean("fullscreen", false));
        resolutionWidth.set(prefs.getInt("resolutionWidth", 800));
        resolutionHeight.set(prefs.getInt("resolutionHeight", 600));

    }

    public void save() {
        Preferences prefs = Preferences.userRoot().node(PREFS_NODE);
        prefs.put("locale", currentLocale.get().toLanguageTag());
        prefs.putDouble("volume", volume.get());
        prefs.putBoolean("fullscreen", fullscreen.get());
        prefs.putInt("resolutionWidth", resolutionWidth.get());
        prefs.putInt("resolutionHeight", resolutionHeight.get());
        System.out.println("Lingua salvata: " + currentLocale.get().toLanguageTag());
    }

    // Property getters
    public ObjectProperty<Locale> currentLocaleProperty() {
        return currentLocale;
    }

    public DoubleProperty volumeProperty() {
        return volume;
    }

    public BooleanProperty fullscreenProperty() {
        return fullscreen;
    }

    public IntegerProperty resolutionWidthProperty() {
        return resolutionWidth;
    }

    public IntegerProperty resolutionHeightProperty() {
        return resolutionHeight;
    }
}