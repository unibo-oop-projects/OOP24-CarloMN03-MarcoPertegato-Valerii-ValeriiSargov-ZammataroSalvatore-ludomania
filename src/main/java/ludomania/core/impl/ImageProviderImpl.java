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
import ludomania.core.api.ImageManager;
import ludomania.core.api.ImageProvider;
import ludomania.cosmetics.CosmeticTheme;

public class ImageProviderImpl implements ImageProvider {
    private final ImageManager imageManager;
    private final CosmeticSet currentTheme;

    public ImageProviderImpl(ImageManager imageManager, CosmeticSet cosmeticSet) {
        this.imageManager = imageManager;
        currentTheme = cosmeticSet;
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
    public Color getBackgroundColor() {
        return currentTheme.getBackground();
    }

    @Override
    public Region getSVGCard(Rank rank, Suit suit) {
        String svg = currentTheme.getCard(rank, suit);
        return svgHelperMethod(svg);

    }

    @Override
    public Region getSVGFiche(Integer number) {
        String svg = currentTheme.getFiche(number);
        return svgHelperMethod(svg);
    }

    @Override
    public Region svgHelperMethod(String svg) {
        try {
            ImageTranscoder transcoder = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new StringReader(svg));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);
            input.setURI("http://IDontKnowWhyINeedThisURIToMakeThisMethodWorkButItNeedsIt" + "=^.^=" + ".com/");
            transcoder.transcode(input, output);
            byte[] imgData = outputStream.toByteArray();
            Image fxImage = new Image(new ByteArrayInputStream(imgData));
            ImageView imageView = new ImageView(fxImage);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            return new HBox(imageView);
        } catch (TranscoderException e) {
            return new HBox();
        }
    }
}
