package ludomania.settings.impl;

import java.util.Locale;

import javafx.util.StringConverter;

/**
 * Converts between {@link Locale} objects and their localized display names.
 * <p>
 * Used primarily to display human-readable language names in UI controls
 * like {@link javafx.scene.control.ComboBox}.
 */

public final class LocaleStringConverter extends StringConverter<Locale> {

    /**
     * Converts a {@link Locale} to its display name in its own language.
     *
     * @param locale the locale to convert
     * @return the localized display name of the locale
     */

    @Override
    public String toString(final Locale locale) {
        return locale.getDisplayName(locale);
    }

    @Override
    public Locale fromString(final String string) {
        return Locale.forLanguageTag(string);
    }
}