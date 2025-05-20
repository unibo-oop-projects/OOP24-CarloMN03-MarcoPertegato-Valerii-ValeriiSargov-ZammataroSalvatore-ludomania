package ludomania.view;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.handler.TrenteEtQuaranteHandler;

public class TrenteEtQuaranteViewBuilder implements ViewBuilder {

    private final TrenteEtQuaranteHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;

    public TrenteEtQuaranteViewBuilder(final TrenteEtQuaranteHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
    }

    @Override
    public Parent build() {
       return new VBox();
    }
    
}
