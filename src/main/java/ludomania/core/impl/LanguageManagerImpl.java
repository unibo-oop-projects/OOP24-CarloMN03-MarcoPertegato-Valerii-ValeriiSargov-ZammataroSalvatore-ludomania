package ludomania.core.impl;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ludomania.core.api.LanguageManager;

public final class LanguageManagerImpl implements LanguageManager {
    private final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();

    public LanguageManagerImpl(final Locale locale) {
        setLocale(locale != null ? locale : Locale.ITALIAN); // Lingua di default
    }

    @Override
    public void setLocale(final Locale locale) {
        try {
            bundleProperty.set(ResourceBundle.getBundle("languages/strings", locale));
        } catch (MissingResourceException e) {
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