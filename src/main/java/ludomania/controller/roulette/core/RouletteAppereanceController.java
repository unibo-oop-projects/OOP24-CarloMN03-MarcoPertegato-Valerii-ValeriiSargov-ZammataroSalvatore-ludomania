package ludomania.controller.roulette.core;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class RouletteAppereanceController {

    public RouletteAppereanceController() {}

    public void highlightCarre(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Node) {
            Node clickedButton = (Node) source;
            clickedButton.getParent().setStyle("-fx-border-color: #00eeff; -fx-border-width: 3px;");
        }
    }
    
    public void unhighlightCarre(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Node) {
            Node clickedButton = (Node) source;
            clickedButton.getParent().setStyle("-fx-border-color: trasparent; -fx-border-width: 1px;");
        }
    }
}
