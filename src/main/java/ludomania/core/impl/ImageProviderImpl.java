package ludomania.core.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import ludomania.core.api.CosmeticSet;
import ludomania.core.api.ImageManager;
import ludomania.core.api.ImageProvider;
import ludomania.cosmetics.BackgroundTheme;
import ludomania.cosmetics.CardTheme;
import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;

/**
 * Implementation of the {@link ImageProvider} interface that provides images
 * based on the current cosmetic theme.
 * <p>
 * This class is responsible for providing various images such as backgrounds,
 * cards, and fiches, according to the
 * selected cosmetic theme. It uses the {@link ImageManager} to load the images
 * and apply the theme.
 * </p>
 */
public final class ImageProviderImpl implements ImageProvider {
    private final ImageManager imageManager;
    private final CosmeticSet currentTheme;

    /**
     * Constructs a new {@link ImageProviderImpl} with the specified image manager
     * and cosmetic set.
     *
     * @param imageManager the {@link ImageManager} used to load images
     * @param cosmeticSet  the {@link CosmeticSet} that defines the current theme
     *                     (card, background, fiche)
     */
    public ImageProviderImpl(final ImageManager imageManager, final CosmeticSet cosmeticSet) {
        this.imageManager = imageManager;
        currentTheme = cosmeticSet;
    }

    @Override
    public void setBackgroundTheme(final CosmeticTheme theme) {
        currentTheme.setBackgroundTheme(theme);
    }

    @Override
    public void setBackgroundTheme(final BackgroundTheme theme) {
        currentTheme.setBackgroundTheme(theme);
    }

    @Override
    public void setCardTheme(final CosmeticTheme theme) {
        currentTheme.setCardTheme(theme);
    }

    @Override
    public void setCardTheme(final CardTheme theme) {
        currentTheme.setCardTheme(theme);
    }

    @Override
    public void setFicheTheme(final CosmeticTheme theme) {
        currentTheme.setFicheTheme(theme);
    }

    @Override
    public void setFicheTheme(final FicheTheme theme) {
        currentTheme.setFicheTheme(theme);
    }

    @Override
    public Image getImage(final String id) {
        return imageManager.getImage(id);
    }

    @Override
    public Color getBackgroundColor() {
        return currentTheme.getBackground();
    }

    @Override
    public Region getSVGCard(final Rank rank, final Suit suit) {
        final String svg = currentTheme.getCard(rank, suit);
        return svgHelperMethod(svg);

    }

    @Override
    public Region getSVGFiche(final Integer number) {
        final String svg = currentTheme.getFiche(number);
        return svgHelperMethod(svg);
    }

    @Override
    public Region svgHelperMethod(final String svg) {
        try {
            final ImageTranscoder transcoder = new PNGTranscoder();
            final TranscoderInput input = new TranscoderInput(new StringReader(svg));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final TranscoderOutput output = new TranscoderOutput(outputStream);
            input.setURI("http://IDontKnowWhyINeedThisURIToMakeThisMethodWorkButItNeedsIt" + "=^.^=" + ".com/");
            transcoder.transcode(input, output);
            final byte[] imgData = outputStream.toByteArray();
            final Image fxImage = new Image(new ByteArrayInputStream(imgData));
            final ImageView imageView = new ImageView(fxImage);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            return new HBox(imageView);
        } catch (final TranscoderException e) {
            return new HBox();
        }
    }
}
