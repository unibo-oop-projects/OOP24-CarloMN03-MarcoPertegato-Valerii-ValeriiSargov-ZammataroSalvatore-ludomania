package ludomania.view;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.CosmeticMenuHandler;

public class CosmeticMenuViewBuilder implements ViewBuilder {
    private final CosmeticMenuHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    public CosmeticMenuViewBuilder(final CosmeticMenuHandler eventHandler, final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
        return new HBox();
    }

}
