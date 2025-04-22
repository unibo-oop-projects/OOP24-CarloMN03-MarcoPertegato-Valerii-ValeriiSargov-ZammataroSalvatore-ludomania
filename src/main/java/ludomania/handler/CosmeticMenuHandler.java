package ludomania.handler;

import java.util.List;

import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.FicheTheme;

public interface CosmeticMenuHandler {
    List<FicheTheme> getFicheThemes();

    List<CardTheme> getCardThemes();

    List<BackgroundTheme> getBackgroundThemes();

    void handleFicheChange(String Theme);

    void handleCardChange(String Theme);

    void handleBackgroundChange(String Theme);

    void handleBack();
}
