package ludomania.settings.api;

import java.util.Locale;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import ludomania.cosmetics.CosmeticTheme;

/**
 * Interface for managing application settings such as locale, volume,
 * fullscreen mode, and screen resolution.
 * <p>
 * Provides property access for UI binding and methods to persist settings.
 */
public interface SettingsManager {

    /**
     * Saves the current settings to persistent storage.
     */
    void save();

    /**
     * Returns the property representing the current application locale.
     *
     * @return an {@link ObjectProperty} containing the current {@link Locale}
     */
    ObjectProperty<Locale> currentLocaleProperty();

    /**
     * Returns the property representing the master volume level.
     *
     * @return a {@link DoubleProperty} for volume
     */
    DoubleProperty volumeProperty();

    /**
     * Returns the property indicating whether fullscreen mode is enabled.
     *
     * @return a {@link BooleanProperty} for fullscreen state
     */
    BooleanProperty fullscreenProperty();

    /**
     * Returns the property representing the preferred screen width.
     *
     * @return an {@link IntegerProperty} for resolution width
     */
    IntegerProperty resolutionWidthProperty();

    /**
     * Returns the property representing the preferred screen height.
     *
     * @return an {@link IntegerProperty} for resolution height
     */
    IntegerProperty resolutionHeightProperty();

    ObjectProperty<CosmeticTheme> cardThemeProperty();

    ObjectProperty<CosmeticTheme> ficheThemeProperty();

    ObjectProperty<CosmeticTheme> backgroundThemeProperty();
}