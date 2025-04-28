package ludomania.settings.api;

import java.util.Locale;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import ludomania.cosmetics.CosmeticTheme;

/**
 * Interface for managing application settings such as locale, volume,
 * fullscreen mode, screen resolution, and cosmetic themes.
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
     * @return a {@link DoubleProperty} for the volume
     */
    DoubleProperty volumeProperty();

    /**
     * Returns the property indicating whether fullscreen mode is enabled.
     *
     * @return a {@link BooleanProperty} for the fullscreen state
     */
    BooleanProperty fullscreenProperty();

    /**
     * Returns the property representing the preferred screen width.
     *
     * @return an {@link IntegerProperty} for the resolution width
     */
    IntegerProperty resolutionWidthProperty();

    /**
     * Returns the property representing the preferred screen height.
     *
     * @return an {@link IntegerProperty} for the resolution height
     */
    IntegerProperty resolutionHeightProperty();

    /**
     * Returns the property representing the selected card theme.
     *
     * @return an {@link ObjectProperty} for the card {@link CosmeticTheme}
     */
    ObjectProperty<CosmeticTheme> cardThemeProperty();

    /**
     * Returns the property representing the selected fiche theme.
     *
     * @return an {@link ObjectProperty} for the fiche {@link CosmeticTheme}
     */
    ObjectProperty<CosmeticTheme> ficheThemeProperty();

    /**
     * Returns the property representing the selected background theme.
     *
     * @return an {@link ObjectProperty} for the background {@link CosmeticTheme}
     */
    ObjectProperty<CosmeticTheme> backgroundThemeProperty();
}
