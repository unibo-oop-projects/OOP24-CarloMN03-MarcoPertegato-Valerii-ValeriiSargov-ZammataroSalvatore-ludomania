package ludomania.core.impl;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.paint.Color;
import ludomania.core.api.CosmeticSet;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

/**
 * Implementation of the {@link CosmeticSet} interface that represents a set of
 * cosmetic themes for cards, fiches, and backgrounds.
 * <p>
 * This class allows for setting and retrieving different cosmetic themes for
 * cards, fiches, and backgrounds.
 * </p>
 */

public final class CosmeticSetImpl implements CosmeticSet {
    private CardTheme card;
    private FicheTheme fiche;
    private BackgroundTheme background;

    /**
     * Constructs a {@link CosmeticSetImpl} with the specified cosmetic themes for
     * card, background, and fiche.
     *
     * @param card       the {@link CosmeticTheme} to be used for the card
     * @param background the {@link CosmeticTheme} to be used for the background
     * @param fiche      the {@link CosmeticTheme} to be used for the fiche
     */
    public CosmeticSetImpl(final CosmeticTheme card, final CosmeticTheme background, final CosmeticTheme fiche) {
        this.background = background.createBackgroundTheme();
        this.card = card.createCardTheme();
        this.fiche = fiche.createFicheTheme();
    }

    @Override
    public void setBackgroundTheme(final CosmeticTheme theme) {
        background = theme.createBackgroundTheme();
    }

    @Override
    public void setCardTheme(final CosmeticTheme theme) {
        card = theme.createCardTheme();
    }

    @Override
    public void setFicheTheme(final CosmeticTheme theme) {
        fiche = theme.createFicheTheme();
    }

    @Override
    public void setBackgroundTheme(final BackgroundTheme theme) {
        background = theme;
    }

    @Override
    public void setCardTheme(final CardTheme theme) {
        card = theme;
    }

    @Override
    public void setFicheTheme(final FicheTheme theme) {
        fiche = theme;
    }

    @Override
    public Color getBackground() {
        return Color.web(background.getCosmetic());
    }

    @Override
    public String getCard(final Rank rank, final Suit suit) {
        return card.getCosmetic(rank, suit);
    }

    @Override
    public String getFiche(final Integer number) {
        return fiche.getCosmetic(number);
    }

    @Override
    public CosmeticSet copy() {
        return new CosmeticSetImpl(card.getTheme(), fiche.getTheme(), background.getTheme());
    }

}
