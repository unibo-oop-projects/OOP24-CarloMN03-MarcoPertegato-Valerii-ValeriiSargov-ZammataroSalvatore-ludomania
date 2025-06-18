package ludomania.controller.roulette.core;

import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
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
    
    public void glowWheel(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof ImageView) {
            ImageView node = (ImageView) source;
            Glow glow = new Glow();
            
            glow.setLevel(0.7);
            node.setEffect(glow);
        }
    }
    
    public void unglowWheel(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof ImageView) {
            ImageView node = (ImageView) source;
            node.setEffect(null);
        }
    }
    
}
