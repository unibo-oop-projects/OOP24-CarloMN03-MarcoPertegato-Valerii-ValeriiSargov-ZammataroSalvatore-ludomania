package ludomania.view;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private final Label noirTotalLabel;
    private final Label rougeTotalLabel;
    private List<Label> betZonesLabels;
    private final VBox betList;
    private HBox noirCardsBox;
    private HBox rougeCardsBox;

    public TrenteEtQuaranteViewBuilder(final TrenteEtQuaranteHandler eventHandler,
            final LanguageManager languageManager,
            final ImageProvider imageProvider) {
        this.eventHandler = eventHandler;
        this.languageManager = languageManager;
        this.imageProvider = imageProvider;
        this.ficheToggleGroup = new ToggleGroup();
        this.usernameLabel = new Label();
        this.balanceLabel = new Label();
        this.noirTotalLabel = new Label();
        this.rougeTotalLabel = new Label();
        this.betList = new VBox(10);
        this.betZonesLabels = new ArrayList<>();
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

        root.setCenter(createCenter());

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
        
        final Button rulesBtn = new Button(languageManager.getString("rule_button"));
        rulesBtn.setOnAction(e -> showRulesDialog());
        
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);

        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(usernameLabel, leftSpacer, title, rightSpacer, rulesBtn, homeBtn);

        return topBar;
    }

    private void showRulesDialog() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Trente et Quarante");       

        Label rulesLabel = new Label(languageManager.getString("tq_rules_text"));
        rulesLabel.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(rulesLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background: white;");

        Button okBtn = new Button(languageManager.getString("exit"));
        okBtn.setOnAction(e -> dialog.close());

        VBox dialogVBox = new VBox(10, scrollPane, okBtn);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(dialogVBox, 450, 450);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private Node createBottomBar(){
        final HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setSpacing(10);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        balanceLabel.setText(languageManager.getString("money")+": xxx €");
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font(16));

        bottomBar.getChildren().addAll(createFicheBar(), spacer, balanceLabel);

        return bottomBar;
    }

    private Node createFicheBar(){
        final HBox ficheBar = new HBox(10);
        ficheBar.setAlignment(Pos.CENTER_LEFT);
        ficheBar.setPadding(new Insets(10));

        Arrays.stream(FicheValue.values())
        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
        .forEach(ficheValue -> {
            final ToggleButton button = new ToggleButton();
            button.setGraphic(imageProvider.getSVGFiche(ficheValue.getValue()));
            button.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
            button.setUserData(ficheValue);
            button.setToggleGroup(ficheToggleGroup);
            ficheBar.getChildren().add(button);
        });

        ficheToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ((ToggleButton) oldToggle).setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
            }
            if (newToggle != null) {
                ((ToggleButton) newToggle).setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0; -fx-border-color: white; -fx-border-width: 2;");
            }
        });
        return ficheBar;
    }

    private Node createBetList() {
        betList.setPadding(new Insets(10));
        betList.setStyle("-fx-background-color: white;");
        betList.setPrefWidth(200);

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

        Button doneButton = new Button(languageManager.getString("done"));
        doneButton.setPrefWidth(100);
        doneButton.setOnAction(e -> {
            System.out.println("Done clicked");
        });

        rightBox.getChildren().add(doneButton);

        return rightBox;
    }

    private Node createCenter(){
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(10));
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        centerBox.getChildren().addAll(createCardRows(), createBetZones());

        return centerBox;
    }

    private Node createCardRows() {
        VBox cardRows = new VBox(10);
        cardRows.setPadding(new Insets(10));

        this.noirCardsBox = createCardBox();
        ScrollPane noirScrollPane = createCardScrollPane(noirCardsBox);
        HBox noirRow = createSingleRow("Noir", noirScrollPane, noirTotalLabel);

        this.rougeCardsBox = createCardBox();
        ScrollPane rougeScrollPane = createCardScrollPane(rougeCardsBox);
        HBox rougeRow = createSingleRow("Rouge", rougeScrollPane, rougeTotalLabel);

        cardRows.getChildren().addAll(noirRow, rougeRow);

        return cardRows;
    }

    private HBox createCardBox() {
        HBox cardBox = new HBox(5);
        cardBox.setMinHeight(160);
        cardBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");
        cardBox.setAlignment(Pos.CENTER_LEFT);
        return cardBox;
    }

    private ScrollPane createCardScrollPane(HBox cardBox) {
        ScrollPane scrollPane = new ScrollPane(cardBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(160);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        return scrollPane;
    }

    private HBox createSingleRow(String labelText, ScrollPane scrollPane, Label totalLabel) {
        Label rowLabel = new Label(labelText);
        rowLabel.setTextFill(Color.WHITE);
        rowLabel.setFont(Font.font(16));
        rowLabel.setMinWidth(60);

        totalLabel.setText("Total: 0");
        totalLabel.setTextFill(Color.WHITE);
        totalLabel.setFont(Font.font(16));

        HBox row = new HBox(10, rowLabel, scrollPane, totalLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        return row;
    }

    private Node createBetZones() {
        HBox zonesBox = new HBox();
        zonesBox.setSpacing(0);
        zonesBox.setPadding(new Insets(5));
        zonesBox.setAlignment(Pos.CENTER);
        
        String[] zoneNames = {"Noir", "Rouge", "Couleur", "Enverse"};
        betZonesLabels.clear();
        
        for (String name : zoneNames) {
            Label zone = new Label(name);
            zone.setTextFill(Color.WHITE);
            zone.setAlignment(Pos.CENTER);
            zone.setPrefHeight(50);
            zone.setPrefWidth(120);
            zone.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-background-color: transparent;");
            zone.setOnMouseClicked(e -> {
                if (!zone.isDisable()) {
                   System.out.println("Zona puntata cliccata: " + name); 
                }                
            });
            
            betZonesLabels.add(zone);
            zonesBox.getChildren().add(zone);
        }

        ficheToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
        boolean enabled = (newToggle != null);
        for (Label zoneLabel : betZonesLabels) {
                zoneLabel.setDisable(!enabled);
                if (enabled) {
                    zoneLabel.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-background-color: transparent;");
                } else {
                    zoneLabel.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #333333;");
                }
            }
        });
        
        if (getSelectedFiche() == null) {
            for (Label zoneLabel : betZonesLabels) {
                zoneLabel.setDisable(true);
                zoneLabel.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #333333;");
            }
        }
        
        return zonesBox;
    }

    public void setNoirTotal(int total) {
        noirTotalLabel.setText("Total: " + total);
    }

    public void setRougeTotal(int total) {
        rougeTotalLabel.setText("Total: " + total);
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public void setBalance(int balance) {
        balanceLabel.setText("Balance: "+ balance+ " $");
    }

    public void addBet(String betDescrizione) {
        Label betLabel = new Label(betDescrizione);
        betLabel.setWrapText(true);
        betList.getChildren().add(betLabel);
    }

    public void clearBets() {
        betList.getChildren().clear();
    }

    public FicheValue getSelectedFiche() {
        ToggleButton selectedToggle = (ToggleButton) ficheToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            return (FicheValue) selectedToggle.getUserData();
        }
        return null;
    }

    public void addCardToNoir(Node cardNode) {
        noirCardsBox.getChildren().add(cardNode);
    }

    public void addCardToRouge(Node cardNode) {
        rougeCardsBox.getChildren().add(cardNode);
    }

    public void clearNoirCards() {
        noirCardsBox.getChildren().clear();
    }

    public void clearRougeCards() {
        rougeCardsBox.getChildren().clear();
    }
}
