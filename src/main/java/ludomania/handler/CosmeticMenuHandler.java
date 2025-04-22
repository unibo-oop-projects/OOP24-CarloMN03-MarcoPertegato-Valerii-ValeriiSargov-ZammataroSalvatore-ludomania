package ludomania.handler;

import java.util.List;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

public interface CosmeticMenuHandler {
    List<FicheTheme> getFicheThemes();

    List<CardTheme> getCardThemes();

    List<BackgroundTheme> getBackgroundThemes();

    void handleFicheChange(FicheTheme theme);

    void handleCardChange(CardTheme theme);

    void handleBackgroundChange(BackgroundTheme theme);

    void handleBack();

    CosmeticTheme getSelectedCardTheme();

    CosmeticTheme getSelectedFicheTheme();

    CosmeticTheme getSelectedBackgroundTheme();
}
