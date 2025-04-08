package ludomania.settings;

import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;

public interface SettingsHandler {
    void handleBack();

    LocaleStringConverter getLocaleStringConverter();

    Property<Locale> getCurrentLocaleProperty();

    Property<Boolean> fullscreenProperty();

    DoubleProperty getVolumeProperty();

    void save();

    void resetToDefaults();

    void resolutionHandler(String newVal);
}
