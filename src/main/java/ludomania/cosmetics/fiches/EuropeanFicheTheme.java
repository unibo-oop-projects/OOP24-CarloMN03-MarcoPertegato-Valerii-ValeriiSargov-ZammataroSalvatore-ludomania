package ludomania.cosmetics.fiches;


import javafx.scene.Node;
import javafx.scene.layout.HBox;
import ludomania.cosmetics.CosmeticTheme;

public class EuropeanFicheTheme implements CosmeticTheme{

    @Override
    public Node getCosmetic() {
        return new HBox();
    }
}
