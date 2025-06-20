package ludomania.core.api;

import javafx.scene.image.Image;

/**
 * Interface for managing images within the application.
 * 
 * Supports preloading images and retrieving them by identifier.
 */

public interface ImageManager {
    /**
     * Preloads a predefined set of images into memory for later use.
     */
    void init();

    /**
     * Retrieves a loaded image based on its identifier.
     * 
     * @param id the unique identifier of the image
     * @return the corresponding Image object
     */
    Image getImage(String id);
}
