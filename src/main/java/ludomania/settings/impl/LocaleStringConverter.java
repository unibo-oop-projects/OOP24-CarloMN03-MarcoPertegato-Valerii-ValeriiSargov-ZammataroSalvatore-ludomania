package ludomania.settings.impl;

import java.util.Locale;

import javafx.util.StringConverter;

public class LocaleStringConverter extends StringConverter<Locale> {
    @Override
    public String toString(final Locale locale) {
        return locale.getDisplayName(locale);
    }

    @Override
    public Locale fromString(final String string) {
        return Locale.forLanguageTag(string);
    }
}