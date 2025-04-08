package ludomania.settings.api;

import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.util.StringConverter;

/**
 * Interface for handling user interactions and data binding in the settings
 * screen.
 * <p>
 * Provides methods for saving and resetting settings, updating resolution,
 * and exposing properties for data binding in the UI.
 */
public interface SettingsHandler {

    /**
     * Handles navigation back to the previous screen or main menu.
     */
    void handleBack();

    /**
     * Returns a string converter for displaying and parsing {@link Locale} values.
     *
     * @return a {@link StringConverter} for locales
     */
    StringConverter<Locale> getLocaleStringConverter();

    /**
     * Returns the property for the currently selected locale.
     *
     * @return the {@link Property} representing the current {@link Locale}
     */
    Property<Locale> getCurrentLocaleProperty();

    /**
     * Returns the property representing the fullscreen setting.
     *
     * @return the {@link Property} representing fullscreen mode
     */
    Property<Boolean> fullscreenProperty();

    /**
     * Returns the property representing the master volume level.
     *
     * @return the {@link DoubleProperty} for audio volume
     */
    DoubleProperty getVolumeProperty();

    /**
     * Saves the current settings (e.g., to persistent storage).
     */
    void save();

    /**
     * Resets all settings to their default values.
     */
    void resetToDefaults();

    /**
     * Handles resolution changes based on a selected string value.
     *
     * @param newVal the new resolution string (e.g., "1920x1080")
     */
    void resolutionHandler(String newVal);
}