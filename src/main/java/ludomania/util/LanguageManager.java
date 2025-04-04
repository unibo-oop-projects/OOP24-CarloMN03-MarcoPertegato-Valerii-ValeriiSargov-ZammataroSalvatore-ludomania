package ludomania.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class LanguageManager {
    private static final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>(
            loadBundle(Locale.ITALIAN)); // Default italiano

    private LanguageManager() {
    } // Classe non istanziabile

    // Carica il ResourceBundle per una specifica Locale
    private static ResourceBundle loadBundle(Locale locale) {
        return ResourceBundle.getBundle("languages/strings", locale);
    }

    // Metodo per cambiare lingua
    public static void setLocale(Locale locale) {
        bundleProperty.set(loadBundle(locale));
    }

    // Binding osservabile per le stringhe
    public static StringBinding bind(String key) {
        return Bindings.createStringBinding(() -> bundleProperty.get().getString(key), bundleProperty);
    }

    // Metodo diretto per accesso non-osservabile (opzionale)
    public static String getString(String key) {
        return bundleProperty.get().getString(key);
    }

    // Property osservabile per ascoltare cambiamenti (utile per logica avanzata)
    public static ObjectProperty<ResourceBundle> bundleProperty() {
        return bundleProperty;
    }
}