package ludomania.controller.roulette.core;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;

public class RouletteGameController {

    //private final RouletteModel model;

    public RouletteGameController() {
        //this.model = new RouletteModel();
    }

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
}
