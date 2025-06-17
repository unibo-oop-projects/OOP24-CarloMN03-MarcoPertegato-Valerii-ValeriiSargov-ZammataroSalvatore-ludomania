package ludomania.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.view.roulette.RouletteViewBuilder;

public class RouletteController implements Controller {
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    
    public RouletteController(
    final SceneManager sceneManager,
    final AudioManager audioManager
    ) {
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        viewBuilder = new RouletteViewBuilder(this, sceneManager.getLanguageManager(),
        sceneManager.getImageProvider());
    }
    
    @Override
    public Parent getView() {
        return viewBuilder.build();
    }
    
    @FXML
    public void pleinBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            int id = Integer.parseInt(clickedButton.getId());
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void chevalBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Separator) {
            Separator clickedButton = (Separator) source;
            
            String[] ids = clickedButton.getId().split("-");
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }    
    }
    
    @FXML
    public void carreBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String[] ids = clickedButton.getId().split("-");
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void colonneBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            Character id = clickedButton.getId().charAt(0);
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void noirBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void rougeBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void pairBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void impairBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void passeBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void manqueBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();
            ObservableList<String> styleClasses = clickedButton.getStyleClass();    
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void douzineBet(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            
            String buttonText = clickedButton.getText();
            Character id = clickedButton.getId().charAt(0);            
            ObservableList<String> styleClasses = clickedButton.getStyleClass();
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    void highlightCarre(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            clickedButton.getParent().setStyle("-fx-border-color: #00eeff; -fx-border-width: 2px;");
            
            String buttonText = clickedButton.getText();
            String[] ids = clickedButton.getId().split("-");
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    void unhighlightCarre(MouseEvent event) {
        Object source = event.getSource();
        
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            clickedButton.getParent().setStyle("-fx-border-color: trasparent; -fx-border-width: 1px;");
            
            String buttonText = clickedButton.getText();
            String[] ids = clickedButton.getId().split("-");
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
}
