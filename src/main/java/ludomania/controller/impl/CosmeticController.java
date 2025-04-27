package ludomania.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Parent;
import javafx.util.Builder;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;
import ludomania.cosmetics.backgrounds.AmericanBackgroundTheme;
import ludomania.cosmetics.backgrounds.EuropeanBackgroundTheme;
import ludomania.cosmetics.backgrounds.NeonBackgroundTheme;
import ludomania.cosmetics.cards.AmericanCardTheme;
import ludomania.cosmetics.cards.EuropeanCardTheme;
import ludomania.cosmetics.cards.NeonCardTheme;
import ludomania.cosmetics.fiches.AmericanFicheTheme;
import ludomania.cosmetics.fiches.EuropeanFicheTheme;
import ludomania.cosmetics.fiches.NeonFicheTheme;
import ludomania.handler.CosmeticMenuHandler;
import ludomania.settings.api.SettingsManager;
import ludomania.view.CosmeticMenuViewBuilder;

public class CosmeticController implements Controller, CosmeticMenuHandler {
    private final SettingsManager settingsManager;
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    List<FicheTheme> ficheThemes;
    List<CardTheme> cardThemes;
    List<BackgroundTheme> backgroundThemes;

    public CosmeticController(final SettingsManager settingsManager, final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.settingsManager = settingsManager;
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;

        ficheThemes = new ArrayList<>(Arrays.asList(
                new EuropeanFicheTheme(),
                new AmericanFicheTheme(),
                new NeonFicheTheme()));
        cardThemes = new ArrayList<>(Arrays.asList(
                new EuropeanCardTheme(),
                new AmericanCardTheme(),
                new NeonCardTheme()));
        backgroundThemes = new ArrayList<>(Arrays.asList(
                new EuropeanBackgroundTheme(),
                new AmericanBackgroundTheme(),
                new NeonBackgroundTheme()));
        viewBuilder = new CosmeticMenuViewBuilder(this, sceneManager.getLanguageManager(),
                sceneManager.getImageProvider());
    }

    @Override
    public Parent getView() {
        return viewBuilder.build();
    }

    @Override
    public List<FicheTheme> getFicheThemes() {
        return ficheThemes;
    }

    @Override
    public List<CardTheme> getCardThemes() {
        return cardThemes;
    }

    @Override
    public List<BackgroundTheme> getBackgroundThemes() {
        return backgroundThemes;
    }

    @Override
    public void handleBack() {
        sceneManager.switchToMainMenu();
    }

    @Override
    public void handleFicheChange(FicheTheme theme) {
        audioManager.playSound("click");
        settingsManager.ficheThemeProperty().set(theme.getTheme());
    }

    @Override
    public void handleCardChange(CardTheme theme) {
        audioManager.playSound("click");
        settingsManager.cardThemeProperty().set(theme.getTheme());
    }

    @Override
    public void handleBackgroundChange(BackgroundTheme theme) {
        audioManager.playSound("click");
        settingsManager.backgroundThemeProperty().set(theme.getTheme());
    }

    @Override
    public CosmeticTheme getSelectedCardTheme() {
        return settingsManager.cardThemeProperty().get();
    }

    @Override
    public CosmeticTheme getSelectedFicheTheme() {
        return settingsManager.ficheThemeProperty().get();
    }

    @Override
    public CosmeticTheme getSelectedBackgroundTheme() {
        return settingsManager.backgroundThemeProperty().get();
    }

}
