package ludomania.core.impl;

import java.util.Map;

import javafx.scene.image.Image;
import ludomania.core.api.ImageManager;

/**
 * Implementation of the {@link ImageManager} interface responsible for managing
 * and loading images.
 * <p>
 * This class handles the loading of images from the file system into memory,
 * storing them in a map, and providing access to
 * those images by their identifiers.
 * </p>
 */

public final class ImageManagerImpl implements ImageManager {
    private static final String DEFAULT_LOCATION = "images/";
    private final Map<String, Image> images;
    private final String pathToImages;

    /**
     * Constructs an {@link ImageManagerImpl} with a specified map of images.
     *
     * @param images a map where images are stored by their identifiers
     */
    public ImageManagerImpl(final Map<String, Image> images) {
        this.images = images;
        this.pathToImages = DEFAULT_LOCATION;
    }

    /**
     * Constructs an {@link ImageManagerImpl} with a specified map of images and a
     * custom path for image loading.
     *
     * @param images       a map where images are stored by their identifiers
     * @param pathToImages the custom path to the images directory
     */
    public ImageManagerImpl(final Map<String, Image> images, final String pathToImages) {
        this.images = images;
        this.pathToImages = pathToImages;
    }

    @Override
    public void init() {
        uploadImage("default.png", "default");
        uploadImage("game1.png", "game1");
        uploadImage("game2.png", "game2");
        uploadImage("game3.png", "game3");
        uploadImage("shopping-cart.png", "shoppingCart");
    }

    void uploadImage(final String imageName, final String id) {
        final Image image = new Image(pathToImages + imageName);
        images.put(id, image);
    }

    @Override
    public Image getImage(final String id) {
        if (images.containsKey(id)) {
            return images.get(id);
        }
        return images.get("default");
    }
}
