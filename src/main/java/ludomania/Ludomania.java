package ludomania;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Ludomania extends Application{
    Card card;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        card=new Card(Rank.ACE,   Suit.CLUBS);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);        
        primaryStage.show();
    }
    private Region createContent() {
        return new Label(card.toString());
    }
}