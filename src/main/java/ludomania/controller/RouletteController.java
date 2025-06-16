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
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            int id = Integer.parseInt(clickedButton.getId());

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }

    }
    
    @FXML
    public void chevalBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Separator) {
            Separator clickedButton = (Separator) source;

            // 3. Get the button's text
            String[] ids = clickedButton.getId().split("-");

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + ids.toString());
        } else {
            System.out.println("Event source is not a Button.");
        }

    }
    
    @FXML
    public void carreBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String[] ids = clickedButton.getId().split("-");

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + ids.toString());
        } else {
            System.out.println("Event source is not a Button.");
        }

    }
    
    @FXML
    public void colonneBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            Character id = clickedButton.getId().charAt(0);

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }

    }
    
    @FXML
    public void noirBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }

    }
    
    @FXML
    public void rougeBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }

    @FXML
    public void pairBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void impairBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void passeBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void manqueBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            String id = clickedButton.getId();

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }
    
    @FXML
    public void douzineBet(MouseEvent event) {
        System.out.println("Button clicked");

        // 1. Get the source of the event
        Object source = event.getSource();

        // 2. Check if the source is a Button and cast it
        if (source instanceof Button) {
            Button clickedButton = (Button) source;

            // 3. Get the button's text
            String buttonText = clickedButton.getText();
            Character id = clickedButton.getId().charAt(0);

            // 4. Get the button's CSS style classes
            ObservableList<String> styleClasses = clickedButton.getStyleClass();

            System.out.println("Button Clicked!");
            System.out.println("  Text: " + buttonText);
            System.out.println("  Classes: " + styleClasses);
            System.out.println("  id: " + id);
        } else {
            System.out.println("Event source is not a Button.");
        }
    }


}
