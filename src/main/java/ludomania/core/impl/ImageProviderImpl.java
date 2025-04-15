package ludomania.core.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    public Node getSVGBackground() {
        Group group = new Group();

        return group;
    }

    @Override
    public Node getSVGCard(Rank rank, Suit suit) {
        String svg = currentTheme.getCard(rank, suit);
        svg = svg.replaceAll("href=\"(#[^\"]+)\"", "xlink:href=\"$1\"");
        if (!svg.contains("xmlns:xlink")) {
            svg = svg.replace("<svg", "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" ");
        }
        try {
            ImageTranscoder transcoder = new JPEGTranscoder();

            transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.95f);

            TranscoderInput input = new TranscoderInput(new StringReader(svg));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            transcoder.transcode(input, output);

            byte[] imgData = outputStream.toByteArray();
            Image fxImage = new Image(new ByteArrayInputStream(imgData));

            ImageView imageView = new ImageView(fxImage);
            return imageView;

        } catch (TranscoderException e) {
            return new HBox();
        }
    }

    @Override
    public Node getSVGFiche(Integer number) {
        Group group = new Group();
        return group;
    }

}
