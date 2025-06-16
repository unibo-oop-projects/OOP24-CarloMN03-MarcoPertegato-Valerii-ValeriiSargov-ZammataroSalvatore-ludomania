package ludomania.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ludomania.core.api.ImageProvider;
import ludomania.core.api.LanguageManager;
import ludomania.cosmetics.FicheValue;
import ludomania.handler.TrenteEtQuaranteHandler;

public class TrenteEtQuaranteViewBuilder implements ViewBuilder {

    private static final int OFFSET = 10;
    private final TrenteEtQuaranteHandler eventHandler;
    private final LanguageManager languageManager;
    private final ImageProvider imageProvider;
    private final ToggleGroup ficheToggleGroup;
    private final Label usernameLabel;
    private final Label balanceLabel;
    private final VBox betList;

    public TrenteEtQuaranteViewBuilder(final TrenteEtQuaranteHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
        this.ficheToggleGroup = new ToggleGroup();
        this.usernameLabel = new Label();
        this.balanceLabel = new Label();
        this.betList = new VBox(10);
    }

    @Override
    public Parent build() {
        System.out.println("build() chiamato");
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(OFFSET));

        root.setTop(createTopBar());

        root.setBottom(createBottomBar());

        root.setLeft(createBetList());

        root.setRight(createDoneButton());

        return root;
    }

    private Node createTopBar(){
        HBox topBar = new HBox(OFFSET);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(OFFSET));

        usernameLabel.setText("Test Player");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont((Font.font(16)));

        Label title = new Label("TrenteEtQuarante");
        title.setFont(Font.font("Serif", 36));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-weight: bold;");

        final Button homeBtn = new Button(languageManager.getString("go_back_button"));
        homeBtn.setOnMouseClicked(e -> eventHandler.handleReturnHome());
        
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);

        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(usernameLabel, leftSpacer, title, rightSpacer, homeBtn);

        return topBar;
    }

    private Node createBottomBar(){
        final HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setSpacing(10);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        balanceLabel.setText("Bilancio: xxx €");
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font(16));

        bottomBar.getChildren().addAll(createFicheBar(), spacer, balanceLabel);

        return bottomBar;
    }

    private Node createFicheBar(){
        final HBox ficheBar = new HBox(10);
        ficheBar.setAlignment(Pos.CENTER_LEFT);
        ficheBar.setPadding(new Insets(10));

        java.util.Arrays.stream(FicheValue.values())
        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
        .forEach(ficheValue -> {
            final ToggleButton button = new ToggleButton();
            button.setGraphic(imageProvider.getSVGFiche(ficheValue.getValue()));
            button.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
            button.setUserData(ficheValue);
            button.setToggleGroup(ficheToggleGroup);
            ficheBar.getChildren().add(button);
        });
         /*
        group.selectedToggleProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                final FicheValue value = (FicheValue) selected.getUserData();
                //onFicheSelected.accept(value);
            }
        });*/

        return ficheBar;
    }

    private Node createBetList() {
        betList.setPadding(new Insets(10));
        betList.setStyle("-fx-background-color: white;");
        betList.setPrefWidth(200);

        //Example
        for (int i = 1; i <= 20; i++) {
            Label betLabel = new Label("Bet #" + i + ": 100€ on Rouge");
            betLabel.setWrapText(true);
            betList.getChildren().add(betLabel);
        }

        ScrollPane scrollPane = new ScrollPane(betList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white;");
        
        return scrollPane;
    }

    private Node createDoneButton(){
        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPadding(new Insets(10));

        Button doneButton = new Button("Done");
        doneButton.setPrefWidth(100);
        doneButton.setOnAction(e -> {
            System.out.println("Done clicked");
        });

        rightBox.getChildren().add(doneButton);

        return rightBox;
    }
}
