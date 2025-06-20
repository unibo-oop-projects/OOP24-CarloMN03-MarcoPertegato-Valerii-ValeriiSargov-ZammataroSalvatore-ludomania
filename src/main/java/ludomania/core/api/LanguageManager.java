package ludomania.core.api;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;

/**
 * Defines the interface for managing application language and localization.
 * <p>
 * Implementations of this interface allow dynamic switching of locales
 * and provide access to localized strings through bindings or direct lookups.
 */
public interface LanguageManager {
    /**
     * Sets the current locale to be used for localization.
     *
     * @param locale the new {@link Locale} to use
     */
    void setLocale(Locale locale);

    /**
     * Returns a {@link StringBinding} that updates automatically when the language
     * changes.
     *
     * @param key the key for the localized string
     * @return a binding to the localized string associated with the key
     */
    StringBinding bind(String key);

    /**
     * Gets the localized string for the specified key using the current locale.
     *
     * @param key the key for the localized string
     * @return the localized string
     */
    String getString(String key);

    /**
     * Returns an observable property for the current {@link ResourceBundle}.
     *
     * @return the property representing the current bundle
     */
    ObjectProperty<ResourceBundle> bundleProperty();

    /**
     * Creates a copy of the current {@link LanguageManager}.
     * @return a new LanguageManager instance with the same theme settings
     */
    LanguageManager copy();
}
