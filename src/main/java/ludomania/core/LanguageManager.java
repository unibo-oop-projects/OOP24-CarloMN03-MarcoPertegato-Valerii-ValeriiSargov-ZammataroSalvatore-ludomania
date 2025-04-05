package ludomania.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class LanguageManager {
    private static final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();
    static {
        setLocale(Locale.ITALIAN); // Lingua di default
    }

    private LanguageManager() {
    }

    public static void initialize(Locale locale) {
        setLocale(locale != null ? locale : Locale.ITALIAN);
    }

    public static void setLocale(Locale locale) {
        try {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", locale));
        } catch (MissingResourceException e) {
            System.err.println("ResourceBundle non trovato per la lingua: " + locale);
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", Locale.ITALIAN));
        }
    }

    public static StringBinding bind(String key) {
        return Bindings.createStringBinding(() -> bundleProperty.get().getString(key), bundleProperty);
    }

    public static String getString(String key) {
        if (bundleProperty.get() != null) {
            if (bundleProperty.get().containsKey(key)) {
                return bundleProperty.get().getString(key);
            } else {
                System.out.println("Chiave non trovata: " + key);
            }
        }
        return "Nessun testo disponibile";
    }
    // public static String getString(String key) {
    // return bundleProperty.get().getString(key);
    // }

    // Property osservabile per ascoltare cambiamenti (utile per logica avanzata)
    public static ObjectProperty<ResourceBundle> bundleProperty() {
        return bundleProperty;
    }
}