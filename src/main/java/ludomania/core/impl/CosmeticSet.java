package ludomania.core.impl;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

class CosmeticSet {
    private CardTheme card;
    private FicheTheme fiche;
    private BackgroundTheme background;

    public CosmeticSet(CosmeticTheme card, CosmeticTheme background, CosmeticTheme fiche) {
        setCardTheme(card);
        setBackgroundTheme(background);
        setFicheTheme(fiche);
    }

    public void setBackgroundTheme(CosmeticTheme theme) {
        background = theme.createBackgroundTheme();
    }

    public void setCardTheme(CosmeticTheme theme) {
        card = theme.createCardTheme();
    }

    public void setFicheTheme(CosmeticTheme theme) {
        fiche = theme.createFicheTheme();
    }

    public String getBackground() {
        return background.getCosmetic();
    }

    public String getCard(Rank rank, Suit suit) {
        return card.getCosmetic(rank, suit);
    }

    public String getFiche(Integer number) {
        return fiche.getCosmetic(number);
    }

}
