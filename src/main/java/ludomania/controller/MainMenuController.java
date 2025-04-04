package ludomania.controller;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ludomania.model.MainMenuHandler;
import ludomania.model.MainMenuModel;
import ludomania.view.MainMenuViewBuilder;

public class MainMenuController implements Controller, MainMenuHandler {
    private final Builder<Region> viewBuilder;

    public MainMenuController() {
        viewBuilder = new MainMenuViewBuilder(new MainMenuModel(), this);
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    public void startGame() {
        System.out.println("ciao dux");
    }

    @Override
    public void handleStartGame() {
    }

    @Override
    public void handleSettings() {
    }

    @Override
    public void handleExit() {
    }

}