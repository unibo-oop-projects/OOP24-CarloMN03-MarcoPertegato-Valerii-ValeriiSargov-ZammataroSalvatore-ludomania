package ludomania.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

/**
 * Controller for managing the cosmetic menu of the application.
 * <p>
 * This controller allows the user to change cosmetic themes such as the fiche
 * theme, card theme, and background theme.
 * It interacts with the {@link SettingsManager} to update the selected themes,
 * the {@link AudioManager} to play sounds,
 * and the {@link SceneManager} to navigate between different scenes.
 * </p>
 */
public final class CosmeticController implements Controller, CosmeticMenuHandler {
    private final SettingsManager settingsManager;
    private final Builder<Parent> viewBuilder;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private final List<FicheTheme> ficheThemes;
    private final List<CardTheme> cardThemes;
    private final List<BackgroundTheme> backgroundThemes;

    /**
     * Constructs a {@link CosmeticController} with the specified
     * {@link SettingsManager}, {@link SceneManager}, and {@link AudioManager}.
     *
     * @param settingsManager the {@link SettingsManager} used to manage the
     *                        selected themes
     * @param sceneManager    the {@link SceneManager} used for scene transitions
     * @param audioManager    the {@link AudioManager} used to play sounds
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "References to languageManager and audioManager are shared intentionally as they are immutable or managed externally."
    )
    public CosmeticController(final SettingsManager settingsManager, final SceneManager sceneManager,
            final AudioManager audioManager) {
        this.settingsManager = Objects.requireNonNull(settingsManager);
        this.sceneManager = Objects.requireNonNull(sceneManager);
        this.audioManager = Objects.requireNonNull(audioManager);

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
        return new ArrayList<>(ficheThemes);
    }

    @Override
    public List<CardTheme> getCardThemes() {
        return new ArrayList<>(cardThemes);
    }

    @Override
    public List<BackgroundTheme> getBackgroundThemes() {
        return new ArrayList<>(backgroundThemes);
    }

    @Override
    public void handleBack() {
        sceneManager.switchToMainMenu();
    }

    @Override
    public void handleFicheChange(final FicheTheme theme) {
        audioManager.playSound("click");
        settingsManager.ficheThemeProperty().set(theme.getTheme());
    }

    @Override
    public void handleCardChange(final CardTheme theme) {
        audioManager.playSound("click");
        settingsManager.cardThemeProperty().set(theme.getTheme());
    }

    @Override
    public void handleBackgroundChange(final BackgroundTheme theme) {
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
