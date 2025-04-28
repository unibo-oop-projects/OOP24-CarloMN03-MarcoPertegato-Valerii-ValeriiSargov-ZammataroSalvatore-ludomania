package ludomania.core.impl;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ludomania.core.api.LanguageManager;

/**
 * Implementation of the {@link LanguageManager} interface that manages the
 * language settings for the application.
 * <p>
 * This class is responsible for loading and managing the {@link ResourceBundle}
 * based on the current locale. It provides
 * methods for setting the locale, retrieving localized strings, and binding a
 * string to the UI based on a specific key.
 * </p>
 */

public final class LanguageManagerImpl implements LanguageManager {
    private final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();

    /**
     * Constructs a new {@link LanguageManagerImpl} with the specified locale.
     * <p>
     * If the locale is {@code null}, it defaults to {@link Locale#ITALIAN}.
     * </p>
     *
     * @param locale the locale to set for the language manager
     */
    public LanguageManagerImpl(final Locale locale) {
        setLocale(locale != null ? locale : Locale.ITALIAN); // Lingua di default
    }

    @Override
    public void setLocale(final Locale locale) {
        try {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", locale));
        } catch (final MissingResourceException e) {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", Locale.ITALIAN));
        }
    }

    @Override
    public StringBinding bind(final String key) {
        return Bindings.createStringBinding(() -> bundleProperty.get().getString(key), bundleProperty);
    }

    @Override
    public String getString(final String key) {
        if (bundleProperty.get() != null) {
            return bundleProperty.get().getString(key);
        }
        return "Nessun testo disponibile";
    }

    @Override
    public ObjectProperty<ResourceBundle> bundleProperty() {
        return bundleProperty;
    }
}