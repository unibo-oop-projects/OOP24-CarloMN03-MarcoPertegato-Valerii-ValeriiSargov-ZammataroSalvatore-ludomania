package ludomania.core.impl;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.image.Image;
import ludomania.core.api.ImageManager;
import ludomania.core.api.ImageProvider;
import ludomania.cosmetics.CosmeticTheme;

public class ImageProviderImpl implements ImageProvider {
    private final ImageManager imageManager;
    private CosmeticSet currentTheme;

    public ImageProviderImpl(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    @Override
    public void setBackgroundTheme(CosmeticTheme theme) {
        currentTheme.setBackgroundTheme(theme);
    }

    @Override
    public void setCardTheme(CosmeticTheme theme) {
        currentTheme.setCardTheme(theme);
    }

    @Override
    public void setFicheTheme(CosmeticTheme theme) {
        currentTheme.setFicheTheme(theme);
    }

    @Override
    public Image getImage(String id) {
        return imageManager.getImage(id);
    }

    @Override
    public String getSVGBackground() {
        return currentTheme.getBackground();
    }

    @Override
    public String getSVGCard(Rank rank, Suit suit) {
        return currentTheme.getCard(rank, suit);
    }

    @Override
    public String getSVGFiche(Integer number) {
        return currentTheme.getFiche(number);
    }

}
