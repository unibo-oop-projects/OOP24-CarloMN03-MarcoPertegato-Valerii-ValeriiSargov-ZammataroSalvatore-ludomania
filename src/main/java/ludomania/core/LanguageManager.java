package ludomania.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LanguageManager {
    private final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();

    public LanguageManager(final Locale locale) {
        setLocale(locale != null ? locale : Locale.ITALIAN); // Lingua di default
    }

    public final void setLocale(final Locale locale) {
        try {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", locale));
        } catch (MissingResourceException e) {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", Locale.ITALIAN));
        }
    }

    public StringBinding bind(final String key) {
        return Bindings.createStringBinding(() -> bundleProperty.get().getString(key), bundleProperty);
    }

    public String getString(final String key) {
        if (bundleProperty.get() != null) {
            return bundleProperty.get().getString(key);
        }
        return "Nessun testo disponibile";
    }
    // public static String getString(String key) {
    // return bundleProperty.get().getString(key);
    // }

    // Property osservabile per ascoltare cambiamenti (utile per logica avanzata)
    public ObjectProperty<ResourceBundle> bundleProperty() {
        return bundleProperty;
    }
}